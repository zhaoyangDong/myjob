package com.hand.hap.hr.mapper;

import java.util.List;

import com.hand.hap.hr.dto.Employee;
import com.hand.hap.mybatis.common.Mapper;

/**
 * @author yuliao.chen@hand-china.com
 */
public interface EmployeeMapper extends Mapper<Employee>{

    Employee queryByCode(String entityId);

    Employee getDirector(String employeeCode);
   
    List<Employee> queryAll(Employee employee);
}
