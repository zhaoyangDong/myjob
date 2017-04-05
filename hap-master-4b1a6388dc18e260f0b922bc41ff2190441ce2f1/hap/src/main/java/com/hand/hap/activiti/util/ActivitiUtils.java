package com.hand.hap.activiti.util;

import java.util.List;

import com.hand.hap.hr.dto.Employee;
import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.GroupEntityImpl;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.impl.persistence.entity.UserEntityImpl;
import org.apache.commons.lang.StringUtils;

import com.hand.hap.hr.dto.Position;

/**
 * @author shengyang.zhou@hand-china.com
 */
public class ActivitiUtils {
    public static UserEntity toActivitiUser(Employee emp) {
        UserEntityImpl entity = new UserEntityImpl();
        if (emp == null) {
            return entity;
        }
        entity.setId(emp.getEmployeeCode());
        String empName = emp.getName();
        entity.setFirstName(StringUtils.defaultIfEmpty(empName, "UNKNOWN"));
        entity.setLastName("");
        entity.setEmail(emp.getEmail());
        entity.setRevision(1);
        return entity;
    }

    public static GroupEntity toActivitiGroup(Position position) {
        GroupEntityImpl groupEntity = new GroupEntityImpl();
        if (position == null) {
            return groupEntity;
        }
        groupEntity.setRevision(1);
        groupEntity.setId(position.getPositionCode());
        groupEntity.setName(position.getName());
        groupEntity.setType("assignment");
        return groupEntity;
    }

    public static List<Group> toActivitiGroups(List<String> groupIds) {
        return null;
    }
}
