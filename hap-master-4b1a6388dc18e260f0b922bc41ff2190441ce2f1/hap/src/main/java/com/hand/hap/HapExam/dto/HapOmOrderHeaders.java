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
@Table(name = "hap_om_order_headers_b")
public class HapOmOrderHeaders extends BaseDTO{
	
	
	@Id
	@Column
	@GeneratedValue(generator = GENERATOR_TYPE)
	private Long headerId;
	
	@NotEmpty
	@Column
	private String orderNumber;
	
	@NotEmpty
	@Column
	private Long companyId;
	
	@NotEmpty
	@Column
	private Date orderDate;
	
	@NotEmpty
	@Column
	private String orderStatus;
	
	@NotEmpty
	@Column
	private Long customerId;

	public Long getHeaderId() {
		return headerId;
	}

	public void setHeaderId(Long headerId) {
		this.headerId = headerId;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	
	@MultiLanguageField
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	
	

}
