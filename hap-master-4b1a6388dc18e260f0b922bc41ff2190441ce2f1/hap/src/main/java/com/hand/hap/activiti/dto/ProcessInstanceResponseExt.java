package com.hand.hap.activiti.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.activiti.rest.common.util.DateToStringSerializer;
import org.activiti.rest.service.api.runtime.process.ProcessInstanceResponse;

import java.util.Date;

/**
 * @author shengyang.zhou@hand-china.com
 */
public class ProcessInstanceResponseExt extends ProcessInstanceResponse {

    @JsonSerialize(using = DateToStringSerializer.class, as = Date.class)
    private Date startTime;
    private String startUserId;
    private String startUserName;
    private String processDefinitionName;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getStartUserId() {
        return startUserId;
    }

    public void setStartUserId(String startUserId) {
        this.startUserId = startUserId;
    }

    public String getStartUserName() {
        return startUserName;
    }

    public void setStartUserName(String startUserName) {
        this.startUserName = startUserName;
    }

    public String getProcessDefinitionName() {
        return processDefinitionName;
    }

    public void setProcessDefinitionName(String processDefinitionName) {
        this.processDefinitionName = processDefinitionName;
    }
}
