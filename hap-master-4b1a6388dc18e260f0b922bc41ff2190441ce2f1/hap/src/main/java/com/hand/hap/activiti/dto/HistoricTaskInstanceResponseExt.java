package com.hand.hap.activiti.dto;

import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Comment;
import org.activiti.rest.service.api.engine.CommentResponse;
import org.activiti.rest.service.api.history.HistoricTaskInstanceResponse;
import org.springframework.util.ReflectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shengyang.zhou@hand-china.com
 */
public class HistoricTaskInstanceResponseExt extends HistoricTaskInstanceResponse {
    private String comment;

    private String assigneeName;

    private String completeBy;

    private String action;

    public HistoricTaskInstanceResponseExt(HistoricTaskInstance taskInstance) {
        setAssignee(taskInstance.getAssignee());
        setClaimTime(taskInstance.getClaimTime());
        setDeleteReason(taskInstance.getDeleteReason());
        setDescription(taskInstance.getDescription());
        setDueDate(taskInstance.getDueDate());
        setDurationInMillis(taskInstance.getDurationInMillis());
        setEndTime(taskInstance.getEndTime());
        setExecutionId(taskInstance.getExecutionId());
        setFormKey(taskInstance.getFormKey());
        setId(taskInstance.getId());
        setName(taskInstance.getName());
        setOwner(taskInstance.getOwner());
        setParentTaskId(taskInstance.getParentTaskId());
        setPriority(taskInstance.getPriority());
        setProcessDefinitionId(taskInstance.getProcessDefinitionId());
        setTenantId(taskInstance.getTenantId());
        setCategory(taskInstance.getCategory());
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAssigneeName() {
        return assigneeName;
    }

    public void setAssigneeName(String assigneeName) {
        this.assigneeName = assigneeName;
    }

    public String getCompleteBy() {
        return completeBy;
    }

    public void setCompleteBy(String completeBy) {
        this.completeBy = completeBy;
    }
}
