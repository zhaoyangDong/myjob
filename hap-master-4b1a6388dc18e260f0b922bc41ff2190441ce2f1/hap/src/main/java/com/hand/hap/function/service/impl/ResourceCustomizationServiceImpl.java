package com.hand.hap.function.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.hand.hap.cache.impl.ResourceCustomizationCache;
import com.hand.hap.core.IRequest;
import com.hand.hap.function.dto.ResourceCustomization;
import com.hand.hap.function.mapper.ResourceCustomizationMapper;
import com.hand.hap.function.service.IResourceCustomizationService;
import com.hand.hap.system.dto.DashBoard;
import com.hand.hap.system.service.impl.BaseServiceImpl;

/**
 * 
 * @author zhizheng.yang@hand-china.com
 *
 */
@Transactional
@Service
public class ResourceCustomizationServiceImpl extends BaseServiceImpl<ResourceCustomization> implements IResourceCustomizationService {

    @Autowired
    private ResourceCustomizationMapper resourceCustomizationMapper;

    @Autowired
    private ResourceCustomizationCache resourceCustomizationCache;
    

    @Override
    public List<ResourceCustomization> selectResourceCustomizationsByResourceId(Long resourceId) {
        return resourceCustomizationMapper.selectResourceCustomizationsByResourceId(resourceId);
    }
    
    
    public List<ResourceCustomization> batchUpdate(IRequest request, List<ResourceCustomization> list) {
        List<ResourceCustomization> result =  super.batchUpdate(request, list);
        if(list.size() > 0){
            ResourceCustomization r = list.get(0);
            resourceCustomizationCache.load(r.getResourceId().toString());
        }
        return result;
    }
    
    public int batchDelete(List<ResourceCustomization> list) {
        int size =  super.batchDelete(list);
        if(list.size() > 0){
            ResourceCustomization r = list.get(0);
            resourceCustomizationCache.load(r.getResourceId().toString());
        }
        return size;
    }
}
