package com.hand.hap.activiti.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.impl.util.io.InputStreamSource;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.rest.common.api.DataResponse;
import org.activiti.rest.service.api.RestResponseFactory;
import org.activiti.rest.service.api.history.HistoricTaskInstanceQueryRequest;
import org.activiti.rest.service.api.repository.ModelRequest;
import org.activiti.rest.service.api.repository.ModelResponse;
import org.activiti.rest.service.api.runtime.process.ProcessInstanceCreateRequest;
import org.activiti.rest.service.api.runtime.process.ProcessInstanceResponse;
import org.activiti.rest.service.api.runtime.task.TaskQueryRequest;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hand.hap.activiti.core.IActivitiConstants;
import com.hand.hap.activiti.dto.ActivitiNode;
import com.hand.hap.activiti.dto.TaskActionRequestExt;
import com.hand.hap.activiti.dto.TaskResponseExt;
import com.hand.hap.activiti.exception.WflSecurityException;
import com.hand.hap.activiti.service.IActivitiEntityService;
import com.hand.hap.activiti.service.IActivitiService;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;

/**
 * @author shengyang.zhou@hand-china.com
 */
@Controller
@RequestMapping(value = "/wfl")
public class ActivitiController extends BaseController {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private RestResponseFactory restResponseFactory;

    @Autowired
    private IActivitiEntityService customEntityService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IActivitiService activitiService;

    @Autowired
    protected ProcessEngineConfiguration processEngineConfiguration;

    @Autowired
    private HistoryService historyService;

    /**
     * 创建流程,使用当前登录用户.
     *
     * @param request
     * @param httpRequest
     * @param response
     * @return
     */
    @RequestMapping(value = "/runtime/process-instances", method = RequestMethod.POST, produces = "application/json")
    public ProcessInstanceResponse createProcessInstance(@RequestBody ProcessInstanceCreateRequest request,
            HttpServletRequest httpRequest, HttpServletResponse response) {
        IRequest iRequest = createRequestContext(httpRequest);
        String employeeCode = httpRequest.getParameter("userId");
        if (StringUtils.isNotEmpty(employeeCode) && "ADMIN".equalsIgnoreCase(iRequest.getEmployeeCode())) {
            // allow ADMIN to simulate other user
            // FOR TEST ONLY
            iRequest.setEmployeeCode(employeeCode);
        }
        return activitiService.startProcess(iRequest, request);
    }

    @RequestMapping(value = "/definition/user-tasks", method = RequestMethod.GET)
    @ResponseBody
    public List<ActivitiNode> getAvailableUserTask(HttpServletRequest request, String processDefinitionId) {
        IRequest iRequest = createRequestContext(request);
        return activitiService.getProcessNodes(iRequest, processDefinitionId);
    }

    /**
     * 待办事项,个人（强制根据员工号过滤）
     *
     * @param request
     * @param requestParams
     * @param httpRequest
     * @return
     */
    @RequestMapping(value = "/query/tasks", method = RequestMethod.POST, produces = "application/json")
    public DataResponse getQueryResult(@RequestBody TaskQueryRequest request,
            @RequestParam Map<String, String> requestParams, HttpServletRequest httpRequest) {
        IRequest iRequest = createRequestContext(httpRequest);
        request.setCandidateOrAssigned(iRequest.getEmployeeCode());
        DataResponse dataResponse = activitiService.queryTaskList(iRequest, request, requestParams);
        return dataResponse;
    }

    /**
     * 待办事项，管理员用(可以任意查询)
     *
     * @param request
     * @param requestParams
     * @param httpRequest
     * @return
     */
    @RequestMapping(value = "/query/tasks/admin", method = RequestMethod.POST, produces = "application/json")
    public DataResponse taskListAdmin(@RequestBody TaskQueryRequest request,
            @RequestParam Map<String, String> requestParams, HttpServletRequest httpRequest) {

        IRequest iRequest = createRequestContext(httpRequest);
        DataResponse dataResponse = activitiService.queryTaskList(iRequest, request, requestParams);
        return dataResponse;
    }

    /**
     * 审批记录
     *
     * @param queryRequest
     * @param allRequestParams
     * @param request
     * @return
     */
    @RequestMapping(value = "/query/historic-task-instances")
    public DataResponse queryProcessInstances(HistoricTaskInstanceQueryRequest queryRequest,
            @RequestParam Map<String, String> allRequestParams, HttpServletRequest request) {

        IRequest iRequest = createRequestContext(request);
        return activitiService.queryHistoricTaskInstances(iRequest, queryRequest, allRequestParams);
    }

    /**
     * 流程历史
     *
     * @param allRequestParams
     * @param request
     * @return
     */
    @RequestMapping(value = "/query/historic-process-instances")
    public DataResponse queryProcessInstances(@RequestParam Map<String, String> allRequestParams,
            HttpServletRequest request) {

        IRequest iRequest = createRequestContext(request);
        return activitiService.queryHistoricProcessInstance(iRequest, allRequestParams);
    }

