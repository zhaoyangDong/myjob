package com.hand.hap.account.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hand.hap.account.service.IRole;
import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.annotation.Condition;
import com.hand.hap.system.dto.BaseDTO;

/**
 * @author shengyang.zhou@hand-china.com
 */
@MultiLanguage
@Table(name = "sys_role_b")
public class Role extends BaseDTO implements IRole {

    public static final String FIELD_ROLE_ID = "roleId";

    public static final String FIELD_ALL_ROLE_ID = "roleIds";

    @Id
    @Column
    @GeneratedValue(generator = GENERATOR_TYPE)
    private Long roleId;

    @Column
    private String roleCode;

    @Column
    @MultiLanguageField
    private String roleName;

    @Column
    @MultiLanguageField
    private String roleDescription;

    @Column
    @Condition(operator = ">=")
    private Date startActiveDate;

    @Column
    @Condition(operator = "<=")
    private Date endActiveDate;

    @Column
    private String enableFlag;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    @Override
    public boolean isEnabled() {
        return YES.equals(enableFlag);
    }

    @Override
    public boolean isActive() {
        return (startActiveDate == null || startActiveDate.getTime() <= System.currentTimeMillis())
                && (endActiveDate == null || endActiveDate.getTime() >= System.currentTimeMillis());
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public static String getFIELD_ROLE_ID() {
        return FIELD_ROLE_ID;
    }

    public Date getStartActiveDate() {
        return startActiveDate;
    }

    public void setStartActiveDate(Date startActiveDate) {
        this.startActiveDate = startActiveDate;
    }

    public Date getEndActiveDate() {
        return endActiveDate;
    }

    public void setEndActiveDate(Date endActiveDate) {
        this.endActiveDate = endActiveDate;
    }

    public String getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(String enableFlag) {
        this.enableFlag = enableFlag;
    }
}
