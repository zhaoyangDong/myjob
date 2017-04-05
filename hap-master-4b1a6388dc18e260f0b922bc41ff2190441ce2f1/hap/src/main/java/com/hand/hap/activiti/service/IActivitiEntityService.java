package com.hand.hap.activiti.service;

import org.activiti.engine.impl.persistence.entity.UserEntity;

/**
 * @author shengyang.zhou@hand-china.com
 */
public interface IActivitiEntityService {

    /**
     * 查找申请者主管
     * 
     * @param employeeCode
     *            员工姓名
     * @return
     */
    String getDirector(String employeeCode);

    boolean isMemberOfGroup(String userId, String groupId);

    String getName(String userId);

    String getEmail(String userId);

    UserEntity getEmployee(String employeeCode);

    String getGroupName(String code);
}