package com.hand.hap.intergration.controllers;

import com.codahale.metrics.annotation.Timed;
import com.hand.hap.cache.impl.ApiConfigCache;
import com.hand.hap.intergration.annotation.HapInbound;
import com.hand.hap.intergration.annotation.HapOutbound;
import com.hand.hap.intergration.dto.HapInterfaceHeader;
import com.hand.hap.intergration.exception.HapApiException;
import com.hand.hap.intergration.service.IHapApiService;
import com.hand.hap.intergration.service.IHapInterfaceHeaderService;
import com.hand.hap.system.controllers.BaseController;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by xiangyiQi on 2016/11/2.
 */
@Controller
public class HapApiController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(HapApiController.class);

    @Autowired
    IHapInterfaceHeaderService headerService;

    @Resource(name = "plsqlBean")
    IHapApiService plsqlService;

    @Resource(name = "restBean")
    IHapApiService restService;

    @Resource(name = "soapBean")
    IHapApiService soapService;

    @Autowired
    ApiConfigCache apiCache;

    @RequestMapping(value = "/r/api", method = RequestMethod.POST)
    @ResponseBody
    @Timed
    @HapOutbound
    @HapInbound(apiName = "hap.invoke.apiname.interfacetranspond")
    public JSONObject sentRequest(HttpServletRequest request,  @RequestBody(required = false)  JSONObject params)
            throws Exception {
        String sysName = request.getParameter("sysName");
        String apiName = request.getParameter("apiName");
        logger.info("sysName:{}  apiName:{} ", sysName, apiName);
        logger.info("requestBody:{}", params);



        HapInterfaceHeader hapInterfaceHeader = headerService.getHeaderAndLine(sysName, apiName);
        logger.info("return HmsInterfaceHeader:{}", hapInterfaceHeader);

        Map map = new HashMap<String, Object>();
        if (hapInterfaceHeader == null) {
            throw new HapApiException(HapApiException.ERROR_NOT_FOUND, "根据sysName和apiName没有找到数据");
        }
        if (!hapInterfaceHeader.getRequestFormat().equals("raw")) {
            throw new HapApiException(HapApiException.ERROR_REQUEST_FORMAT, "不支持的请求形式");
        }
        JSONObject json = null;

        if (hapInterfaceHeader.getInterfaceType().equals("REST")) {
            json = restService.invoke(hapInterfaceHeader, params);

        } else if (hapInterfaceHeader.getInterfaceType().equals("SOAP")) {
            json = soapService.invoke(hapInterfaceHeader, params);
        } else if (hapInterfaceHeader.getInterfaceType().equals("PLSQL")) {
            json = plsqlService.invoke(hapInterfaceHeader, params);
        } else {
            throw new HapApiException(HapApiException.ERROR_INTERFACE_TYPE, "不支持的接口类型");
        }

        return json;

    }


}
