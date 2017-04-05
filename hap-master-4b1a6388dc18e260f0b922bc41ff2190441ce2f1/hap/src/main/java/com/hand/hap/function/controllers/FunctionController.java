/*
 * #{copyright}#
 */
package com.hand.hap.function.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.function.dto.Function;
import com.hand.hap.function.dto.Resource;
import com.hand.hap.function.service.IFunctionService;

/**
 * 功能的controller.
 * 
 * @author wuyichu
 * @author njq.niu@hand-china.com
 * @author xiawang.liu@hand-china.com
 */
@Controller
@RequestMapping(value = "/sys/function")
public class FunctionController extends BaseController {

    @Autowired
    private IFunctionService functionService;

    /**
     * 获取挂靠功能资源.
     *
     * @param request
     *            HttpServletRequest
     * @param functionId
     *            功能ID
     * @param isExit
     *            isExit
     * @param resource
     *            资源
     * @param page
     *            起始页
     * @param pagesize
     *            分页大小
     * @return ResponseData
     */
    @RequestMapping(value = "/fetchResource")
    @ResponseBody
    public ResponseData fetch(final HttpServletRequest request, final Long functionId, final Integer isExit,
            final Resource resource, @RequestParam(defaultValue = DEFAULT_PAGE) final int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) final int pagesize) {
        IRequest requestContext = createRequestContext(request);
        Function function = new Function();
        function.setFunctionId(functionId);
        return new ResponseData(
                functionService.selectExitResourcesByFunction(requestContext, function, resource, page, pagesize));
    }

    /**
     * 获取未挂靠的功能资源.
     *
     * @param request
     *            HttpServletRequest
     * @param functionId
     *            功能ID
     * @param isExit
     *            isExit
     * @param resource
     *            资源
     * @param page
     *            起始页
     * @param pagesize
     *            分页大小
     * @return ResponseData
     */
    @RequestMapping(value = "/fetchNotResource")
    @ResponseBody
    public ResponseData fetch2(final HttpServletRequest request, final Long functionId, final Integer isExit,
            final Resource resource, @RequestParam(defaultValue = DEFAULT_PAGE) final int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) final int pagesize) {
        IRequest requestContext = createRequestContext(request);
        Function function = new Function();
        if (StringUtils.isEmpty(resource.getUrl())) {
            resource.setUrl(null);
        }
        if (StringUtils.isEmpty(resource.getName())) {
            resource.setName(null);
        }
        if (StringUtils.isEmpty(resource.getType())) {
            resource.setType(null);
        }
        function.setFunctionId(functionId);
        return new ResponseData(
                functionService.selectNotExitResourcesByFunction(requestContext, function, resource, page, pagesize));
    }

    /**
     * 主界面菜单数据获取.
     *
     * @param request
     *            HttpServletRequest
     * @return object
     */
    @RequestMapping("/menus")
    @ResponseBody
    public Object getMenuTree(HttpServletRequest request) {
        return functionService.selectRoleFunctions(createRequestContext(request));
    }

    /**
     * 查询功能.
     *
     * @param function
     *            功能对象
     * @param page
     *            起始页
     * @param pagesize
     *            分页大小
     * @param request
     *            HttpServletRequest
     * @return ResponseData
     */
    @RequestMapping(value = "/query")
    @ResponseBody
    public ResponseData query(final Function function, @RequestParam(defaultValue = DEFAULT_PAGE) final int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) final int pagesize, final HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(functionService.selectFunction(requestContext, function, page, pagesize));
    }

    /**
     * 删除功能.
     *
     * @param functions
     *            functions
     * @param result
     *            BindingResult
     * @param request
     *            HttpServletRequest
     * @return ResponseData ResponseData
     */
    @RequestMapping(value = "/remove")
    @ResponseBody
    public ResponseData remove(@RequestBody final List<Function> functions, final BindingResult result,
            final HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        functionService.batchDelete(requestContext, functions);
        return new ResponseData();
    }

    /**
     * 功能批量更新.
     *
     * @param functions
     *            functions
     * @param result
     *            BindingResult
     * @param request
     *            HttpServletRequest
     * @return ResponseData ResponseData
     */
    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData submit(@RequestBody final List<Function> functions, final BindingResult result,
            final HttpServletRequest request) {
        getValidator().validate(functions, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(functionService.batchUpdate(requestContext, functions));
    }

    /**
     * 更新功能资源.
     * 
     * @param request
     *            HttpServletRequest
     * @param function
     *            功能
     * @return ResponseData ResponseData
     */
    @RequestMapping(value = "/updateFunctionResource")
    @ResponseBody
    public ResponseData updateFunctionResource(final HttpServletRequest request, @RequestBody final Function function) {
        IRequest requestContext = createRequestContext(request);
        ResponseData data = new ResponseData();
        functionService.updateFunctionResources(requestContext, function, function.getResources());
        data.setSuccess(true);
        return data;
    }
}
