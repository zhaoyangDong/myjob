package com.hand.hap.intergration.controllers;


import com.hand.hap.cache.Cache;
import com.hand.hap.cache.CacheManager;
import com.hand.hap.core.IRequest;
import com.hand.hap.intergration.dto.HapInterfaceInbound;
import com.hand.hap.intergration.dto.HapInterfaceOutbound;
import com.hand.hap.intergration.service.IHapInterfaceInboundService;
import com.hand.hap.intergration.service.IHapInterfaceOutboundService;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.Prompt;
import com.hand.hap.system.dto.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * xiangyu.qi@hand-china.com 2016/11/01
 * @version 2016/11/22
 */

@Controller
@RequestMapping("/sys/invoke")
public class HapInterfaceInvokeController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(HapInterfaceInvokeController.class);

    @Autowired
    private IHapInterfaceInboundService inboundService;

    @Autowired
    private IHapInterfaceOutboundService outboundService;

    @Autowired
    private CacheManager cacheManager;

    @RequestMapping(value = "/querryInbound" ,produces = "application/json")
    @ResponseBody
    public ResponseData queryInbound(@RequestBody  HapInterfaceInbound inbound,  HttpServletRequest request){
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(inboundService.select(requestContext, inbound, inbound.getPage(), inbound.getPagesize()));
    }

    @RequestMapping(value = "/querryOutbound")
    @ResponseBody
    public ResponseData queryOutbound(@RequestBody  HapInterfaceOutbound outbound,  HttpServletRequest request){
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(outboundService.select(requestContext, outbound, outbound.getPage(), outbound.getPagesize()));
    }

    @RequestMapping(value = "/removeInbound")
    @ResponseBody
    public ResponseData deleteInbound(HttpServletRequest request,@RequestBody List<HapInterfaceInbound> inbounds){
        inboundService.batchDelete(inbounds);
        return new ResponseData();
    }

    @RequestMapping(value = "/removeOutbound")
    @ResponseBody
    public ResponseData deleteOutbound(HttpServletRequest request,@RequestBody List<HapInterfaceOutbound> outbounds){
        outboundService.batchDelete(outbounds);
        return new ResponseData();
    }

}
