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
@Table(name = "hap_om_order_lines_b")
public class HapOmOrderLines extends BaseDTO{
	
	@Id
	@Column
	@GeneratedValue(generator = GENERATOR_TYPE)
	private Long lineId;
	
	@NotEmpty
	@Column
	private Long headerId;
	
	@NotEmpty
	@Column
	private Long lineNumber;
	
	@NotEmpty
	@Column
	private Long inventoryItemId;
	
	@NotEmpty
	@Column
	private Long orderdQuantity;
	
	@NotEmpty
	@Column
	private String orderQuantityUom;
	
	@NotEmpty
	@Column
	private Long unitSellingPrice;
	
	@NotEmpty
	@Column
	private String description;
	
	@NotEmpty
	@Column
	private Long companyId;
	
	@Column
	private String addition1;
	
	@Column
	private String addition2;
	
	@Column
	private String addition3;
	
	@Column
	private String addition4;
	
	@Column
	private String addition5;

	public Long getLineId() {
		return lineId;
	}

	public void setLineId(Long lineId) {
		this.lineId = lineId;
	}

	public Long getHeaderId() {
		return headerId;
	}

	public void setHeaderId(Long headerId) {
		this.headerId = headerId;
	}

	public Long getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(Long lineNumber) {
		this.lineNumber = lineNumber;
	}

	public Long getInventoryItemId() {
		return inventoryItemId;
	}

	public void setInventoryItemId(Long inventoryItemId) {
		this.inventoryItemId = inventoryItemId;
	}

	public Long getOrderdQuantity() {
		return orderdQuantity;
	}

	public void setOrderdQuantity(Long orderdQuantity) {
		this.orderdQuantity = orderdQuantity;
	}

	
	@MultiLanguageField
	public String getOrderQuantityUom() {
		return orderQuantityUom;
	}

	public void setOrderQuantityUom(String orderQuantityUom) {
		this.orderQuantityUom = orderQuantityUom;
	}

	public Long getUnitSellingPrice() {
		return unitSellingPrice;
	}

	public void setUnitSellingPrice(Long unitSellingPrice) {
		this.unitSellingPrice = unitSellingPrice;
	}

	public String getDescription() {
		return description;
	}

	
	@MultiLanguageField
	public void setDescription(String description) {
		this.description = description;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getAddition1() {
		return addition1;
	}

	public void setAddition1(String addition1) {
		this.addition1 = addition1;
	}

	public String getAddition2() {
		return addition2;
	}

	public void setAddition2(String addition2) {
		this.addition2 = addition2;
	}

	public String getAddition3() {
		return addition3;
	}

	public void setAddition3(String addition3) {
		this.addition3 = addition3;
	}

	public String getAddition4() {
		return addition4;
	}

	public void setAddition4(String addition4) {
		this.addition4 = addition4;
	}

	public String getAddition5() {
		return addition5;
	}

	public void setAddition5(String addition5) {
		this.addition5 = addition5;
	}
	
	
	

}
