package com.hand.hap.hr.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.hr.dto.HrOrgUnit;
import com.hand.hap.system.service.IBaseService;

import java.util.List;

/**
 * Created by zjl on 2016/9/16.
 */
public interface IOrgUnitService extends IBaseService<HrOrgUnit>, ProxySelf<IOrgUnitService> {
    /*
     * 查询所有unit
     */
    public List<HrOrgUnit> queryUnits(IRequest requestContext, HrOrgUnit unit, int page, int pagesize);
}
