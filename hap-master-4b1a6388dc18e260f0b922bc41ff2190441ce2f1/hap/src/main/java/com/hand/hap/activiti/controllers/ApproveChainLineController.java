package com.hand.hap.activiti.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hand.hap.activiti.dto.ApproveChainHeader;
import com.hand.hap.activiti.dto.ApproveChainLine;
import com.hand.hap.activiti.service.IApproveChainLineService;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;

;

@Controller
public class ApproveChainLineController extends BaseController {

    @Autowired
    private IApproveChainLineService service;

    /**
     * 不分页
     * 
     * @param dto
     * @param request
     * @return
     */
    @RequestMapping(value = "/wfl/approve/chain/line/query")
    @ResponseBody
    public ResponseData query(ApproveChainLine dto, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, 1, 99999));
    }

    @RequestMapping(value = "/wfl/approve-chain-line/query")
    @ResponseBody
    public ResponseData queryByHeader(ApproveChainHeader dto, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(
                service.selectByNodeId(requestContext, dto.getProcessKey(), dto.getUsertaskId()));
    }

    @RequestMapping(value = "/wfl/approve/chain/line/submit")
    @ResponseBody
    public ResponseData update(HttpServletRequest request, @RequestBody List<ApproveChainLine> dto) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/wfl/approve/chain/line/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<ApproveChainLine> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
}