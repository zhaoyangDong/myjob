package com.hand.hap.HapExam.dto;

import com.hand.hap.system.dto.BaseDTO;

public class Order extends BaseDTO{
	
	
	private String headerId;
	private String companyId;
	private String companyName;
	private String customerId;
	private String customerName;
	private String orderNumber;
	private String orderDate;
	private String orderStatus;
	private String orderdQuantity;
	private String unitSellingPrice;
	private String inventoryItemId;
	private String itemDescription;
	public String getHeaderId() {
		return headerId;
	}
	public void setHeaderId(String headerId) {
		this.headerId = headerId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getOrderdQuantity() {
		return orderdQuantity;
	}
	public void setOrderdQuantity(String orderdQuantity) {
		this.orderdQuantity = orderdQuantity;
	}
	public String getUnitSellingPrice() {
		return unitSellingPrice;
	}
	public void setUnitSellingPrice(String unitSellingPrice) {
		this.unitSellingPrice = unitSellingPrice;
	}
	public String getInventoryItemId() {
		return inventoryItemId;
	}
	public void setInventoryItemId(String inventoryItemId) {
		this.inventoryItemId = inventoryItemId;
	}
	public String getItemDescription() {
		return itemDescription;
	}
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
	
	
   
	
	

}
