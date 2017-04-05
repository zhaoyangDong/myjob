package com.hand.hap.hr.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.hand.hap.account.dto.User;
import com.hand.hap.account.dto.UserRole;
import com.hand.hap.account.service.IUserService;
import com.hand.hap.hr.dto.UserAndRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hand.hap.core.IRequest;
import com.hand.hap.hr.dto.Employee;
import com.hand.hap.hr.service.IEmployeeService;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;


/**
 * @author yuliao.chen@hand-china.com
 */

@Controller
public class EmployeeController extends BaseController{
	
	@Autowired
	private IEmployeeService employeeService;

	
	@RequestMapping(value = "/hr/employee/query")
	@ResponseBody
	public ResponseData query(final Employee employee, @RequestParam(defaultValue = DEFAULT_PAGE) final int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) final int pagesize, final HttpServletRequest request){
		IRequest requestContext = createRequestContext(request);
		return new ResponseData(employeeService.select(requestContext, employee, page, pagesize));
	}

	@RequestMapping(value = "hr/employee/queryAll")
	@ResponseBody
	public ResponseData queryAll(final Employee employee,@RequestParam(defaultValue = DEFAULT_PAGE) final int page,
								 @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) final int pagesize, final HttpServletRequest request) {
		IRequest requestContext = createRequestContext(request);
		return new ResponseData(employeeService.queryAll(requestContext, employee, page, pagesize));
	}
	
	@RequestMapping(value = "/hr/employee/submit")
	@ResponseBody
	public ResponseData submit(@RequestBody final List<Employee> employees, final BindingResult result,
            final HttpServletRequest request){
		 	getValidator().validate(employees, result);
	        if (result.hasErrors()) {
	            ResponseData responseData = new ResponseData(false);
	            responseData.setMessage(getErrorMessage(result, request));
	            return responseData;
	        }
	        IRequest requestContext = createRequestContext(request);
	        return new ResponseData(employeeService.batchUpdate(requestContext, employees));
	}

	@RequestMapping(value = "hr/employee/create_user")
	@ResponseBody
	public ResponseData createUserByEmployee( @RequestBody UserAndRoles userAndRoles,HttpServletRequest request){
		IRequest request1=createRequestContext(request);
		employeeService.createUserByEmployee(request1,userAndRoles);
		return new ResponseData();
	}
	
}
