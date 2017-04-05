package com.hand.hap.activiti.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.UserTask;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.FormData;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.impl.persistence.entity.data.GroupDataManager;
import org.activiti.engine.impl.persistence.entity.data.UserDataManager;
import org.activiti.engine.impl.util.ProcessDefinitionUtil;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.DelegationState;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.activiti.rest.common.api.DataResponse;
import org.activiti.rest.service.api.RestResponseFactory;
import org.activiti.rest.service.api.engine.variable.RestVariable;
import org.activiti.rest.service.api.history.HistoricProcessInstanceCollectionResource;
import org.activiti.rest.service.api.history.HistoricTaskInstanceQueryRequest;
import org.activiti.rest.service.api.history.HistoricTaskInstanceQueryResource;
import org.activiti.rest.service.api.runtime.process.ExecutionVariableCollectionResource;
import org.activiti.rest.service.api.runtime.process.ProcessInstanceCollectionResource;
import org.activiti.rest.service.api.runtime.process.ProcessInstanceCreateRequest;
import org.activiti.rest.service.api.runtime.process.ProcessInstanceResponse;
import org.activiti.rest.service.api.runtime.task.TaskActionRequest;
import org.activiti.rest.service.api.runtime.task.TaskQueryRequest;
import org.activiti.rest.service.api.runtime.task.TaskQueryResource;
import org.activiti.rest.service.api.runtime.task.TaskResource;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hand.hap.activiti.core.IActivitiConstants;
import com.hand.hap.activiti.custom.JumpActivityCmd;
import com.hand.hap.activiti.dto.ActivitiNode;
import com.hand.hap.activiti.dto.HistoricProcessInstanceResponseExt;
import com.hand.hap.activiti.dto.HistoricTaskInstanceResponseExt;
import com.hand.hap.activiti.dto.ProcessInstanceResponseExt;
import com.hand.hap.activiti.dto.TaskActionRequestExt;
import com.hand.hap.activiti.dto.TaskDelegate;
import com.hand.hap.activiti.dto.TaskResponseExt;
import com.hand.hap.activiti.exception.TaskActionException;
import com.hand.hap.activiti.exception.WflSecurityException;
import com.hand.hap.activiti.service.IActivitiService;
import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.util.StringUtil;

/**
 * @author shengyang.zhou@hand-china.com
 */
@Service
public class ActivitiServiceImpl implements IActivitiService, IActivitiConstants, InitializingBean {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private GroupDataManager groupDataManager;

    @Autowired
    private UserDataManager userDataManager;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private FormService formService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private RestResponseFactory restResponseFactory;

    @Autowired
    private ProcessEngineConfigurationImpl processEngineConfiguration;

    /* 以下 为 手动注入的 bean */
    private TaskResource taskResource = new TaskResource();

    private ExecutionVariableCollectionResource executionVariableCollectionResource = new ExecutionVariableCollectionResource();

    private TaskQueryResource taskQueryResource = new TaskQueryResource();

    private ProcessInstanceCollectionResource processInstanceCollectionResource = new ProcessInstanceCollectionResource();

    private HistoricTaskInstanceQueryResource historicTaskInstanceQueryResource = new HistoricTaskInstanceQueryResource();

    private HistoricProcessInstanceCollectionResource historicProcessInstanceCollectionResource = new HistoricProcessInstanceCollectionResource();

    /* Fake request,response,used to call rest api */
    private HttpServletRequest fakeRequest = new MockHttpServletRequest();
    private HttpServletResponse fakeResponse = new MockHttpServletResponse();

    @Override
    public ProcessInstanceResponse startProcess(IRequest iRequest, ProcessInstanceCreateRequest createRequest) {
        try {
            String employeeCode = iRequest.getEmployeeCode();
            Authentication.setAuthenticatedUserId(employeeCode);
            return processInstanceCollectionResource.createProcessInstance(createRequest, fakeRequest, fakeResponse);
        } finally {
            Authentication.setAuthenticatedUserId(null);
        }
    }

