/*
 * #{copyright}#
 */

package com.hand.hap.hr.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;

/**
 * 员工对象.
 * 
 * @author yuliao.chen@hand-china.com
 */
@ExtensionAttribute(disable=true)
@Table(name = "hr_employee")
public class Employee extends BaseDTO {

	private static final long serialVersionUID = 1512311800885905705L;

	@Id
	@Column
	@GeneratedValue(generator = GENERATOR_TYPE)
	private Long employeeId;

	/**
	 * 员工编码
	 */
	@NotEmpty
	@Column
	private String employeeCode;

	/**
	 * 员工姓名
	 */
	@NotEmpty
	@Column
	private String name;

	/**
	 * 出生日期
	 */
	@Column
	private Date bornDate;
	
	/**
	 * 电子邮件
	 */
	@Column
	private String email;
	
	/**
	 * 移动电话
	 */
	@Column
	private String mobil;
	
	/**
	 * 入职日期
	 */
	@Column
	private Date joinDate;
	
	/**
	 * 性别
	 */
	@Column
	private String gender;
	
	/**
	 * ID
	 */
	@NotEmpty
	@Column
	private String certificateId;
	
	/**
	 * 状态
	 */
	@NotEmpty
	@Column
	private String status;
	
	/**
	 * 启用状态
	 */
	@NotEmpty
	@Column
	private String enabledFlag;

	/**
	 * 部门Id
	 */
	@Transient
	private Long unitId;
	/**
	 * 岗位id
	 * */
	@Transient
	private Long positionId;
	
	/**
	 * 部门名称
	 * */
	@Transient
	private String unitName;
	
	/**
	 * 岗位名称
	 * */
	@Transient
	private String positionName;
	
	
	public Long getUnitId() {
		return unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}

	public Long getPositionId() {
		return positionId;
	}

	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBornDate() {
		return bornDate;
	}

	public void setBornDate(Date bornDate) {
		this.bornDate = bornDate;
	}



	public String getMobil() {
		return mobil;
	}

	public void setMobil(String mobil) {
		this.mobil = mobil;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCertificateId() {
		return certificateId;
	}

	public void setCertificateId(String certificateId) {
		this.certificateId = certificateId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEnabledFlag() {
		return enabledFlag;
	}

	public void setEnabledFlag(String enabledFlag) {
		this.enabledFlag = enabledFlag;
	}

}
