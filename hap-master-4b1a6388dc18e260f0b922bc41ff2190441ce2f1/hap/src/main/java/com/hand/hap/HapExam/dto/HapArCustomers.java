package com.hand.hap.HapExam.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.system.dto.BaseDTO;

@MultiLanguage
@Table(name = "hap_ar_customers_b")
public class HapArCustomers extends BaseDTO{
	
	@Id
	@Column
	@GeneratedValue(generator = GENERATOR_TYPE)
	private Long customerId;
	
	@NotEmpty
	@Column
	private String customerNumber;
	
	@NotEmpty
	@Column
	private String customerName;
	
	@NotEmpty
	@Column
	private Long companyId;
	
	@NotEmpty
	@Column
	private String enabledFlag;

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	@MultiLanguageField
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getEnabledFlag() {
		return enabledFlag;
	}

	public void setEnabledFlag(String enabledFlag) {
		this.enabledFlag = enabledFlag;
	}
	
	
	
	

}