    public DataResponse getInvolvedProcess(IRequest request, Map<String, String> allParameters) {
        return processInstanceCollectionResource.getProcessInstances(allParameters, fakeRequest);
    }

    @Override
    public Model deployModel(String modelId) throws Exception {
        Model model = repositoryService.getModel(modelId);

        byte[] modelData = repositoryService.getModelEditorSource(modelId);
        JsonNode jsonNode = objectMapper.readTree(modelData);
        BpmnModel bpmnModel = new BpmnJsonConverter().convertToBpmnModel(jsonNode);

        // byte[] xmlBytes = new BpmnXMLConverter().convertToXML(bpmnModel,
        // "UTF-8");

        Deployment deploy = repositoryService.createDeployment().category(model.getCategory()).name(model.getName())
                .key(model.getKey()).addBpmnModel(model.getKey() + ".bpmn20.xml", bpmnModel).deploy();

        model.setDeploymentId(deploy.getId());
        repositoryService.saveModel(model);
        return model;

    }

    @Override
    public void completeTask(IRequest request, String taskId, TaskActionRequestExt actionRequest)
            throws TaskActionException {
        if (!TaskActionRequest.ACTION_COMPLETE.equalsIgnoreCase(actionRequest.getAction())) {
            return;
        }
        Authentication.setAuthenticatedUserId(request.getEmployeeCode());
        Task taskEntity = getTaskById(taskId);
        String action = null;// 本次操作执行的动作

        List<RestVariable> vars = actionRequest.getVariables();
        if (vars != null) {
            for (RestVariable rv : vars) {
                if (PROP_APPROVE_RESULT.equalsIgnoreCase(rv.getName())) {
                    action = String.valueOf(rv.getValue());
                    break;
                }
            }
        }
        if (StringUtils.isEmpty(taskEntity.getAssignee())) {
            actionRequest.setAssignee(request.getEmployeeCode());
            // 自动 claim
            taskService.claim(taskId, request.getEmployeeCode());
            taskService.addComment(taskId, null, COMMENT_ACTION, action);
            taskService.addComment(taskId, null, PROP_COMMENT, actionRequest.getComment());
            taskResource.executeTaskAction(taskId, actionRequest);
        } else if (hasRight(request.getEmployeeCode(), taskEntity.getAssignee())) {
            actionRequest.setAssignee(request.getEmployeeCode());
            taskService.addComment(taskId, null, COMMENT_ACTION, action);
            taskService.addComment(taskId, null, PROP_COMMENT, actionRequest.getComment());
            taskResource.executeTaskAction(taskId, actionRequest);
        } else {
            throw new TaskActionException(TaskActionException.COMPLETE_TASK_NEED_ASSIGNEE_OR_ADMIN);
        }
    }

    @Override
    public void delegateTask(IRequest request, String taskId, TaskActionRequestExt actionRequest)
            throws TaskActionException {
        if (!TaskActionRequest.ACTION_DELEGATE.equalsIgnoreCase(actionRequest.getAction())) {
            return;
        }
        Authentication.setAuthenticatedUserId(request.getEmployeeCode());
        String assignee = actionRequest.getAssignee();
        if (StringUtils.isEmpty(assignee)) {
            throw new TaskActionException(TaskActionException.DELEGATE_NO_ASSIGNEE);
        }
        Task taskEntity = getTaskById(taskId);

        DelegationState state = taskEntity.getDelegationState();
        if (state != null && state == DelegationState.PENDING) {
            // 正在转交中
            throw new TaskActionException(TaskActionException.DELEGATE_IN_PENDING);
        }

        if (taskEntity.getOwner() != null) {

            if (eq(taskEntity.getOwner(), assignee)) {
                throw new TaskActionException(TaskActionException.DELEGATE_TO_OWNER);
            }

            if (!hasRight(request.getEmployeeCode(), taskEntity.getOwner())) {
                throw new TaskActionException(TaskActionException.DELEGATE_NEED_OWNER_OR_ADMIN);
            }
        }

        taskEntity.setOwner(assignee);// change owner when delegate
        taskService.saveTask(taskEntity);

        if (StringUtils.isEmpty(taskEntity.getAssignee())) {
            // actionRequest.setAssignee(request.getEmployeeCode());
            taskService.addComment(taskId, null, COMMENT_DELEGATE_BY, actionRequest.getComment());
            taskResource.executeTaskAction(taskId, actionRequest);
        } else if (hasRight(request.getEmployeeCode(), taskEntity.getAssignee())) {
            // actionRequest.setAssignee(request.getEmployeeCode());
            taskService.addComment(taskId, null, COMMENT_DELEGATE_BY, actionRequest.getComment());
            taskResource.executeTaskAction(taskId, actionRequest);
        } else {
            throw new TaskActionException(TaskActionException.COMPLETE_TASK_NEED_ASSIGNEE_OR_ADMIN);
        }
    }

