package com.hand.hap.fnd.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.fnd.dto.Company;
import com.hand.hap.fnd.service.ICompanyService;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * author:jialong.zuo@hand-china.com  on 2016/10/9.
 */
@Controller
@RequestMapping(value ="/fnd")
public class CompanyController extends BaseController {
    @Autowired
    ICompanyService companyService;

    @RequestMapping(value ="/company/query")
    @ResponseBody
    public ResponseData select(Company company,HttpServletRequest request, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                               @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize){
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(companyService.selectAllCompany(requestCtx,company,page,pagesize));
    }

    @RequestMapping(value="/company/update")
    @ResponseBody
    public ResponseData update(@RequestBody List<Company> companies,HttpServletRequest request){
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(companyService.batchUpdate(requestCtx, companies));
    }

    @RequestMapping(value = "/company/remove")
    @ResponseBody
    public ResponseData delete(@RequestBody List<Company> companies,HttpServletRequest request){
        IRequest requestCtx =createRequestContext(request);
        companyService.batchDelete(companies);
        return new ResponseData();
    }
}
