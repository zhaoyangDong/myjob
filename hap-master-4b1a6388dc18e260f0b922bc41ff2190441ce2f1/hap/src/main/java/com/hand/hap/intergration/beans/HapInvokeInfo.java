package com.hand.hap.intergration.beans;

import com.hand.hap.intergration.dto.HapInterfaceInbound;
import com.hand.hap.intergration.dto.HapInterfaceOutbound;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Qixiangyu on 2016/11/21.
 */
public class HapInvokeInfo {

    public static final ThreadLocal<Integer> HTTP_RESPONSE_CODE = new ThreadLocal<>();

    public static final ThreadLocal<String> OUTBOUND_REQUEST_PARAMETER = new ThreadLocal<>();

    public static final String REQUEST_SUCESS = "success";

    public static final String REQUEST_FAILURE = "failure";

    public static final ThreadLocal<HapInterfaceInbound> INBOUND = new ThreadLocal<>();

    public static final ThreadLocal<HapInterfaceOutbound> OUTBOUND = new ThreadLocal<>();

    public static final ThreadLocal<Long> REQUEST_START_TIME = new ThreadLocal<>();

    public static final ThreadLocal<HttpServletRequest> HTTP_REQUEST = new ThreadLocal<>();

    public static final ThreadLocal<Object> REST_INVOKE_HANDLER = new ThreadLocal<>();
}
