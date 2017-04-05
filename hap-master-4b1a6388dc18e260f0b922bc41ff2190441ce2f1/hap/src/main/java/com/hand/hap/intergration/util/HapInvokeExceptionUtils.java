package com.hand.hap.intergration.util;

import org.apache.commons.lang.exception.ExceptionUtils;

/**
 * Created by Qixiangyu on 2017/2/21.
 */
public class HapInvokeExceptionUtils {

    /**
     *  截取root cause的前n行信息
     *  @return
     */
    public static String getRootCauseStackTrace(Throwable throwable,int n ){
        String [] stackTraces =  ExceptionUtils.getRootCauseStackTrace(throwable);
        String root = null;
        if(stackTraces !=null && stackTraces.length >0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < n && i<stackTraces.length; i++) {
                sb.append(stackTraces[i]+"\n");
            }
            root = sb.toString();
        }
        return root;
    }

}
