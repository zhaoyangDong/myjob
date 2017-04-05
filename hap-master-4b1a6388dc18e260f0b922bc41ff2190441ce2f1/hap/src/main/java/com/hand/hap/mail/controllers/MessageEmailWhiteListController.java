/*
 * #{copyright}#
 */

package com.hand.hap.mail.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.hand.hap.system.dto.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.mail.dto.MessageEmailWhiteList;
import com.hand.hap.core.exception.BaseException;
import com.hand.hap.mail.service.IMessageEmailWhiteListService;

/**
 * 邮件白名单Controller.
 * 
 * @author Clerifen Li
 */
@Controller
public class MessageEmailWhiteListController extends BaseController {

    @Autowired
    private IMessageEmailWhiteListService service;

    @RequestMapping(value = "/sys/messageEmailWhiteList/query")
    @ResponseBody
    public ResponseData getMessageEmailWhiteList(HttpServletRequest request, MessageEmailWhiteList example,
                                                 @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                                 @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectMessageEmailWhiteLists(requestContext, example, page, pagesize));
    }

    @RequestMapping(value = "/sys/messageEmailWhiteList/remove", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData deleteMessageEmailWhiteList(HttpServletRequest request, @RequestBody List<MessageEmailWhiteList> objs) throws BaseException {
        IRequest requestContext = createRequestContext(request);
        service.batchDelete(requestContext, objs);
        return new ResponseData();
    }

}
