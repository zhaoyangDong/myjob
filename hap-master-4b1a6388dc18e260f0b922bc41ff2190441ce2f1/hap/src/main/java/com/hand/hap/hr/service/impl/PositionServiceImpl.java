package com.hand.hap.hr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.hr.dto.Position;
import com.hand.hap.hr.mapper.PositionMapper;
import com.hand.hap.hr.service.IPositionService;
import com.hand.hap.message.IMessagePublisher;
import com.hand.hap.system.service.impl.BaseServiceImpl;

/**
 * 功能服务接口实现.
 * 
 * @author hailin.xu@hand-china.com
 */
@Service
@Transactional
public class PositionServiceImpl extends BaseServiceImpl<Position> implements IPositionService {
    @Autowired
    PositionMapper positionMapper;

    @Autowired
    private IMessagePublisher messagePublisher;

    @Override
    public List<Position> queryPosition(IRequest requestContext, Position position, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        return positionMapper.queryPosition(position);
    }

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }

    @Override
    public List<Position> batchUpdate(IRequest request, List<Position> list) {
        super.batchUpdate(request, list);
        for (Position position : list) {
            messagePublisher.publish("position.change", position);
        }
        return list;
    }
}
