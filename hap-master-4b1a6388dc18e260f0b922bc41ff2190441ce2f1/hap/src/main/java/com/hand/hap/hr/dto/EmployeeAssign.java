package com.hand.hap.hr.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;

/**
 * @author shengyang.zhou@hand-china.com
 */
@ExtensionAttribute(disable = true)
@Table(name = "HR_EMPLOYEE_ASSIGN")
public class EmployeeAssign extends BaseDTO {

    @Id
    @GeneratedValue
    private Long assignId;

    private Long employeeId;

    private Long positionId;

    @Transient
    private String positionName;

    @Transient
    private String unitName;

    private String primaryPositionFlag;

    private String enabledFlag;

    public Long getAssignId() {
        return assignId;
    }

    public void setAssignId(Long assignId) {
        this.assignId = assignId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public String getPrimaryPositionFlag() {
        return primaryPositionFlag;
    }

    public void setPrimaryPositionFlag(String primaryPositionFlag) {
        this.primaryPositionFlag = primaryPositionFlag;
    }

    public String getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(String enabledFlag) {
        this.enabledFlag = enabledFlag;
    }
}
