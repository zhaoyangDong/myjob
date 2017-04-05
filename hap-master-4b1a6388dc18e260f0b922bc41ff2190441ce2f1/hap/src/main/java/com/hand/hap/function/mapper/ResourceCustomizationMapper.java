package com.hand.hap.function.mapper;


import java.util.List;

import com.hand.hap.function.dto.ResourceCustomization;
import com.hand.hap.mybatis.common.Mapper;

/**
 * 
 * @author zhizheng.yang@hand-china.com
 *
 */
public interface ResourceCustomizationMapper extends Mapper<ResourceCustomization>{

    List<ResourceCustomization> selectResourceCustomizationsByResourceId(Long resourceId);

}
