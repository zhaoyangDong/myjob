package com.hand.hap.hr.controllers;

import javax.servlet.http.HttpServletRequest;

import com.hand.hap.hr.dto.EmployeeAssign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hand.hap.core.IRequest;
import com.hand.hap.hr.service.IEmployeeAssignService;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;

import java.util.List;


@Controller
public class EmployeeAssignController extends BaseController{
	@Autowired
	private IEmployeeAssignService employeeAssignService;
	
	
	@RequestMapping(value = "/hr/employee/assign/query")
	@ResponseBody
	public ResponseData queryByEmployeeId(final Long employeeId, @RequestParam(defaultValue = DEFAULT_PAGE) final int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) final int pagesize, final HttpServletRequest request){
		IRequest requestContext = createRequestContext(request);
		return  new ResponseData(employeeAssignService.selectByEmployeeId(requestContext, employeeId, page, pagesize));
	}


    @RequestMapping(value = "/hr/employee/assign/submit")
    @ResponseBody
    public ResponseData submitAssign(HttpServletRequest request, @RequestBody List<EmployeeAssign> assignList){
        IRequest requestContext = createRequestContext(request);
        return  new ResponseData(employeeAssignService.batchUpdate(requestContext, assignList));
    }
	
	
}
