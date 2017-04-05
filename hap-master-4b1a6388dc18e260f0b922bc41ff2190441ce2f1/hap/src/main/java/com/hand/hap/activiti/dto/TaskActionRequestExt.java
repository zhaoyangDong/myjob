package com.hand.hap.activiti.dto;


import org.activiti.rest.service.api.runtime.task.TaskActionRequest;

/**
 * @author shengyang.zhou@hand-china.com
 */
public class TaskActionRequestExt extends TaskActionRequest {

    private String comment;

    private String jumpTarget;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getJumpTarget() {
        return jumpTarget;
    }

    public void setJumpTarget(String jumpTarget) {
        this.jumpTarget = jumpTarget;
    }
}
