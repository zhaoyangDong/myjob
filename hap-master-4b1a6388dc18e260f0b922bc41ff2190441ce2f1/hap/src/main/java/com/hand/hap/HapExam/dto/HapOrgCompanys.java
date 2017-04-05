package com.hand.hap.HapExam.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.system.dto.BaseDTO;

@MultiLanguage
@Table(name = "hap_org_companys_b")
public class HapOrgCompanys extends BaseDTO{
	
   @Id
   @Column
   @GeneratedValue(generator = GENERATOR_TYPE)
   private Long companyId;
   
   @NotEmpty
   @Column
   private String companyNumber;
   
   @NotEmpty
   @Column
   private String companyName;
   
   @NotEmpty
   @Column
   private String enabledFlag;

public Long getCompanyId() {
	return companyId;
}

public void setCompanyId(Long companyId) {
	this.companyId = companyId;
}

public String getCompanyNumber() {
	return companyNumber;
}

public void setCompanyNumber(String companyNumber) {
	this.companyNumber = companyNumber;
}

@MultiLanguageField
public String getCompanyName() {
	return companyName;
}

public void setCompanyName(String companyName) {
	this.companyName = companyName;
}

public String getEnabledFlag() {
	return enabledFlag;
}

public void setEnabledFlag(String enabledFlag) {
	this.enabledFlag = enabledFlag;
}
   
   
  
}
