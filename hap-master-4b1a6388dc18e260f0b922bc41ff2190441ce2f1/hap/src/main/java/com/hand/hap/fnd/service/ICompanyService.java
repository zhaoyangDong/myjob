package com.hand.hap.fnd.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.fnd.dto.Company;
import com.hand.hap.system.service.IBaseService;

import java.util.List;

/**
 * author:jialong.zuo@hand-china.com on 2016/10/9.
 */
public interface ICompanyService extends IBaseService<Company>,ProxySelf<ICompanyService> {
    public List<Company> selectAllCompany(IRequest requestContext, Company company, int page, int pagesize);
}
