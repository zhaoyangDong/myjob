package com.hand.hap.hr.service.impl;

import java.util.List;

import com.hand.hap.account.dto.User;
import com.hand.hap.account.dto.UserRole;
import com.hand.hap.account.service.IUserRoleService;
import com.hand.hap.account.service.IUserService;
import com.hand.hap.hr.dto.UserAndRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.hr.dto.Employee;
import com.hand.hap.hr.mapper.EmployeeMapper;
import com.hand.hap.hr.service.IEmployeeService;
import com.hand.hap.message.IMessagePublisher;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author yuliao.chen@hand-china.com
 */
@Service
@Transactional
public class EmployeeServiceImpl extends BaseServiceImpl<Employee> implements IEmployeeService {

	@Autowired
    EmployeeMapper employeeMapper;
	
    @Autowired
    private IMessagePublisher messagePublisher;

    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private IUserService userService;

    @Override
    public List<Employee> batchUpdate(IRequest request, List<Employee> list) {
        super.batchUpdate(request, list);
        for (Employee e : list) {
            messagePublisher.publish("employee.change", e);
        }
        return list;
    }


	@Override
	public List<Employee> queryAll(IRequest requestContext, Employee employee, int page, int pagesize) {
		PageHelper.startPage(page, pagesize);
		return employeeMapper.queryAll(employee);
	}

    @Override
    public void createUserByEmployee(IRequest request, UserAndRoles roles) {
		User u =userService.insertSelective(request,roles.getUser());
        if(!(null==roles.getRoles())) {
            Long userId = u.getUserId();
            List<UserRole> roles1 = roles.getRoles();
            for (UserRole role : roles1) {
                role.setUserId(userId);
                userRoleService.insertSelective(request, role);
            }
        }
    }
}
