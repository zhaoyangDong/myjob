package com.hand.hap.activiti.service.impl;

import com.hand.hap.activiti.custom.IActivitiBean;
import com.hand.hap.hr.dto.Employee;
import com.hand.hap.hr.dto.Position;
import com.hand.hap.hr.mapper.EmployeeMapper;
import com.hand.hap.hr.mapper.PositionMapper;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hand.hap.activiti.service.IActivitiEntityService;
import com.hand.hap.activiti.util.ActivitiUtils;

/**
 * @author shengyang.zhou@hand-china.com
 */
@Service
public class ActivitiEntityServiceImpl implements IActivitiEntityService, IActivitiBean {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private PositionMapper positionMapper;

    @Override
    public String getDirector(String employeeCode) {
        Employee director = employeeMapper.getDirector(employeeCode);
        if (director == null) {
            return "ADMIN";
        }
        return director.getEmployeeCode();
    }

    @Override
    public boolean isMemberOfGroup(String userId, String groupId) {
        return true;
    }

    @Override
    public String getName(String userId) {
        return getEmployee(userId).getFirstName();
    }

    @Override
    public String getEmail(String userId) {
        return getEmployee(userId).getEmail();
    }

    @Override
    public UserEntity getEmployee(String employeeCode) {
        return ActivitiUtils.toActivitiUser(employeeMapper.queryByCode(employeeCode));
    }

    @Override
    public String getGroupName(String groupId) {
        Position pos = positionMapper.getPositionByCode(groupId);
        if (pos == null) {
            return groupId;
        }
        return pos.getName();
    }

    @Override
    public String getBeanName() {
        return "empService";
    }
}
