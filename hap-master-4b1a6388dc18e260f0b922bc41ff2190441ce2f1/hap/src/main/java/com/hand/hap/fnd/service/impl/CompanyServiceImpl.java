package com.hand.hap.fnd.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.fnd.dto.Company;
import com.hand.hap.fnd.mapper.CompanyMapper;
import com.hand.hap.fnd.service.ICompanyService;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * author:jialong.zuo@hand-china.com on 2016/10/9.
 */
@Service
@Transactional
public class CompanyServiceImpl extends BaseServiceImpl<Company> implements ICompanyService {

    @Autowired
    CompanyMapper companyMapper;

    @Override
    public List<Company> selectAllCompany(IRequest requestContext, Company company, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        return companyMapper.selectAllCompany(company);
    }
}
