package com.hand.hap.intergration.service.impl;


import com.github.pagehelper.PageHelper;
import com.hand.hap.cache.impl.ApiConfigCache;
import com.hand.hap.core.IRequest;
import com.hand.hap.intergration.dto.HapInterfaceHeader;
import com.hand.hap.mybatis.entity.Example;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hap.intergration.mapper.HapInterfaceHeaderMapper;
import com.hand.hap.intergration.service.IHapInterfaceHeaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jiguang.sun@hand-china.com on 2016/7/21.
 */
@Service
@Transactional
public class HapInterfaceHeaderServiceImpl extends BaseServiceImpl<HapInterfaceHeader> implements IHapInterfaceHeaderService {

    private final Logger logger = LoggerFactory.getLogger(HapInterfaceHeaderServiceImpl.class);

    @Autowired
    private HapInterfaceHeaderMapper hapInterfaceHeaderMapper;

    @Autowired
    private ApiConfigCache apiCache;


    @Override
    public List<HapInterfaceHeader> getAllHeader(IRequest requestContext ,HapInterfaceHeader interfaceHeader,int page, int pagesize) {

        PageHelper.startPage(page, pagesize);
        List<HapInterfaceHeader> list = hapInterfaceHeaderMapper.getAllHeader(interfaceHeader);//headerAndHeaderTlDTO
        return list;


    }



    @Override
    public List<HapInterfaceHeader> getHeaderAndLineList(IRequest requestContext ,HapInterfaceHeader interfaceHeader) {
        List<HapInterfaceHeader> list = hapInterfaceHeaderMapper.getHeaderAndLineList(interfaceHeader);
        if (list.isEmpty() || list.size() < 0) {
            list = hapInterfaceHeaderMapper.getHeaderByHeaderId(interfaceHeader);
        }
        return list;

    }

    @Override
    public HapInterfaceHeader getHeaderAndLine(String sysName, String apiName) {
        logger.info("sysName apiName:{}", sysName + apiName);
        HapInterfaceHeader headerAndLineDTO = apiCache.getValue(sysName + apiName);
        if (headerAndLineDTO == null) {
            HapInterfaceHeader headerAndLineDTO1 = hapInterfaceHeaderMapper.getHeaderAndLineBySysNameAndApiName(sysName, apiName);
            if (headerAndLineDTO1 != null) {
                apiCache.setValue(sysName + apiName, headerAndLineDTO1);
            }
            return headerAndLineDTO1;
        } else {
            return headerAndLineDTO;

        }

    }
    /*
   * 获取所有的header和line数据——> HeaderAndHeaderTlDTO
   * */
    @Override
    public List<HapInterfaceHeader> getAllHeaderAndLine() {
        return hapInterfaceHeaderMapper.getAllHeaderAndLine();
    }


    /*
    * 获取所有的header和line数据——> HeaderAndHeaderTlDTO(分页)
    * */
    @Override
    public List<HapInterfaceHeader> getAllHeaderAndLine(int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        List<HapInterfaceHeader> list = hapInterfaceHeaderMapper.getAllHeaderAndLine();
        return list;
    }

    @Override
    public List<HapInterfaceHeader> getHeaderByHeaderId(IRequest requestContext,HapInterfaceHeader HapInterfaceHeader) {
        return hapInterfaceHeaderMapper.getHeaderByHeaderId(HapInterfaceHeader);
    }

    @Override
    public HapInterfaceHeader getHeaderAndLineByLineId(HapInterfaceHeader headerAndLineDTO) {
        return hapInterfaceHeaderMapper.getHeaderAndLineBylineId(headerAndLineDTO);
    }

    @Override
    public int updateHeader(IRequest request, HapInterfaceHeader hmsInterfaceHeader) {

        int result = hapInterfaceHeaderMapper.updateByPrimaryKeySelective(hmsInterfaceHeader);
        if (result > 0) {
            // 修改头，修改后重新加入缓存
            apiCache.clear();
            apiCache.initLoad();
        }
        return result;
    }


}
