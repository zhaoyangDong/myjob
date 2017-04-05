package com.hand.hap.function.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.function.dto.ResourceCustomization;
import com.hand.hap.system.service.IBaseService;

/**
 * 
 * @author zhizheng.yang@hand-china.com
 *
 */
public interface IResourceCustomizationService extends IBaseService<ResourceCustomization>, ProxySelf<IResourceCustomizationService> {

    /**
     * 根据resourceId查询ResourcePath
     * @param requestContext
     * @param resourceId
     * @return
     */
    List<ResourceCustomization> selectResourceCustomizationsByResourceId(Long resourceId);
}
