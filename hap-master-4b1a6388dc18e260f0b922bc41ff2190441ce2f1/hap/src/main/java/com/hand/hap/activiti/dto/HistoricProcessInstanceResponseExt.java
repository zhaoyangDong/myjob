package com.hand.hap.activiti.dto;

import org.activiti.rest.service.api.history.HistoricProcessInstanceResponse;

/**
 * @author shengyang.zhou@hand-china.com
 */
public class HistoricProcessInstanceResponseExt extends HistoricProcessInstanceResponse {
    private String processName;

    private String startUserName;

    // 最后审批人
    private String lastApprover;
    // 最后审批操作
    private String lastApproveAction;

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getStartUserName() {
        return startUserName;
    }

    public void setStartUserName(String startUserName) {
        this.startUserName = startUserName;
    }

    public String getLastApprover() {
        return lastApprover;
    }

    public void setLastApprover(String lastApprover) {
        this.lastApprover = lastApprover;
    }

    public String getLastApproveAction() {
        return lastApproveAction;
    }

    public void setLastApproveAction(String lastApproveAction) {
        this.lastApproveAction = lastApproveAction;
    }
}
