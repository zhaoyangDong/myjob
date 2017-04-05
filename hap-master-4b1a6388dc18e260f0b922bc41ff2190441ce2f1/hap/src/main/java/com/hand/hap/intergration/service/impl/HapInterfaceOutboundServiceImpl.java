package com.hand.hap.intergration.service.impl;

import java.util.List;

import com.hand.hap.intergration.util.HapInvokeExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.google.common.base.Throwables;
import com.hand.hap.core.IRequest;
import com.hand.hap.intergration.beans.HapInvokeInfo;
import com.hand.hap.intergration.dto.HapInterfaceOutbound;
import com.hand.hap.intergration.mapper.HapInterfaceOutboundMapper;
import com.hand.hap.intergration.service.IHapInterfaceOutboundService;
import com.hand.hap.system.service.impl.BaseServiceImpl;

@Service
@Transactional
public class HapInterfaceOutboundServiceImpl extends BaseServiceImpl<HapInterfaceOutbound>
        implements IHapInterfaceOutboundService {

    @Autowired
    private HapInterfaceOutboundMapper outboundMapper;

    @Override
    public List<HapInterfaceOutbound> select(IRequest request, HapInterfaceOutbound condition, int pageNum,
            int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return outboundMapper.select(condition);
    }

    @Override
    public int outboundInvoke(HapInterfaceOutbound outbound, Throwable throwable) {

        if (throwable != null) {
            // 获取异常堆栈
            outbound.setStackTrace(HapInvokeExceptionUtils.getRootCauseStackTrace(throwable,10));
            outbound.setRequestStatus(HapInvokeInfo.REQUEST_FAILURE);
        }
        outbound.setResponseTime(System.currentTimeMillis() - HapInvokeInfo.REQUEST_START_TIME.get());
        return outboundMapper.insertSelective(outbound);
    }
}