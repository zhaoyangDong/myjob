package com.hand.hap.hr.dto;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.annotation.Condition;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * author:jialong.zuo@hand-china.com on 2016/9/26.
 */
@MultiLanguage
@Table(name = "hr_org_unit_b")
@ExtensionAttribute(disable = true)
public class HrOrgUnit extends BaseDTO {
    @Id
    @GeneratedValue
    private Long unitId;

    @Column
    private Long parentId;

    @Condition(operator = LIKE)
    @NotNull
    private String unitCode;

    @Condition(operator = LIKE)
    @MultiLanguageField
    @Column
    @NotNull
    private String name;

    @MultiLanguageField
    @Condition(operator = LIKE)
    @Column
    private String description;

    private Long managerPosition;

    private Long companyId;

    private String enabledFlag;

    @Transient
    private String positionName;

    @Transient
    private String companyName;

    @Transient
    private String parentName;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(String enabledFlag) {
        this.enabledFlag = enabledFlag;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public Long getManagerPosition() {
        return managerPosition;
    }

    public void setManagerPosition(Long managerPosition) {
        this.managerPosition = managerPosition;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
}