    /**
     * 完成,转交...任务
     *
     * @param taskId
     * @param actionRequest
     * @param request
     * @param response
     */
    @RequestMapping(value = "/runtime/tasks/{taskId}", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void executeTaskAction(@PathVariable String taskId, @RequestBody TaskActionRequestExt actionRequest,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        IRequest iRequest = createRequestContext(request);
        activitiService.executeTaskAction(iRequest, taskId, actionRequest);
    }

    /**
     * 新建模型(editor)
     *
     * @param modelRequest
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/repository/models", method = RequestMethod.POST, produces = "application/json")
    public ModelResponse createModel(@RequestBody ModelRequest modelRequest, HttpServletRequest request,
            HttpServletResponse response) {
        Model model = repositoryService.newModel();
        model.setCategory(modelRequest.getCategory());
        model.setDeploymentId(modelRequest.getDeploymentId());
        model.setKey(modelRequest.getKey());
        model.setMetaInfo(modelRequest.getMetaInfo());
        model.setName(modelRequest.getName());
        model.setVersion(modelRequest.getVersion());
        model.setTenantId(modelRequest.getTenantId());

        repositoryService.saveModel(model);
        response.setStatus(HttpStatus.CREATED.value());

        HashMap<String, Object> content = new HashMap<>();
        content.put("resourceId", model.getId());

        HashMap<String, String> properties = new HashMap<>();
        properties.put("process_id", modelRequest.getKey());
        properties.put("name", modelRequest.getName());
        properties.put("process_namespace", modelRequest.getCategory());
        content.put("properties", properties);

        HashMap<String, String> stencilset = new HashMap<>();
        stencilset.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
        content.put("stencilset", stencilset);

        try {
            repositoryService.addModelEditorSource(model.getId(), objectMapper.writeValueAsBytes(content));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return restResponseFactory.createModelResponse(model);

    }

    @RequestMapping("/repository/model/node")
    public ResponseData getUserTaskFromModelSource(HttpServletRequest request, String modelId) {
        IRequest iRequest = createRequestContext(request);
        List<?> list = activitiService.getUserTaskFromModelSource(iRequest, modelId);
        return new ResponseData(list);
    }

    /**
     * 部署流程
     *
     * @param modelId
     * @throws IOException
     */
    @RequestMapping("/repository/model/{modelId}/deploy")
    @ResponseBody
    public ResponseData modelDeployment(@PathVariable String modelId) {
        Model model = null;
        try {
            model = activitiService.deployModel(modelId);
        } catch (Exception e) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(e.getMessage());
            return rd;
        }
        return new ResponseData(Arrays.asList(model));
    }

    @RequestMapping(value = "/repository/model/{modelId}/export", produces = "text/xml")
    public ResponseEntity<byte[]> modelExport(@PathVariable String modelId,
            @RequestParam(defaultValue = "") String type) throws IOException {
        Model model = repositoryService.getModel(modelId);
        byte[] modelData = repositoryService.getModelEditorSource(modelId);
        JsonNode jsonNode = objectMapper.readTree(modelData);
        BpmnModel bpmnModel = new BpmnJsonConverter().convertToBpmnModel(jsonNode);
        byte[] xmlBytes = new BpmnXMLConverter().convertToXML(bpmnModel, "UTF-8");
        HttpHeaders responseHeaders = new HttpHeaders();
        String id = model.getId();
        if (bpmnModel.getMainProcess() != null) {
            id = bpmnModel.getMainProcess().getId();
        }
        if ("bpmn20".equalsIgnoreCase(type)) {
            responseHeaders.set("Content-Disposition", "attachment;filename=" + id + ".bpmn20.xml");
            responseHeaders.set("Content-Type", "application/octet-stream");
        } else {
            responseHeaders.set("Content-Type", "text/xml;charset=utf8");
        }
        try {
            return new ResponseEntity<>(xmlBytes, responseHeaders, HttpStatus.OK);
        } catch (Exception e) {
            throw new ActivitiIllegalArgumentException("Error exporting diagram", e);
        }
    }

