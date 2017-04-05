package com.hand.hap.activiti.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hand.hap.activiti.dto.ApproveChainHeader;
import com.hand.hap.activiti.service.IApproveChainHeaderService;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;

;

@Controller
public class ApproveChainHeaderController extends BaseController {

    @Autowired
    private IApproveChainHeaderService service;

    @RequestMapping(value = "/wfl/approve/chain/header/query")
    @ResponseBody
    public ResponseData query(ApproveChainHeader dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/wfl/approve/chain/header/submit")
    @ResponseBody
    public ResponseData update(HttpServletRequest request, @RequestBody List<ApproveChainHeader> dto) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/wfl/approve/chain/submit")
    @ResponseBody
    public ResponseData submitHeadLine(HttpServletRequest request, @RequestBody List<ApproveChainHeader> dto) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.updateHeadLine(requestCtx, dto));
    }

    @RequestMapping(value = "/wfl/approve/chain/header/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<ApproveChainHeader> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
}