    @Override
    public void resolveTask(IRequest request, String taskId, TaskActionRequestExt actionRequest)
            throws TaskActionException {
        if (!TaskActionRequest.ACTION_RESOLVE.equalsIgnoreCase(actionRequest.getAction())) {
            return;
        }
        Authentication.setAuthenticatedUserId(request.getEmployeeCode());

        Task taskEntity = getTaskById(taskId);

        if (!hasRight(request.getEmployeeCode(), taskEntity.getOwner())) {
            throw new TaskActionException(TaskActionException.RESOLVE_NEED_OWNER_OR_ADMIN);
        }
        taskResource.executeTaskAction(taskId, actionRequest);

    }

    @Override
    public void jumpTo(IRequest request, String taskId, TaskActionRequestExt actionRequest) {
        JumpActivityCmd cmd = new JumpActivityCmd(taskId, actionRequest.getJumpTarget());
        processEngineConfiguration.getCommandExecutor().execute(cmd);
    }

    protected boolean hasRight(String a, String b) {
        return isAdmin(a) || eq(a, b);
    }

    protected boolean eq(Object o1, Object o2) {
        return o1 == null ? o2 == null : o1.equals(o2);
    }

    @Override
    public void executeTaskAction(IRequest request, String taskId, TaskActionRequestExt actionRequest)
            throws TaskActionException {
        if (StringUtils.isEmpty(actionRequest.getAction())) {
            throw new IllegalArgumentException("Action is required.");
        }
        Authentication.setAuthenticatedUserId(request.getEmployeeCode());
        if (TaskActionRequest.ACTION_COMPLETE.equalsIgnoreCase(actionRequest.getAction())) {
            completeTask(request, taskId, actionRequest);
            return;
        }
        if (TaskActionRequest.ACTION_DELEGATE.equalsIgnoreCase(actionRequest.getAction())) {
            delegateTask(request, taskId, actionRequest);
            return;
        }

        if (TaskActionRequest.ACTION_RESOLVE.equalsIgnoreCase(actionRequest.getAction())) {
            resolveTask(request, taskId, actionRequest);
            return;
        }

        if (ACTION_JUMP.equalsIgnoreCase(actionRequest.getAction())) {
            jumpTo(request, taskId, actionRequest);
            return;
        }

    }

    @Override
    public List<ActivitiNode> getProcessNodes(IRequest request, String processDefinitionId) {
        org.activiti.bpmn.model.Process process = ProcessDefinitionUtil.getProcess(processDefinitionId);
        List<ActivitiNode> list = new ArrayList<>();
        Collection<FlowElement> eles = process.getFlowElements();
        for (FlowElement fe : eles) {
            if (fe instanceof UserTask) {
                ActivitiNode node = new ActivitiNode();
                node.setName(fe.getName());
                node.setNodeId(fe.getId());
                node.setType("UserTask");
                list.add(node);
            }
        }
        return list;
    }

