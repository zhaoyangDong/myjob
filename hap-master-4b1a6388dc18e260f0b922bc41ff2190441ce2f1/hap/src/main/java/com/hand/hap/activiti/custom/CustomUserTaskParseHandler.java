/*
 * Copyright Hand China Co.,Ltd.
 */

package com.hand.hap.activiti.custom;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.EndEvent;
import org.activiti.bpmn.model.ExclusiveGateway;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.ImplementationType;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.Task;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.bpmn.parser.factory.ActivityBehaviorFactory;
import org.activiti.engine.impl.bpmn.parser.handler.UserTaskParseHandler;
import org.springframework.beans.factory.annotation.Autowired;

import com.hand.hap.activiti.listeners.NotificationListener;

/**
 * @author shengyang.zhou@hand-china.com
 */
public class CustomUserTaskParseHandler extends UserTaskParseHandler {

    @Autowired
    private NotificationListener notificationListener;

    @Autowired
    private ActivityBehaviorFactory activityBehaviorFactory;

    @Override
    protected void executeParse(BpmnParse bpmnParse, UserTask userTask) {
        super.executeParse(bpmnParse, userTask);
        ///// user notify
        ActivitiListener listener = new ActivitiListener();
        listener.setEvent(TaskListener.EVENTNAME_CREATE);
        listener.setImplementationType(ImplementationType.IMPLEMENTATION_TYPE_INSTANCE);
        listener.setInstance(notificationListener);
        userTask.getTaskListeners().add(listener);
        ///////
        FlowNode flowNode = addApproveChain(bpmnParse, userTask);
        autoEnd(bpmnParse, flowNode);
    }

    private FlowNode addApproveChain(BpmnParse bpmnParse, UserTask userTask) {
        List<SequenceFlow> outgoings = userTask.getOutgoingFlows();
        if (outgoings.size() != 1) {
            return userTask;
        }

        ActivitiListener executeListener = new ActivitiListener();
        executeListener.setEvent("start");
        executeListener.setImplementationType("expression");
        executeListener.setImplementation("#{hapApproveChain.onTaskStart(execution)}");
        userTask.getExecutionListeners().add(executeListener);

        SequenceFlow s0 = outgoings.get(0);


        ExclusiveGateway exclusiveGateway = new ExclusiveGateway();
        exclusiveGateway.setId("eg_auto_" + UUID.randomUUID().toString());
        exclusiveGateway.setParentContainer(userTask.getParentContainer());
        exclusiveGateway.setBehavior(activityBehaviorFactory.createExclusiveGatewayActivityBehavior(exclusiveGateway));

        SequenceFlow s1 = new SequenceFlow(); // usertask -> service

        s1.setValues(s0);
        s1.setWaypoints(s0.getWaypoints());
        s1.setSourceFlowElement(userTask);
        s1.setTargetFlowElement(exclusiveGateway);

        outgoings.remove(s0);
        outgoings.add(s1);

        exclusiveGateway.getIncomingFlows().add(s1);

        SequenceFlow s2 = new SequenceFlow(); // 选择网关指向 usertask
        s2.setId("sf_auto_" + UUID.randomUUID().toString());
        s2.setSourceRef(exclusiveGateway.getId());
        s2.setSourceFlowElement(exclusiveGateway);
        s2.setTargetRef(userTask.getId());
        s2.setTargetFlowElement(userTask);
        s2.setConditionExpression("${hapApproveChain.execute(execution,'"+userTask.getId()+"')=='Y'}");

        userTask.getIncomingFlows().add(s2);
        exclusiveGateway.getOutgoingFlows().add(s2);

        SequenceFlow defaultSeq = new SequenceFlow(); // continue;
        defaultSeq.setId("sf_auto_" + UUID.randomUUID().toString());

        if (s0.getTargetFlowElement() instanceof FlowNode) {
            FlowNode targetTskOfs0 = (FlowNode) s0.getTargetFlowElement();
            targetTskOfs0.getIncomingFlows().remove(s0);
            targetTskOfs0.getIncomingFlows().add(defaultSeq);
        }
        defaultSeq.setSourceRef(exclusiveGateway.getId());
        defaultSeq.setSourceFlowElement(exclusiveGateway);
        defaultSeq.setTargetFlowElement(s0.getTargetFlowElement());
        defaultSeq.setTargetRef(s0.getTargetRef());

        exclusiveGateway.getOutgoingFlows().add(0, defaultSeq);// add at 0
        exclusiveGateway.setDefaultFlow(defaultSeq.getId());

        bpmnParse.getSequenceFlows().put(s1.getId(), s1);
        bpmnParse.getSequenceFlows().put(s2.getId(), s2);
        bpmnParse.getSequenceFlows().put(defaultSeq.getId(), defaultSeq);

        return exclusiveGateway;
    }

    private void autoEnd(BpmnParse bpmnParse, FlowNode userTask) {
        List<SequenceFlow> outgoings = userTask.getOutgoingFlows();
        if (outgoings == null || outgoings.size() == 0) {
            return;
        }

        SequenceFlow s0 = outgoings.get(0); // userTask 原始连出线

        FlowElement nextNode = s0.getTargetFlowElement();
        if (nextNode instanceof Task) {
            ExclusiveGateway exclusiveGateway = new ExclusiveGateway();
            exclusiveGateway.setId("eg_auto_" + UUID.randomUUID().toString());
            exclusiveGateway.setParentContainer(userTask.getParentContainer());
            exclusiveGateway
                    .setBehavior(activityBehaviorFactory.createExclusiveGatewayActivityBehavior(exclusiveGateway));

            outgoings.remove(s0);
            SequenceFlow s1 = new SequenceFlow();
            outgoings.add(s1); // s1 替换 s0, (id 保持不变)
            s1.setValues(s0); // s1 copy s0 属性
            s1.setSourceFlowElement(userTask);
            s1.setTargetFlowElement(exclusiveGateway);
            s1.setWaypoints(s0.getWaypoints());
            exclusiveGateway.setIncomingFlows(Arrays.asList(s1));

            SequenceFlow s2 = new SequenceFlow(); // 选择网关默认连出线, 连向 s0 原来的 target
            s2.setId("sf_auto_" + UUID.randomUUID().toString());
            s2.setSourceFlowElement(exclusiveGateway);
            s2.setSourceRef(exclusiveGateway.getId());
            s2.setTargetFlowElement(nextNode);
            s2.setTargetRef(nextNode.getId());
            ((Task) nextNode).getIncomingFlows().remove(s0);
            ((Task) nextNode).getIncomingFlows().add(s2);

            SequenceFlow s3 = new SequenceFlow(); // 从选择网关 连向 endevent
            s3.setId("sf_auto_" + UUID.randomUUID().toString());
            s3.setSourceFlowElement(exclusiveGateway);
            s3.setSourceRef(exclusiveGateway.getId());

            EndEvent endEvent = new EndEvent();
            endEvent.setId("end_auto_" + UUID.randomUUID().toString());

            s3.setTargetFlowElement(endEvent);
            s3.setTargetRef(endEvent.getId());
            s3.setConditionExpression("${approveResult=='REJECTED'}");

            exclusiveGateway.setOutgoingFlows(Arrays.asList(s2, s3));

            exclusiveGateway.setDefaultFlow(s2.getId());

            bpmnParse.getSequenceFlows().put(s1.getId(), s1);
            bpmnParse.getSequenceFlows().put(s2.getId(), s2);
            bpmnParse.getSequenceFlows().put(s3.getId(), s3);

        }
    }
}
