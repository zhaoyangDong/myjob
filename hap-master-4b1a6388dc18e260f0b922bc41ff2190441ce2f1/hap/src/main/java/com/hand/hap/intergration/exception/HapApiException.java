package com.hand.hap.intergration.exception;

import com.hand.hap.core.exception.BaseException;

/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved.
 * Project Name:HmapParent
 * Package Name:hmap.core.hms.exception
 * Date:2016/8/15
 * Create By:jiguang.sun@hand-china.com
 */

public class HapApiException extends BaseException {

    private static final long serialVersionUID = -3250576758107608016L;

    public static final String EXCEPTION_CODE = "hms.interface";

    //根据sysName和apiName没有找到数据
    public static final String ERROR_NOT_FOUND = "error.request.url.not.found";

    //不支持的请求形式
    public static final String ERROR_REQUEST_FORMAT = "error.requestFormat.not.support";

    //不支持的接口类型
    public static final String ERROR_INTERFACE_TYPE = "error.interfaceType.not.support";

    //rest http请求失败
    public static final String ERROR_HTTP_REQUEST = "HTTP.GET.Request.Failed";

    //map2Xml 错误
    public static final String ERROR_MAP_TO_XML = "error.format_MapToXml";

    //Xml2Map 错误
    public static final String ERROR_XML_TO_MAP = "error.format_xmlToMap";

    //Json2Map 错误
    public static final String ERROR_JSON_TO_MAP = "error.format_jsonToMap";

    protected HapApiException(String code, String descriptionKey, Object[] parameters) {
        super(code, descriptionKey, parameters);
    }

    public HapApiException(String message, String descriptionKey){
//        super(EXCEPTION_CODE,message,descriptionKey);
        super(message,descriptionKey,null);

    }

}