    @Override
    public List<ActivitiNode> getUserTaskFromModelSource(IRequest request, String modelId) {
        List<ActivitiNode> list = new ArrayList<>();
        byte[] data = repositoryService.getModelEditorSource(modelId);
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(data);
            BpmnModel bpmnModel = new BpmnJsonConverter().convertToBpmnModel(jsonNode);
            Process process = bpmnModel.getMainProcess();
            Collection<FlowElement> elements = process.getFlowElements();
            for (FlowElement flowElement : elements) {
                if (flowElement instanceof UserTask) {
                    ActivitiNode node = new ActivitiNode();
                    node.setNodeId(flowElement.getId());
                    node.setName(flowElement.getName());
                    node.setType("UserTask");
                    list.add(node);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public String getEmployeeName(String userId) {
        UserEntity userEntity = userDataManager.findById(userId);
        if (userEntity != null) {
            return userEntity.getFirstName();
        }
        return userId;
    }

    @Override
    public String getGroupName(String groupId) {
        Group group = groupDataManager.findById(groupId);
        if (group != null) {
            return group.getName();
        }
        return groupId;
    }

    @Override
    public TaskResponseExt getTaskDetails(IRequest request, String taskId) throws WflSecurityException {
        Task task = getTaskById(taskId);

        TaskResponseExt taskExt = new TaskResponseExt(task);

        List<Group> userGroup = null;

        // display name of assignee or group
        if (StringUtils.isNotEmpty(taskExt.getAssignee())) {
            // privilege check
            if (!hasRight(request.getEmployeeCode(), taskExt.getAssignee())) {
                throw new WflSecurityException(WflSecurityException.NEED_ASSIGNEE_OR_ADMIN);
            }
            taskExt.setAssigneeName(getEmployeeName(taskExt.getAssignee()));
        } else {
            List<IdentityLink> idList = taskService.getIdentityLinksForTask(task.getId());
            List<String> nameList = new ArrayList<>();
            boolean isCandi = isAdmin(request.getEmployeeCode());
            for (IdentityLink il : idList) {
                if (il.getGroupId() != null) {
                    // privilege check
                    if (!isCandi) {
                        if (userGroup == null) {
                            userGroup = processEngineConfiguration.getUserDataManager()
                                    .findGroupsByUser(request.getEmployeeCode());
                        }
                        for (Group g : userGroup) {
                            if (eq(g.getId(), il.getGroupId())) {
                                isCandi = true;
                                break;
                            }
                        }
                    }
                    // privilege check end

                    nameList.add(getGroupName(il.getGroupId()));
                } else if (il.getUserId() != null) {
                    if (!isCandi && eq(request.getEmployeeCode(), il.getUserId())) {
                        // privilege check
                        isCandi = true;
                    }
                    nameList.add(getEmployeeName(il.getUserId()));
                }
            }
            if (!isCandi) {
                if (!hasRight(request.getEmployeeCode(), taskExt.getAssignee())) {
                    throw new WflSecurityException(WflSecurityException.NEED_ASSIGNEE_OR_ADMIN);
                }
            }
            taskExt.setAssigneeName(StringUtils.join(nameList.toArray(), ";"));
        }

        // attachment
        List<org.activiti.engine.task.Attachment> attaList = taskService.getTaskAttachments(taskId);
        taskExt.setAttachments(attaList);

        // form data:formVariables
        FormData formData = formService.getTaskFormData(taskId);
        taskExt.setFormData(restResponseFactory.createFormDataResponse(formData));

        // approve history
        List<HistoricTaskInstance> historicTaskInstanceList = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(task.getProcessInstanceId()).finished().list();
        for (HistoricTaskInstance aHistoricTaskInstanceList : historicTaskInstanceList) {
            HistoricTaskInstanceResponseExt taskHistory = new HistoricTaskInstanceResponseExt(
                    aHistoricTaskInstanceList);
            if (StringUtil.isNotEmpty(taskHistory.getAssignee())) {
                taskHistory.setAssigneeName(getEmployeeName(taskHistory.getAssignee()));
            }

            taskHistory.setComment(getCommentOfType(taskHistory.getId(), PROP_COMMENT));
            taskHistory.setAction(getCommentOfType(taskHistory.getId(), COMMENT_ACTION));

            taskExt.getHistoricTaskList().add(taskHistory);
        }

        // delegate
        List<Comment> comments = taskService.getTaskComments(task.getId(), COMMENT_DELEGATE_BY);
        if (comments.size() > 0) {
            Comment comment = comments.get(comments.size() - 1);
            TaskDelegate taskDelegate = new TaskDelegate();

            taskDelegate.setFromUserId(comment.getUserId());
            taskDelegate.setFromUserName(getEmployeeName(comment.getUserId()));
            taskDelegate.setTime(comment.getTime());
            taskDelegate.setReason(comment.getFullMessage());
            taskExt.setTaskDelegate(taskDelegate);
        }

        // processInstance
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(task.getProcessInstanceId()).singleResult();
        ProcessInstanceResponseExt processInstanceResp = (ProcessInstanceResponseExt) restResponseFactory
                .createProcessInstanceResponse(processInstance);
        processInstanceResp.setStartUserName(getEmployeeName(processInstanceResp.getStartUserId()));
        taskExt.setProcessInstance(processInstanceResp);

        // execution variable
        List<RestVariable> vars = executionVariableCollectionResource.getVariables(task.getExecutionId(), null,
                fakeRequest);

        taskExt.setExecutionVariables(vars);
        return taskExt;
    }

    @Override
    public DataResponse queryTaskList(IRequest iRequest, TaskQueryRequest taskQueryRequest,
            Map<String, String> requestParams) {
        DataResponse dataResponse = taskQueryResource.getQueryResult(taskQueryRequest, requestParams, fakeRequest);
        List<TaskResponseExt> list = (List<TaskResponseExt>) dataResponse.getData();
        for (TaskResponseExt taskResponse : list) {
            if (StringUtils.isNotEmpty(taskResponse.getOwner())) {
                taskResponse.setOwner(getEmployeeName(taskResponse.getOwner()));
            }
            if (StringUtils.isNotEmpty(taskResponse.getAssignee())) {
                taskResponse.setAssigneeName(getEmployeeName(taskResponse.getAssignee()));
            } else {
                List<IdentityLink> idList = taskService.getIdentityLinksForTask(taskResponse.getId());
                List<String> nameList = new ArrayList<>();
                for (IdentityLink il : idList) {
                    if (il.getGroupId() != null) {
                        nameList.add(getGroupName(il.getGroupId()));
                    } else if (il.getUserId() != null) {
                        nameList.add(getEmployeeName(il.getUserId()));
                    }
                }
                taskResponse.setAssigneeName(StringUtils.join(nameList.toArray(), ";"));
            }
            ProcessInstance procInstance = runtimeService.createProcessInstanceQuery()
                    .processInstanceId(taskResponse.getProcessInstanceId()).list().iterator().next();
            taskResponse.setProcessName(procInstance.getProcessDefinitionName());

            taskResponse.setStartUserId(procInstance.getStartUserId());
            taskResponse.setStartUserName(getEmployeeName(procInstance.getStartUserId()));
        }
        return dataResponse;
    }

    @Override
    public DataResponse queryHistoricProcessInstance(IRequest iRequest, Map<String, String> params) {

        if ("involve".equalsIgnoreCase(params.get("queryType"))) {
            params.put("involvedUser", iRequest.getEmployeeCode());
            params.remove("startedBy");
        } else if ("create".equalsIgnoreCase(params.get("queryType"))) {
            params.put("startedBy", iRequest.getEmployeeCode());
            params.remove("involvedUser");
        } else if ("any".equalsIgnoreCase(params.get("queryType"))) {
            if (!isAdmin(iRequest.getEmployeeCode())) {
                throw new RuntimeException(new WflSecurityException(WflSecurityException.NEED_ASSIGNEE_OR_ADMIN));
            }
        }

        DataResponse dataResponse = historicProcessInstanceCollectionResource.getHistoricProcessInstances(params,
                fakeRequest);
        List<HistoricProcessInstanceResponseExt> list = (List<HistoricProcessInstanceResponseExt>) dataResponse
                .getData();
        for (HistoricProcessInstanceResponseExt his : list) {
            if (StringUtils.isNotEmpty(his.getStartUserId())) {
                his.setStartUserName(getEmployeeName(his.getStartUserId()));
            }
            // 最后审批人和操作
            List<HistoricTaskInstance> historicTaskInstanceList = historyService.createHistoricTaskInstanceQuery()
                    .processInstanceId(his.getId()).finished().orderByHistoricTaskInstanceEndTime().desc().list();
            if (historicTaskInstanceList != null && historicTaskInstanceList.size() > 0) {
                for (int i = 0; i < historicTaskInstanceList.size(); i++) {
                    HistoricTaskInstanceResponseExt taskLastResponse = new HistoricTaskInstanceResponseExt(
                            historicTaskInstanceList.get(i));
                    if (StringUtil.isNotEmpty(taskLastResponse.getAssignee())
                            && StringUtil.isNotEmpty(getCommentOfType(taskLastResponse.getId(), COMMENT_ACTION))) {
                        if (StringUtil.isNotEmpty(getEmployeeName(taskLastResponse.getAssignee()))) {
                            his.setLastApprover(getEmployeeName(taskLastResponse.getAssignee()));
                        } else {
                            his.setLastApprover(taskLastResponse.getAssignee());
                        }
                        his.setLastApproveAction(getCommentOfType(taskLastResponse.getId(), COMMENT_ACTION));
                        break;
                    }
                }
            }
        }
        return dataResponse;
    }

    @Override
    public DataResponse queryHistoricTaskInstances(IRequest iRequest, HistoricTaskInstanceQueryRequest queryRequest,
            Map<String, String> allRequestParams) {
        if (allRequestParams == null) {
            allRequestParams = Collections.emptyMap();
        }
        DataResponse dataResponse = historicTaskInstanceQueryResource.queryProcessInstances(queryRequest,
                allRequestParams, fakeRequest);
        List<HistoricTaskInstanceResponseExt> list = (List<HistoricTaskInstanceResponseExt>) dataResponse.getData();
        for (HistoricTaskInstanceResponseExt taskInstanceResponse : list) {
            if (StringUtil.isNotEmpty(taskInstanceResponse.getAssignee())) {
                String assigneeName = getEmployeeName(taskInstanceResponse.getAssignee());
                if (StringUtil.isNotEmpty(assigneeName)) {
                    taskInstanceResponse.setAssigneeName(assigneeName);
                } else {
                    taskInstanceResponse.setAssigneeName(taskInstanceResponse.getAssignee());
                }
            }

            taskInstanceResponse.setComment(getCommentOfType(taskInstanceResponse.getId(), PROP_COMMENT));
            taskInstanceResponse.setAction(getCommentOfType(taskInstanceResponse.getId(), COMMENT_ACTION));
        }
        return dataResponse;
    }

    protected boolean isAdmin(String userId) {
        return "ADMIN".equalsIgnoreCase(userId);
    }

    protected Task getTaskById(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            throw new ActivitiObjectNotFoundException("Could not find a task with id '" + taskId + "'.", Task.class);
        }
        return task;
    }

    protected String getCommentOfType(String taskId, String type) {
        List<Comment> list = taskService.getTaskComments(taskId, type);
        if (list == null || list.size() == 0) {
            return null;
        }
        return list.get(list.size() - 1).getFullMessage();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        AutowireCapableBeanFactory autowireCapableBeanFactory = applicationContext.getAutowireCapableBeanFactory();
        autowireCapableBeanFactory.autowireBean(taskResource);
        autowireCapableBeanFactory.autowireBean(executionVariableCollectionResource);
        autowireCapableBeanFactory.autowireBean(taskQueryResource);
        autowireCapableBeanFactory.autowireBean(processInstanceCollectionResource);
        autowireCapableBeanFactory.autowireBean(historicTaskInstanceQueryResource);
        autowireCapableBeanFactory.autowireBean(historicProcessInstanceCollectionResource);
    }
}
