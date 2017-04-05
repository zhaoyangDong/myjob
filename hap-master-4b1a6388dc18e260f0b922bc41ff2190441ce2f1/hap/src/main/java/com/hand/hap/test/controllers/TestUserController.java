package com.hand.hap.test.controllers;

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
import com.hand.hap.test.dto.TestUser;
import com.hand.hap.test.service.ITestUserservice;


@Controller
public class TestUserController extends BaseController{
	
	@Autowired
	private ITestUserservice itestuserservice;
	
	@RequestMapping(value = "/test/user/query")
    @ResponseBody
    public ResponseData query(TestUser testuser, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        List<TestUser> list = itestuserservice.select(requestContext, testuser, page, pageSize);
       
        return new ResponseData(list);
    }

	
	
	
	
	
	
	
	@RequestMapping(value = "/test/user/submit", method = RequestMethod.POST)
	public ResponseData submitPosition(@RequestBody List<TestUser> testuser, BindingResult result, HttpServletRequest request)
			throws TokenException {
		checkToken(request, testuser);
		getValidator().validate(testuser, result);
		if (result.hasErrors()) {
			ResponseData rd = new ResponseData(false);
			rd.setMessage(getErrorMessage(result, request));
			return rd;
		}
		IRequest requestCtx = createRequestContext(request);
		return new ResponseData(itestuserservice.batchUpdate(requestCtx, testuser));
		
		
	   }
		
		
		
		
		
		
		@RequestMapping(value = "/test/user/remove")
	    @ResponseBody
	    public ResponseData delete(HttpServletRequest request,@RequestBody List<TestUser> testuser){
			itestuserservice.batchDelete(testuser);
	        return new ResponseData();
	 
	}
}