    @RequestMapping("/repository/deploy/{deployId}/export")
    public ResponseEntity<byte[]> deployExport(@PathVariable String deployId) throws IOException {
        BpmnModel bpmnModel = repositoryService.getBpmnModel(deployId);
        byte[] xmlBytes = new BpmnXMLConverter().convertToXML(bpmnModel, "UTF-8");
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "text/xml;charset=utf8");
        try {
            return new ResponseEntity<>(xmlBytes, responseHeaders, HttpStatus.OK);
        } catch (Exception e) {
            throw new ActivitiIllegalArgumentException("Error exporting diagram", e);
        }
    }

    @RequestMapping(value = "/repository/model/import", produces = "text/html;charset=UTF-8")
    public String importModel(HttpServletRequest request) throws FileUploadException, IOException {
        if (!ServletFileUpload.isMultipartContent(request)) {
            return "<script>alert('NOT a Multipart Request')</script>";
        }
        InputStream fileInputStream = null;
        String name = null;
        String key = null;
        String category = null;
        if (request instanceof MultipartHttpServletRequest) {
            MultipartFile file = ((MultipartHttpServletRequest) request).getFile("file");
            if (file != null) {
                fileInputStream = file.getInputStream();
            }
            name = request.getParameter("name");
            key = request.getParameter("key");
            category = request.getParameter("category");
        } else {
            ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
            List<FileItem> items = upload.parseRequest(request);
            FileItem item = null;
            Map<String, String> parameters = new HashMap<>();
            for (FileItem fi : items) {
                if (!fi.isFormField()) {
                    item = fi;
                } else {
                    parameters.put(fi.getFieldName(), fi.getString("UTF-8"));
                }
            }
            if (item != null && StringUtils.isNotEmpty(item.getName())) {
                fileInputStream = item.getInputStream();
                name = parameters.get("name");
                key = parameters.get("key");
                category = parameters.get("category");
            }
        }
        if (fileInputStream == null) {
            return "<script>alert('File Content is Null')</script>";
        }
        try (InputStream inputStream = fileInputStream) {
            InputStreamSource source = new InputStreamSource(fileInputStream);
            BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(source, false, false, "UTF-8");
            bpmnModel.getMainProcess().setId(StringUtils.defaultIfEmpty(key, bpmnModel.getMainProcess().getId()));
            bpmnModel.getMainProcess().setName(StringUtils.defaultIfEmpty(name, bpmnModel.getMainProcess().getName()));

            Model model = repositoryService.newModel();
            model.setCategory(StringUtils.defaultIfEmpty(category, "default"));
            model.setDeploymentId(null);
            model.setKey(bpmnModel.getMainProcess().getId());
            model.setName(bpmnModel.getMainProcess().getName());

            String metaInfo = model.getMetaInfo();
            if (StringUtils.isEmpty(metaInfo)) {
                HashMap<String, String> map = new HashMap<>();
                map.put("name", model.getName());
                map.put("version", String.valueOf(model.getVersion()));
                metaInfo = objectMapper.writeValueAsString(map);
            }
            model.setMetaInfo(metaInfo);

            repositoryService.saveModel(model);

            Object content = new BpmnJsonConverter().convertToJson(bpmnModel);

            repositoryService.addModelEditorSource(model.getId(), objectMapper.writeValueAsBytes(content));
            restResponseFactory.createModelResponse(model);
            return "<script>window.parent.onImportComplete(true)</script>";
        } catch (Exception e) {
            String msg = StringUtils.defaultIfEmpty(ExceptionUtils.getRootCauseMessage(e), e.getMessage());
            if (msg == null) {
                msg = "Unknown Error";
            }
            msg = msg.replace("'", "\\'").replace("\n", "<br/>");
            return "<script>window.parent.onImportComplete(false,'" + msg + "')</script>";
        }

    }

    @RequestMapping("/runtime/tasks/{taskId}/details")
    @ResponseBody
    public TaskResponseExt taskDetails(HttpServletRequest request, @PathVariable String taskId)
            throws WflSecurityException {

        IRequest iRequest = createRequestContext(request);
        return activitiService.getTaskDetails(iRequest, taskId);
    }

    @RequestMapping(value = "/runtime/process-instances/{processInstanceId}/diagram", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getProcessInstanceDiagram(@PathVariable String processInstanceId,
            HttpServletResponse response) {

        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId).singleResult();

        ProcessDefinition pde = repositoryService.getProcessDefinition(processInstance.getProcessDefinitionId());

        if (pde != null && pde.hasGraphicalNotation()) {
            BpmnModel bpmnModel = repositoryService.getBpmnModel(pde.getId());
            ProcessDiagramGenerator diagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();

            List<HistoricActivityInstance> finished = historyService.createHistoricActivityInstanceQuery()
                    .processInstanceId(processInstanceId).finished().list();
            List<String> ids = new ArrayList<>(finished.stream()
                    .map(e -> e.getActivityId() + IActivitiConstants.HISTORY_SUFFIX).collect(Collectors.toList()));
            ids.addAll(runtimeService.getActiveActivityIds(processInstance.getId()));

            InputStream resource = diagramGenerator.generateDiagram(bpmnModel, "svg", ids,
                    Collections.<String> emptyList(), processEngineConfiguration.getActivityFontName(),
                    processEngineConfiguration.getLabelFontName(), processEngineConfiguration.getAnnotationFontName(),
                    processEngineConfiguration.getClassLoader(), 1.0);

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Content-Type", "image/svg+xml");
            try {
                return new ResponseEntity<>(IOUtils.toByteArray(resource), responseHeaders, HttpStatus.OK);
            } catch (Exception e) {
                throw new ActivitiIllegalArgumentException("Error exporting diagram", e);
            }

        } else {
            throw new ActivitiIllegalArgumentException(
                    "Process instance with id '" + processInstance.getId() + "' has no graphical notation defined.");
        }
    }

}
