package com.hand.hap.fnd.mapper;

import com.hand.hap.fnd.dto.Company;
import com.hand.hap.mybatis.common.Mapper;

import java.util.List;

/**
 * author:jialong.zuo@hand-china.com on 2016/10/9.
 */
public interface CompanyMapper extends Mapper<Company> {
    public List<Company> selectAllCompany(Company company);

}
