package com.hand.hap.hr.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.hr.dto.Employee;
import com.hand.hap.hr.dto.UserAndRoles;
import com.hand.hap.system.service.IBaseService;

/**
 * @author yuliao.chen@hand-china.com
 */
public interface IEmployeeService extends IBaseService<Employee>,ProxySelf<IEmployeeService>{
	
	public List<Employee> queryAll(IRequest requestContext,Employee employee, int page, int pagesize);

	void createUserByEmployee(IRequest request, UserAndRoles roles);
}
