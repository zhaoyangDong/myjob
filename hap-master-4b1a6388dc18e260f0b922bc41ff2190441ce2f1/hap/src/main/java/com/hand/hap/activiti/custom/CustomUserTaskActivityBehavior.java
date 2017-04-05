/*
 * Copyright Hand China Co.,Ltd.
 */

package com.hand.hap.activiti.custom;

import java.util.Collections;

import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.apache.commons.lang.StringUtils;

import com.hand.hap.activiti.dto.ApproveChainLine;
import com.hand.hap.activiti.service.impl.HapApproveChain;

/**
 * @author shengyang.zhou@hand-china.com
 */
public class CustomUserTaskActivityBehavior extends UserTaskActivityBehavior {

    public CustomUserTaskActivityBehavior(UserTask userTask) {
        super(userTask);
    }

    @Override
    public void execute(DelegateExecution execution) {
        if (HapApproveChain.CURRENT_LINE.get() == null) {
            super.execute(execution);
            return;
        }

        new UserTaskActivityBehavior(byApproveChain(this.userTask)).execute(execution);
    }

    private UserTask byApproveChain(UserTask task) {
        ApproveChainLine currentLine = HapApproveChain.CURRENT_LINE.get();
        HapApproveChain.CURRENT_LINE.remove();
        if (currentLine == null) {
            return task;
        }
        UserTask userTask = task.clone();

        userTask.setName(task.getName() + " - " + currentLine.getName());

        if (StringUtils.isNotEmpty(currentLine.getAssignee())) {
            userTask.setAssignee(currentLine.getAssignee());
        } else if (StringUtils.isNotEmpty(currentLine.getAssignGroup())) {
            userTask.setCandidateGroups(Collections.singletonList(currentLine.getAssignGroup()));
        }

        if (StringUtils.isNotEmpty(currentLine.getFormKey())) {
            userTask.setFormKey(currentLine.getFormKey());
        }

        if (StringUtils.isEmpty(userTask.getDocumentation())) {
            userTask.setDocumentation(currentLine.getName());
        }
        return userTask;
    }

}
