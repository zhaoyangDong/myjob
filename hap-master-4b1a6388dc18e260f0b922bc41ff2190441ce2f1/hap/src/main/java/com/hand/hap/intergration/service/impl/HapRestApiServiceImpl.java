package com.hand.hap.intergration.service.impl;

import com.github.pagehelper.StringUtil;
import com.hand.hap.intergration.beans.HapInvokeInfo;
import com.hand.hap.intergration.beans.HapTransferDataMapper;
import com.hand.hap.intergration.dto.HapInterfaceHeader;
import com.hand.hap.intergration.service.IHapApiService;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.DefaultValueProcessor;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

/**
 * Created by user on 2016/7/28.
 */
@Service
public class HapRestApiServiceImpl implements IHapApiService {

    private final Logger logger = LoggerFactory.getLogger(HapRestApiServiceImpl.class);

    public String get(String url, HapInterfaceHeader headerAndLineDTO, JSONObject params) throws Exception {
        String resultData = "";
        // TODO get请求把参数拼在url后面，根据自己需求更改
        if (params != null && params.size() > 0) {
            Iterator iterator = params.keys();
            url += "?";
            StringBuffer parm = new StringBuffer();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                parm.append(key + "=" + URLEncoder.encode(params.get(key).toString(), "utf-8") + "&");
            }
            String p = parm.substring(0, parm.length() - 1);
            // 供出站AOP获取参数信息
            HapInvokeInfo.OUTBOUND_REQUEST_PARAMETER.set(p);
            url += p;
        }

        logger.info("request url:{}", url);
        try {

            URL restServiceURL = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) restServiceURL.openConnection();
            httpURLConnection.setRequestMethod("GET");

            if (headerAndLineDTO.getRequestAccept() != null) {
                httpURLConnection.setRequestProperty("Accept", headerAndLineDTO.getRequestAccept());
            }
            String basicBase64;
            if (headerAndLineDTO.getAuthFlag().equals("Y")) {
                String e1 = headerAndLineDTO.getAuthUsername() + ":" + headerAndLineDTO.getAuthPassword();
                basicBase64 = new String(Base64.encodeBase64(e1.getBytes()));
                httpURLConnection.setRequestProperty("Authorization", "Basic " + basicBase64);
            }

            httpURLConnection.connect();
            HapInvokeInfo.HTTP_RESPONSE_CODE.set(httpURLConnection.getResponseCode());
            if (httpURLConnection.getResponseCode() != 200) {
                throw new RuntimeException(
                        "HTTP GET Request Failed with Error code : " + httpURLConnection.getResponseCode());
            }

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"));
            StringBuilder results = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                results.append(line);
            }
            resultData = results.toString();

            httpURLConnection.disconnect();

        } catch (MalformedURLException e) {
            logger.error(e.getMessage());
            throw e;

        } catch (IOException e) {
            logger.error(e.getMessage());
            throw e;
        }
        logger.info("responseData:{}", resultData);
        return resultData;
    }

    public String post(String url, HapInterfaceHeader headerAndLineDTO, String params) throws Exception {
        String resultData = "";
        HapInvokeInfo.OUTBOUND_REQUEST_PARAMETER.set(params);
        try {

            URL myURL = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) myURL.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            if (!params.isEmpty()) {
                connection.setDoOutput(true);
            }

            if (headerAndLineDTO.getRequestContentType() != null) {
                connection.setRequestProperty("Content-Type", headerAndLineDTO.getRequestContentType());
            } else {
                connection.setRequestProperty("Content-Type", "application/json");
            }

            String basicBase64;
            if (headerAndLineDTO.getAuthFlag().equals("Y")) {
                String e = headerAndLineDTO.getAuthUsername() + ":" + headerAndLineDTO.getAuthPassword();
                basicBase64 = new String(Base64.encodeBase64(e.getBytes()));
                connection.setRequestProperty("Authorization", "Basic " + basicBase64);
            }
            connection.connect();

            if (!params.isEmpty()) {
                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(params.getBytes());
                outputStream.flush();
                outputStream.close();
            }
            HapInvokeInfo.HTTP_RESPONSE_CODE.set(connection.getResponseCode());
            if (connection.getResponseCode() != 200) {
                throw new RuntimeException("HTTP GET Request Failed with Error code : " + connection.getResponseCode());
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            StringBuilder results = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                results.append(line);
            }
            reader.close();
            connection.disconnect();
            resultData = results.toString();

        } catch (MalformedURLException e) {
            logger.error(e.getMessage());
            throw e;

        } catch (IOException e) {
            logger.error(e.getMessage());
            throw e;
        }
        logger.info("responseData:{}", resultData);
        return resultData;

    }

    @Override
    public JSONObject invoke(HapInterfaceHeader headerAndLineDTO, JSONObject inbound) throws Exception {
        String url = headerAndLineDTO.getDomainUrl() + headerAndLineDTO.getIftUrl();
        logger.info("request url:{}", url);
        String data = null;
        JSONObject json = null;
        String inboundParam = " ";

        HapTransferDataMapper mapper = null;
        // 如果用户定义了包装类，那么就需要将inbound进行包装，本类一般都是客户化开发
        // 根据需要来做，使用时需要进行动态加载 by zongyun.zhou@hand-china.com
        if (StringUtil.isNotEmpty(headerAndLineDTO.getMapperClass())) {
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            try {
                Class c = cl.loadClass(headerAndLineDTO.getMapperClass());
                mapper = (HapTransferDataMapper) c.newInstance();
            } catch (ClassNotFoundException e) {
                logger.error("ClassNotFoundException:" + e.getMessage());
                throw e;
            } catch (InstantiationException e) {
                logger.error("InstantiationException:" + e.getMessage());
                throw e;
            } catch (IllegalAccessException e) {
                logger.error("IllegalAccessException:" + e.getMessage());
                throw e;
            }
        }

        if (headerAndLineDTO.getRequestMethod().equals("POST")) {
            if (inbound != null) {
                inboundParam = inbound.toString();
                if (mapper != null)
                    inboundParam = mapper.requestDataMap(inbound);
            }
            logger.info("params Xml :{}", inboundParam.toString());

            data = this.post(url, headerAndLineDTO, inboundParam);
            if (mapper != null) {
                data = mapper.responseDataMap(data);
            } else {
                data = this.post(url, headerAndLineDTO, inboundParam);
            }

        } else if (headerAndLineDTO.getRequestMethod().equals("GET")) {
            data = this.get(url, headerAndLineDTO, inbound);
            if (mapper != null) {
                data = mapper.responseDataMap(data);
            }
        }
        data = data.toString().replaceAll("null", "\"\"");

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerDefaultValueProcessor(String.class, new DefaultValueProcessor() {
            public Object getDefaultValue(Class type) {
                return JSONNull.getInstance();
            }
        });
        json = JSONObject.fromObject(data, jsonConfig);
        return json;
    }
}
