package com.hand.hap.activiti.custom;

import com.hand.hap.activiti.core.IActivitiConstants;
import org.activiti.bpmn.model.Activity;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.bpmn.behavior.AbstractBpmnActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.ParallelMultiInstanceBehavior;

/**
 * @author shengyang.zhou@hand-china.com
 */
public class CustomParallelMultiInstanceBehavior extends ParallelMultiInstanceBehavior implements IActivitiConstants {
    public CustomParallelMultiInstanceBehavior(Activity activity,
            AbstractBpmnActivityBehavior originalActivityBehavior) {
        super(activity, originalActivityBehavior);
    }

    @Override
    protected int createInstances(DelegateExecution execution) {
        int ret = super.createInstances(execution);
        setLoopVariable(execution, NUMBER_OF_REJECTED, 0);
        setLoopVariable(execution, NUMBER_OF_APPROVED, 0);
        return ret;
    }

    @Override
    public void leave(DelegateExecution execution) {
        String approveResult = String.valueOf(execution.getVariable(PROP_APPROVE_RESULT));
        DelegateExecution rootExecution = getMultiInstanceRootExecution(execution);
        if (isRejected(approveResult)) {
            setLoopVariable(rootExecution, NUMBER_OF_REJECTED, getLoopVariable(rootExecution, NUMBER_OF_REJECTED) + 1);
        } else if (isApproved(approveResult)) {
            setLoopVariable(rootExecution, NUMBER_OF_APPROVED, getLoopVariable(rootExecution, NUMBER_OF_APPROVED) + 1);
        }
        super.leave(execution);
    }
}
