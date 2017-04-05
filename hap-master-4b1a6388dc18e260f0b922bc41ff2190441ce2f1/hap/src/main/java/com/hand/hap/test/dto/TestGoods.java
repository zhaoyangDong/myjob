package com.hand.hap.test.dto;

import javax.persistence.Column;
import javax.persistence.Table;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.system.dto.BaseDTO;



@MultiLanguage
@Table(name = "test_goods_b")
public class TestGoods extends BaseDTO{
	
	@Column
	private int goodsId;
	
	@Column
	private int userId;
	
	@Column
	private String name;
	@Column
	private String color;
	@Column
	private String type;
	public int getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	@MultiLanguageField
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	@MultiLanguageField
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	

}
