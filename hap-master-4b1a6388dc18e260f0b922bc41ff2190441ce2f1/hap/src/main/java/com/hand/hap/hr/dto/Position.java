package com.hand.hap.hr.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;

/**
 * 岗位表
 *
 * @author hailin.xu@hand-china.com 
 */
@MultiLanguage
@Table(name = "hr_org_position_b")
@ExtensionAttribute(disable=true)
public class Position extends BaseDTO{

	  private static final long serialVersionUID = 1L;

		/**
	     * 表ID，主键，供其他表做外键.
	     */
	    @Id
	    @GeneratedValue
	    private Long positionId;

	    /**
	     * 部门id.
	     */
	    private Long unitId;
	    
	    /**
	     * 部门名称
	     * */
	    @Transient
	    private String unitName;

	    /**
	     * 岗位编码.
	     */
	    private String positionCode;
	    
	    /**
	     * 岗位名称.
	     */
	    @MultiLanguageField
	    private String name;

	    /**
	     * 岗位描述
	     */
	    @com.hand.hap.core.annotation.MultiLanguageField
	    private String description;

	    /**
	     * 上级岗位id.
	     */
	    private Long parentPositionId;
        /**
         * 上级岗位名称
         * */
	    @Transient
	    private String parentPositionName;
	    public String getUnitName() {
			return unitName;
		}

		public void setUnitName(String unitName) {
			this.unitName = unitName == null ? null : unitName.trim();
		}

		public String getParentPositionName() {
			return parentPositionName;
		}

		public void setParentPositionName(String parentPositionName) {
			this.parentPositionName = parentPositionName == null ? null : parentPositionName.trim();

		}

		public Long getPositionId() {
		    return positionId;
	    }
	
		public void setPositionId(Long positionId) {
			this.positionId = positionId;
		}
	
		public Long getUnitId() {
			return unitId;
		}
	
		public void setUnitId(Long unitId) {
			this.unitId = unitId;
		}
	
		public String getPositionCode() {
			return positionCode;
		}
	
		public void setPositionCode(String positionCode) {
			this.positionCode = positionCode;
		}
	
		public String getName() {
			return name;
		}
	
		public void setName(String name) {
	        this.name = name == null ? null : name.trim();

		}
	
		public String getDescription() {
			return description;
		}
	
		public void setDescription(String description) {
	        this.description = description == null ? null : description.trim();

		}
	
		public Long getParentPositionId() {
			return parentPositionId;
		}
	
		public void setParentPositionId(Long parentPositionId) {
			this.parentPositionId = parentPositionId;
		}


	    

}
