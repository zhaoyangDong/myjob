package com.hand.hap.hr.mapper;

import com.hand.hap.hr.dto.HrOrgUnit;
import com.hand.hap.mybatis.common.Mapper;

import java.util.List;

/**
 * author:jialong.zuo@hand-china.com
 */
public interface OrgUnitMapper extends Mapper<HrOrgUnit> {
    /*
     * 查询所有部门
     */
    public List<HrOrgUnit> queryUnits(HrOrgUnit unit);
}
