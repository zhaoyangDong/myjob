/*
 * #{copyright}#
 */

package com.hand.hap.system.controllers.sys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.exception.TokenException;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.system.dto.SysUserDemo;
import com.hand.hap.system.service.ISysUserDemoService;

/**
 * PromptController.
 * 
 * @author shengyang.zhou@hand-china.com
 */
@Controller
public class SysUserDemoController extends BaseController {

    @Autowired
    private ISysUserDemoService demoService;

    /**
     * 描述信息查询.
     * 
     * @param prompt
     *            Prompt
     * @param page
     *            起始页
     * @param pagesize
     *            分页大小
     * @return ResponseData
     */
    @RequestMapping(value = "/sys/user/demo/query")
    @ResponseBody
    public ResponseData getDemos(HttpServletRequest request,SysUserDemo sysDemo, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                   @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest iRequest = createRequestContext(request);
        return new ResponseData(demoService.select(iRequest, sysDemo, page, pagesize));
    }

    /**
     * 描述信息保存.
     * 
     * @param demo
     *            demo
     * @param result
     *            BindingResult
     * @param request
     *            HttpServletRequest
     * @return ResponseData
     * @throws TokenException
     */
    @RequestMapping(value = "/sys/user/demo/submit", method = RequestMethod.POST)
    public ResponseData submitPrompt(@RequestBody List<SysUserDemo> demo, BindingResult result,
            HttpServletRequest request) throws TokenException {
        checkToken(request, demo);
        getValidator().validate(demo, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(demoService.batchUpdate(requestCtx, demo));
    }

    /**
     * 描述信息删除.
     * 
     * @param request
     * @param demo
     * @return ResponseData
     * @throws TokenException
     */
    @RequestMapping(value = "/sys/user/demo/remove", method = RequestMethod.POST)
    public ResponseData remove(HttpServletRequest request, @RequestBody List<SysUserDemo> demo) throws TokenException {
        checkToken(request, demo);
        demoService.batchDelete(demo);
        return new ResponseData();
    }
}
