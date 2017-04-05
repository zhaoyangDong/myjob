/*
 * #{copyright}#
 */
package com.hand.hap.system.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.annotation.Condition;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;

/**
 * 测试表
 * 
 * @author hailin.xu@hand-china.com
 */
@MultiLanguage
@Table(name = "sys_user_demo_b")
@ExtensionAttribute(disable=true)
public class SysUserDemo extends BaseDTO {
	
	private static final long serialVersionUID = 1L;

	/**
     * 表ID，主键，供其他表做外键.
     */
    @Id
    @GeneratedValue
    private Long userId;

    @NotEmpty
    @Condition(operator = LIKE)
    private String userCode;

    /**
     * 姓名
     */
    @NotEmpty
    @Condition(operator = LIKE)
    @MultiLanguageField
    private String userName;

    /**
     * 年龄
     */
    private Long userAge;
    
    /**
     * 性别
     */
    private String userSex;
    
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT)
    private Date userBirth;
       
    private String userEmail;
    
    private Long userPhone;
    
    private String enableFlag;
    
    @Column
    @MultiLanguageField
    private String description;
    
    private String roleName;
    
    @JsonFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date startActiveTime;
    
    @JsonFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date endActiveTime;
    
    private Long roleId;
    
	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public String getUserCode() {
		return userCode;
	}
	
	public void setUserCode(String userCode) {
		this.userCode = userCode == null ? null : userCode.trim();
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName == null ? null : userName.trim();
	}
	
	public Long getUserAge() {
		return userAge;
	}
	
	public void setUserAge(Long userAge) {
		this.userAge = userAge;
	}
	
	public String getUserSex() {
		return userSex;
	}
	
	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}
	
	public Date getUserBirth() {
		return userBirth;
	}
	
	public void setUserBirth(Date userBirth) {
		this.userBirth = userBirth;
	}
	
	public String getUserEmail() {
		return userEmail;
	}
	
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
	public Long getUserPhone() {
		return userPhone;
	}
	
	public void setUserPhone(Long userPhone) {
		this.userPhone = userPhone;
	}
	
	public String getEnableFlag() {
		return enableFlag;
	}
	
	public void setEnableFlag(String enableFlag) {
		this.enableFlag = enableFlag;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description == null ? null : description.trim();
	}
	
	public String getRoleName() {
		return roleName;
	}
	
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public Date getStartActiveTime() {
		return startActiveTime;
	}
	
	public void setStartActiveTime(Date startActiveTime) {
		this.startActiveTime = startActiveTime;
	}
	
	public Date getEndActiveTime() {
		return endActiveTime;
	}
	
	public void setEndActiveTime(Date endActiveTime) {
		this.endActiveTime = endActiveTime;
	}
	
	public Long getRoleId() {
		return roleId;
	}
	
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

   
}