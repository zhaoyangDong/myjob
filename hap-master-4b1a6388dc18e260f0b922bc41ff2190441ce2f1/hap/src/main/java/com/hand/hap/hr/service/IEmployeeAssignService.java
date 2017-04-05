package com.hand.hap.hr.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.hr.dto.EmployeeAssign;
import com.hand.hap.system.service.IBaseService;

public interface IEmployeeAssignService extends IBaseService<EmployeeAssign>,ProxySelf<IEmployeeAssignService>{
	public List<EmployeeAssign> selectByEmployeeId(IRequest requestContext, Long employeeId, int page, int pagesize);
}
