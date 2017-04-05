package com.hand.hap.activiti.custom;

import com.hand.hap.activiti.dto.HistoricProcessInstanceResponseExt;
import com.hand.hap.activiti.dto.HistoricTaskInstanceResponseExt;
import com.hand.hap.activiti.dto.ProcessInstanceResponseExt;
import com.hand.hap.activiti.dto.TaskResponseExt;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.rest.service.api.RestResponseFactory;
import org.activiti.rest.service.api.RestUrlBuilder;
import org.activiti.rest.service.api.RestUrls;
import org.activiti.rest.service.api.engine.variable.RestVariable;
import org.activiti.rest.service.api.history.HistoricProcessInstanceResponse;
import org.activiti.rest.service.api.history.HistoricTaskInstanceResponse;
import org.activiti.rest.service.api.runtime.process.ProcessInstanceResponse;
import org.activiti.rest.service.api.runtime.task.TaskResponse;

import java.util.Map;

/**
 * @author shengyang.zhou@hand-china.com
 */
public class CustomRestResponseFactory extends RestResponseFactory {

    public TaskResponse createTaskResponse(Task task, RestUrlBuilder urlBuilder) {
        TaskResponseExt response = new TaskResponseExt(task);
        response.setUrl(urlBuilder.buildUrl(RestUrls.URL_TASK, task.getId()));

        // Add references to other resources, if needed
        if (response.getParentTaskId() != null) {
            response.setParentTaskUrl(urlBuilder.buildUrl(RestUrls.URL_TASK, response.getParentTaskId()));
        }
        if (response.getProcessDefinitionId() != null) {
            response.setProcessDefinitionUrl(urlBuilder.buildUrl(RestUrls.URL_PROCESS_DEFINITION, response.getProcessDefinitionId()));
        }
        if (response.getExecutionId() != null) {
            response.setExecutionUrl(urlBuilder.buildUrl(RestUrls.URL_EXECUTION, response.getExecutionId()));
        }
        if (response.getProcessInstanceId() != null) {
            response.setProcessInstanceUrl(urlBuilder.buildUrl(RestUrls.URL_PROCESS_INSTANCE, response.getProcessInstanceId()));
        }

        if (task.getProcessVariables() != null) {
            Map<String, Object> variableMap = task.getProcessVariables();
            for (String name : variableMap.keySet()) {
                response.addVariable(createRestVariable(name, variableMap.get(name), RestVariable.RestVariableScope.GLOBAL, task.getId(), VARIABLE_TASK, false, urlBuilder));
            }
        }
        if (task.getTaskLocalVariables() != null) {
            Map<String, Object> variableMap = task.getTaskLocalVariables();
            for (String name : variableMap.keySet()) {
                response.addVariable(createRestVariable(name, variableMap.get(name), RestVariable.RestVariableScope.LOCAL, task.getId(), VARIABLE_TASK, false, urlBuilder));
            }
        }

        return response;
    }

