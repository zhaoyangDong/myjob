package com.hand.hap.activiti.dto;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.task.Attachment;
import org.activiti.engine.task.Task;
import org.activiti.rest.service.api.engine.variable.RestVariable;
import org.activiti.rest.service.api.form.FormDataResponse;
import org.activiti.rest.service.api.history.HistoricTaskInstanceResponse;
import org.activiti.rest.service.api.runtime.process.ProcessInstanceResponse;
import org.activiti.rest.service.api.runtime.task.TaskResponse;

/**
 * @author shengyang.zhou@hand-china.com
 */
public class TaskResponseExt extends TaskResponse {

    private String processName;

    private String assigneeName;

    private String startUserId;
    private String startUserName;

    private TaskDelegate taskDelegate;

    private List<Attachment> attachments;

    private FormDataResponse formData;
    private List<HistoricTaskInstanceResponse> historicTaskList = new ArrayList<>();
    private List<RestVariable> executionVariables;
    private ProcessInstanceResponse processInstance;

    public TaskResponseExt(Task task) {
        super(task);
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getAssigneeName() {
        return assigneeName;
    }

    public void setAssigneeName(String assigneeName) {
        this.assigneeName = assigneeName;
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

    public TaskDelegate getTaskDelegate() {
        return taskDelegate;
    }

    public void setTaskDelegate(TaskDelegate taskDelegate) {
        this.taskDelegate = taskDelegate;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public FormDataResponse getFormData() {
        return formData;
    }

    public void setFormData(FormDataResponse formData) {
        this.formData = formData;
    }

    public List<HistoricTaskInstanceResponse> getHistoricTaskList() {
        return historicTaskList;
    }

    public void setHistoricTaskList(List<HistoricTaskInstanceResponse> historicTaskList) {
        this.historicTaskList = historicTaskList;
    }

    public void setExecutionVariables(List<RestVariable> executionVariables) {
        this.executionVariables = executionVariables;
    }

    public List<RestVariable> getExecutionVariables() {
        return executionVariables;
    }

    public void setProcessInstance(ProcessInstanceResponse processInstanceInfo) {
        this.processInstance = processInstanceInfo;
    }

    public ProcessInstanceResponse getProcessInstance() {
        return processInstance;
    }
}
