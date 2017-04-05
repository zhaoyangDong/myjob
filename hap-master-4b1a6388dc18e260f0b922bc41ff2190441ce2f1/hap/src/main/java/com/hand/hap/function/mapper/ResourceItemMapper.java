/*
 * #{copyright}#
 */

package com.hand.hap.function.mapper;

import java.util.List;

import com.hand.hap.function.dto.Function;
import com.hand.hap.function.dto.Resource;
import com.hand.hap.function.dto.ResourceItem;
import com.hand.hap.mybatis.common.Mapper;

public interface ResourceItemMapper extends Mapper<ResourceItem> {
    
    List<ResourceItem> selectResourceItemsByResourceId(Resource resource);
    
    List<ResourceItem> selectResourceItemsByFunctionId(Function function);
    
    
    ResourceItem selectResourceItemByResourceIdAndItemId(ResourceItem record);
    
}