package com.hand.hap.hr.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.hr.dto.HrOrgUnit;
import com.hand.hap.hr.mapper.OrgUnitMapper;
import com.hand.hap.hr.service.IOrgUnitService;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * *author:jialong.zuo@hand-china.com on 2016/9/16.
 */
@Service
@Transactional
public class OrgUnitServiceImpl extends BaseServiceImpl<HrOrgUnit> implements IOrgUnitService {

    @Autowired
    OrgUnitMapper mapper;

    @Override
    public List<HrOrgUnit> queryUnits(IRequest requestContext, HrOrgUnit unit, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        return mapper.queryUnits(unit);
    }

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }
}
