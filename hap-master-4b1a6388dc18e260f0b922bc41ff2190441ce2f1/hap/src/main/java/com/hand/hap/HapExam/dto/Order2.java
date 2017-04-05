package com.hand.hap.HapExam.dto;

import com.hand.hap.system.dto.BaseDTO;

public class Order2 extends BaseDTO{
	 private Long companyId;
	  private  Long customerId;
	  private  String orderNumber;
	  private Long inventoryItemId;
	  private String orderStatus;
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public Long getInventoryItemId() {
		return inventoryItemId;
	}
	public void setInventoryItemId(Long inventoryItemId) {
		this.inventoryItemId = inventoryItemId;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	  


}
