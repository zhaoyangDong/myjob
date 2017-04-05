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
@Table(name = "hap_inv_inventory_items_b")
public class HapInvInventoryItems extends BaseDTO{
	
	@Id
	@Column
	@GeneratedValue(generator = GENERATOR_TYPE)
	private Long inventoryItemId;
	
	@NotEmpty
	@Column
	private String itemCode;
	
	@NotEmpty
	@Column
	private String itemUom;
	
	@NotEmpty
	@Column
	private String itemDescription;
	
	@NotEmpty
	@Column
	private String enabledFlag;

	public Long getInventoryItemId() {
		return inventoryItemId;
	}

	public void setInventoryItemId(Long inventoryItemId) {
		this.inventoryItemId = inventoryItemId;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemUom() {
		return itemUom;
	}

	public void setItemUom(String itemUom) {
		this.itemUom = itemUom;
	}

	
	@MultiLanguageField
	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public String getEnabledFlag() {
		return enabledFlag;
	}

	public void setEnabledFlag(String enabledFlag) {
		this.enabledFlag = enabledFlag;
	}
	
	
	
	

}
