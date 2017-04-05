package com.hand.hap.hr.mapper;

import com.hand.hap.hr.dto.EmployeeAssign;
import com.hand.hap.mybatis.common.Mapper;

import java.util.List;

/**
 * @author shengyang.zhou@hand-china.com
 */
public interface EmployeeAssignMapper extends Mapper<EmployeeAssign> {
    List<EmployeeAssign> selectByEmployeeId(Long employeeId);

    int deleteByEmployeeId(Long employeeId);
}
