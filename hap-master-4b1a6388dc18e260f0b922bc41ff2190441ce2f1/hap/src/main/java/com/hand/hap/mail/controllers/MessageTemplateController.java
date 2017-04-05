/*
 * #{copyright}#
 */

package com.hand.hap.mail.controllers;

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
import com.hand.hap.core.exception.BaseException;
import com.hand.hap.mail.dto.MessageTemplate;
import com.hand.hap.mail.service.IMessageTemplateService;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;

/**
 * 消息模板Controller
 * 
 * @author Clerifen Li
 */
@Controller
public class MessageTemplateController extends BaseController {

    @Autowired
    private IMessageTemplateService service;

    @RequestMapping(value = "/sys/messageTemplate/query")
    @ResponseBody
    public ResponseData getMessageTemplate(HttpServletRequest request, MessageTemplate example,
                                           @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                           @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectMessageTemplates(requestContext, example, page, pagesize));
    }

    @ResponseBody
    @RequestMapping(value="/sys/messageTemplate/add")
    public ResponseData addMessageTemplate(HttpServletRequest request, @RequestBody MessageTemplate obj, BindingResult result) throws BaseException {

        getValidator().validate(obj, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestContext = createRequestContext(request);
        service.createMessageTemplate(requestContext, obj);
        return new ResponseData();
    }
    
    @ResponseBody
    @RequestMapping(value="/sys/messageTemplate/update")
    public ResponseData updateMessageTemplate(HttpServletRequest request, @RequestBody MessageTemplate obj, BindingResult result) throws BaseException {

        getValidator().validate(obj, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestContext = createRequestContext(request);
        service.updateMessageTemplate(requestContext, obj);
        return new ResponseData();
    }
    
    @RequestMapping(value = "/sys/messageTemplate/remove", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData deleteMessageTemplate(HttpServletRequest request, @RequestBody List<MessageTemplate> objs) throws BaseException {
        IRequest requestContext = createRequestContext(request);
        service.batchDelete(requestContext, objs);
        return new ResponseData();
    }

}
