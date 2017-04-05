package com.hand.hap.hr.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.hr.dto.HrOrgUnit;
import com.hand.hap.hr.mapper.OrgUnitMapper;
import com.hand.hap.hr.service.IOrgUnitService;
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
 * * Created by jialong.zuo@hand-china.com on 2016/9/16.
 */
@Controller
@RequestMapping(value = "/hr")
public class UnitController extends BaseController {

    @Autowired
    IOrgUnitService service;

    @Autowired
    OrgUnitMapper unitmapper;


    @RequestMapping(value = "/unit/query")
    @ResponseBody
    public ResponseData query(HttpServletRequest request, HrOrgUnit unit,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.queryUnits(requestCtx, unit, page, pagesize));
    }

    @RequestMapping(value = "/unit/queryall")
    @ResponseBody
    public ResponseData queryallunits(HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(unitmapper.queryUnits(new HrOrgUnit()));
    }

    @RequestMapping(value = "/unit/submit")
    @ResponseBody
    public ResponseData update(HttpServletRequest request, @RequestBody List<HrOrgUnit> units) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, units));
    }

    @RequestMapping(value = "/unit/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<HrOrgUnit> units) {
        service.batchDelete(units);
        return new ResponseData();
    }

}
