package com.hand.hap.activiti.service;

import java.util.List;
import java.util.Map;

import org.activiti.engine.repository.Model;
import org.activiti.rest.common.api.DataResponse;
import org.activiti.rest.service.api.history.HistoricTaskInstanceQueryRequest;
import org.activiti.rest.service.api.runtime.process.ProcessInstanceCreateRequest;
import org.activiti.rest.service.api.runtime.process.ProcessInstanceResponse;
import org.activiti.rest.service.api.runtime.task.TaskQueryRequest;
import org.springframework.web.bind.annotation.RequestParam;

import com.hand.hap.activiti.dto.ActivitiNode;
import com.hand.hap.activiti.dto.TaskActionRequestExt;
import com.hand.hap.activiti.dto.TaskResponseExt;
import com.hand.hap.activiti.exception.TaskActionException;
import com.hand.hap.activiti.exception.WflSecurityException;
import com.hand.hap.core.IRequest;

/**
 * @author shengyang.zhou@hand-china.com
 */
public interface IActivitiService {

    ProcessInstanceResponse startProcess(IRequest iRequest, ProcessInstanceCreateRequest createRequest);

    Model deployModel(String modelId) throws Exception;

    void completeTask(IRequest request, String taskId, TaskActionRequestExt actionRequest) throws TaskActionException;

    void delegateTask(IRequest request, String taskId, TaskActionRequestExt actionRequest) throws TaskActionException;

    void resolveTask(IRequest request, String taskId, TaskActionRequestExt actionRequest) throws TaskActionException;

    void jumpTo(IRequest request, String taskId, TaskActionRequestExt actionRequest);

    void executeTaskAction(IRequest request, String taskId, TaskActionRequestExt taskActionRequest)
            throws TaskActionException;

    List<ActivitiNode> getProcessNodes(IRequest request, String processDefinitionId);

    List<ActivitiNode> getUserTaskFromModelSource(IRequest request, String modelId);

    String getEmployeeName(String userId);

    String getGroupName(String groupId);

    TaskResponseExt getTaskDetails(IRequest request, String taskId) throws WflSecurityException;

    DataResponse queryTaskList(IRequest iRequest, TaskQueryRequest taskQueryRequest,
                               Map<String, String> requestParams);

    DataResponse queryHistoricProcessInstance(IRequest iRequest, Map<String, String> params);

    DataResponse queryHistoricTaskInstances(IRequest iRequest, HistoricTaskInstanceQueryRequest queryRequest,
                                            @RequestParam Map<String, String> allRequestParams);
}
