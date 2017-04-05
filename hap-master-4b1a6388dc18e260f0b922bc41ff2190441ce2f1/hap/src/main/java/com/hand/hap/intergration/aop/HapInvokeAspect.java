/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved. Project
 * Name:hstaffParent Package Name:hstaff.core.aop Date:2016/11/21 0028 Create
 * By:xiangyu.qi@hand-china.com
 *
 */
package com.hand.hap.intergration.aop;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.hand.hap.intergration.controllers.HapApiController;
import net.sf.json.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.impl.RequestHelper;
import com.hand.hap.intergration.annotation.HapInbound;
import com.hand.hap.intergration.beans.HapInvokeInfo;
import com.hand.hap.intergration.dto.HapInterfaceHeader;
import com.hand.hap.intergration.dto.HapInterfaceInbound;
import com.hand.hap.intergration.dto.HapInterfaceOutbound;
import com.hand.hap.intergration.service.IHapInterfaceHeaderService;
import com.hand.hap.intergration.service.IHapInterfaceInboundService;
import com.hand.hap.intergration.service.IHapInterfaceOutboundService;
import com.hand.hap.system.dto.ResponseData;

@Aspect
@Component
public class HapInvokeAspect {

    @Autowired
    private IHapInterfaceOutboundService outboundService;

    @Autowired
    private IHapInterfaceInboundService inboundService;

    @Autowired
    private IHapInterfaceHeaderService headerService;

    private static final Logger logger = LoggerFactory.getLogger(HapInvokeAspect.class);

    @Pointcut("@annotation(com.hand.hap.intergration.annotation.HapOutbound)")
    public void outboundAspect() {
    }

    /*
     * 出站请求AOP处理
     */
    @Around("outboundAspect()")
    public Object aroundMethod(ProceedingJoinPoint pjd) throws Throwable {

        Long startTime = System.currentTimeMillis();
        HapInvokeInfo.REQUEST_START_TIME.set(startTime);
        Object result = null;
        Throwable throwable = null;
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        IRequest iRequest = RequestHelper.createServiceRequest(request);
        HapInterfaceOutbound outbound = new HapInterfaceOutbound();
        HapInvokeInfo.OUTBOUND.set(outbound);
        try {
            outbound.setRequestTime(new Date());
            Object[] args = pjd.getArgs();
            // 处理请求参数
            for (Object obj : args) {
                if (obj instanceof HttpServletRequest) {
                    String sysName = request.getParameter("sysName");
                    String apiName = request.getParameter("apiName");
                    outbound.setInterfaceName(sysName + "-" + apiName);
                    HapInterfaceHeader hapInterfaceHeader = headerService.getHeaderAndLine(sysName, apiName);
                    if (hapInterfaceHeader != null) {
                        outbound.setInterfaceUrl(hapInterfaceHeader.getDomainUrl() + hapInterfaceHeader.getIftUrl());
                    } else {
                        outbound.setInterfaceUrl("");
                    }
                }
            }
            result = pjd.proceed();
            if (HapInvokeInfo.OUTBOUND_REQUEST_PARAMETER.get() != null) {
                outbound.setRequestParameter(HapInvokeInfo.OUTBOUND_REQUEST_PARAMETER.get());
            }
            if (HapInvokeInfo.HTTP_RESPONSE_CODE.get() != null)
                outbound.setResponseCode(HapInvokeInfo.HTTP_RESPONSE_CODE.get().toString());
            // 请求成功
            if (result != null) {
                outbound.setResponseContent(result.toString());
            }
            outbound.setRequestStatus(HapInvokeInfo.REQUEST_SUCESS);

            // 如果同时监听inbound outbound 异常只会被捕捉一次，设置异常
            HapInterfaceInbound inbound = HapInvokeInfo.INBOUND.get();
            if (inbound != null) {
                if (inbound.getStackTrace() != null) {
                    outbound.setStackTrace(inbound.getStackTrace());
                    outbound.setRequestStatus(HapInvokeInfo.REQUEST_FAILURE);
                }
                HapInvokeInfo.INBOUND.remove();
            }

        } catch (Throwable e) {
            throwable = e;
            result = new JSONObject();
            ((JSONObject) result).put("error",e.getMessage());
        } finally {
            outboundService.outboundInvoke(outbound, throwable);
        }
        return result;

    }

    /*
     * 入站请求AOP处理
     */
    @Around("@annotation(bound)")
    public Object inaroundMethod(ProceedingJoinPoint pjd, HapInbound bound) throws Throwable {
        Long startTime = System.currentTimeMillis();

        Object result = null;
        Throwable throwable = null;
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        HapInterfaceInbound inbound = new HapInterfaceInbound();
        HapInvokeInfo.INBOUND.set(inbound);
        try {
            inbound.setRequestTime(new Date());
            inbound.setInterfaceName(bound.apiName());
            result = pjd.proceed();
            if (result != null) {
                String content = "";
                if (result instanceof ResponseData) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    content = objectMapper.writeValueAsString(result);

                } else if (result instanceof ModelAndView) {
                    content = result.toString();
                } else if (result instanceof String) {
                    content = (String) result;
                } else {
                    content = result.toString();
                }
                inbound.setResponseContent(content);
            }
            inbound.setRequestStatus(HapInvokeInfo.REQUEST_SUCESS);
            HapInterfaceOutbound outbound = HapInvokeInfo.OUTBOUND.get();
            // 如果同时监听inbound outbound 异常只会被捕捉一次，设置异常
            if (outbound != null) {
                if (outbound.getStackTrace() != null) {
                    inbound.setStackTrace(outbound.getStackTrace());
                    inbound.setRequestStatus(HapInvokeInfo.REQUEST_FAILURE);
                }
                HapInvokeInfo.OUTBOUND.remove();
            }

        } catch (Throwable e) {
            throwable = e;
            //是否是拦截的HapApiController
            if(pjd.getTarget().getClass().getName().equalsIgnoreCase(HapApiController.class.getName())) {
                result = new JSONObject();
                ((JSONObject) result).put("error", e.getMessage());
            }
        } finally {
            Long endTime = System.currentTimeMillis();
            inbound.setResponseTime(endTime - startTime);
            inboundService.inboundInvoke(request, inbound, throwable);
        }
        return result;
    }

}