    public HistoricTaskInstanceResponse createHistoricTaskInstanceResponse(HistoricTaskInstance taskInstance, RestUrlBuilder urlBuilder) {
        HistoricTaskInstanceResponseExt result = new HistoricTaskInstanceResponseExt(taskInstance);
        if (taskInstance.getProcessDefinitionId() != null) {
            result.setProcessDefinitionUrl(urlBuilder.buildUrl(RestUrls.URL_PROCESS_DEFINITION, taskInstance.getProcessDefinitionId()));
        }
        result.setProcessInstanceId(taskInstance.getProcessInstanceId());
        if (taskInstance.getProcessInstanceId() != null) {
            result.setProcessInstanceUrl(urlBuilder.buildUrl(RestUrls.URL_HISTORIC_PROCESS_INSTANCE, taskInstance.getProcessInstanceId()));
        }
        result.setStartTime(taskInstance.getStartTime());
        result.setTaskDefinitionKey(taskInstance.getTaskDefinitionKey());
        result.setWorkTimeInMillis(taskInstance.getWorkTimeInMillis());
        result.setUrl(urlBuilder.buildUrl(RestUrls.URL_HISTORIC_TASK_INSTANCE, taskInstance.getId()));
        if (taskInstance.getProcessVariables() != null) {
            Map<String, Object> variableMap = taskInstance.getProcessVariables();
            for (String name : variableMap.keySet()) {
                result.addVariable(createRestVariable(name, variableMap.get(name), RestVariable.RestVariableScope.GLOBAL, taskInstance.getId(), VARIABLE_HISTORY_TASK, false, urlBuilder));
            }
        }
        if (taskInstance.getTaskLocalVariables() != null) {
            Map<String, Object> variableMap = taskInstance.getTaskLocalVariables();
            for (String name : variableMap.keySet()) {
                result.addVariable(createRestVariable(name, variableMap.get(name), RestVariable.RestVariableScope.LOCAL, taskInstance.getId(), VARIABLE_HISTORY_TASK, false, urlBuilder));
            }
        }
        return result;
    }
    public HistoricProcessInstanceResponse createHistoricProcessInstanceResponse(HistoricProcessInstance processInstance, RestUrlBuilder urlBuilder) {
        HistoricProcessInstanceResponseExt result = new HistoricProcessInstanceResponseExt();
        result.setProcessName(processInstance.getProcessDefinitionName());
        result.setBusinessKey(processInstance.getBusinessKey());
        result.setDeleteReason(processInstance.getDeleteReason());
        result.setDurationInMillis(processInstance.getDurationInMillis());
        result.setEndActivityId(processInstance.getEndActivityId());
        result.setEndTime(processInstance.getEndTime());
        result.setId(processInstance.getId());
        result.setProcessDefinitionId(processInstance.getProcessDefinitionId());
        result.setProcessDefinitionUrl(urlBuilder.buildUrl(RestUrls.URL_PROCESS_DEFINITION, processInstance.getProcessDefinitionId()));
        result.setStartActivityId(processInstance.getStartActivityId());
        result.setStartTime(processInstance.getStartTime());
        result.setStartUserId(processInstance.getStartUserId());
        result.setSuperProcessInstanceId(processInstance.getSuperProcessInstanceId());
        result.setUrl(urlBuilder.buildUrl(RestUrls.URL_HISTORIC_PROCESS_INSTANCE, processInstance.getId()));
        if (processInstance.getProcessVariables() != null) {
            Map<String, Object> variableMap = processInstance.getProcessVariables();
            for (String name : variableMap.keySet()) {
                result.addVariable(createRestVariable(name, variableMap.get(name), RestVariable.RestVariableScope.LOCAL, processInstance.getId(), VARIABLE_HISTORY_PROCESS, false, urlBuilder));
            }
        }
        result.setTenantId(processInstance.getTenantId());
        return result;
    }

    public ProcessInstanceResponse createProcessInstanceResponse(ProcessInstance processInstance, RestUrlBuilder urlBuilder) {
        ProcessInstanceResponseExt result = new ProcessInstanceResponseExt();
        result.setStartTime(processInstance.getStartTime());
        result.setStartUserId(processInstance.getStartUserId());
        result.setProcessDefinitionName(processInstance.getProcessDefinitionName());

        result.setActivityId(processInstance.getActivityId());
        result.setBusinessKey(processInstance.getBusinessKey());
        result.setId(processInstance.getId());
        result.setProcessDefinitionId(processInstance.getProcessDefinitionId());
        result.setProcessDefinitionUrl(urlBuilder.buildUrl(RestUrls.URL_PROCESS_DEFINITION, processInstance.getProcessDefinitionId()));
        result.setEnded(processInstance.isEnded());
        result.setSuspended(processInstance.isSuspended());
        result.setUrl(urlBuilder.buildUrl(RestUrls.URL_PROCESS_INSTANCE, processInstance.getId()));
        result.setTenantId(processInstance.getTenantId());

        // Added by Ryan Johnston
        if (processInstance.isEnded()) {
            // Process complete. Note the same in the result.
            result.setCompleted(true);
        } else {
            // Process not complete. Note the same in the result.
            result.setCompleted(false);
        }
        // End Added by Ryan Johnston

        if (processInstance.getProcessVariables() != null) {
            Map<String, Object> variableMap = processInstance.getProcessVariables();
            for (String name : variableMap.keySet()) {
                result.addVariable(createRestVariable(name, variableMap.get(name), RestVariable.RestVariableScope.LOCAL, processInstance.getId(), VARIABLE_PROCESS, false, urlBuilder));
            }
        }

        return result;
    }
}
