package com.hand.hap.hr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.hr.dto.EmployeeAssign;
import com.hand.hap.hr.mapper.EmployeeAssignMapper;
import com.hand.hap.hr.service.IEmployeeAssignService;
import com.hand.hap.system.service.impl.BaseServiceImpl;

@Service
public class EmployeeAssignServiceImpl extends BaseServiceImpl<EmployeeAssign> implements IEmployeeAssignService{
	
	@Autowired
	private EmployeeAssignMapper employeeAssignMapper;
	
	@Override
	public List<EmployeeAssign> selectByEmployeeId(IRequest requestContext, Long employeeId, int page, int pagesize){
		return employeeAssignMapper.selectByEmployeeId(employeeId);
	}

}
