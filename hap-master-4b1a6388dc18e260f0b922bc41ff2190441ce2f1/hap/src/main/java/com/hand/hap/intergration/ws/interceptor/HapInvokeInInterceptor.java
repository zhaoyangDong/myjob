package com.hand.hap.intergration.ws.interceptor;

import java.io.InputStream;
import java.io.Reader;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.io.CachedWriter;
import org.apache.cxf.io.DelegatingInputStream;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.service.model.EndpointInfo;
import org.springframework.beans.factory.annotation.Autowired;

import com.hand.hap.intergration.beans.HapInvokeInfo;
import com.hand.hap.intergration.dto.HapInterfaceInbound;
import com.hand.hap.intergration.dto.HapInterfaceOutbound;
import com.hand.hap.intergration.service.IHapInterfaceInboundService;
import com.hand.hap.intergration.service.IHapInterfaceOutboundService;

/**
 * Created by Qixiangyu on 2016/12/2.
 */
@NoJSR250Annotations
public class HapInvokeInInterceptor extends AbstractPhaseInterceptor<Message> {

    @Autowired
    private IHapInterfaceInboundService inboundService;

    @Autowired
    private IHapInterfaceOutboundService outboundService;

    public HapInvokeInInterceptor() {
        super(Phase.RECEIVE);
    }

    @Override
    public void handleMessage(Message message) throws Fault {

        HapInterfaceOutbound outbound = HapInvokeInfo.OUTBOUND.get();
        if (outbound == null) {
            inboundInvoke(message);
        } else {
            // 出站请求，调用第三方的ws，返回的内容记录
            outboundInvoke(outbound, message);
        }
    }

    @Override
    public void handleFault(Message message) {
        HapInterfaceOutbound outbound = HapInvokeInfo.OUTBOUND.get();
        HapInterfaceInbound inbound = HapInvokeInfo.INBOUND.get();
        if (inbound != null) {
            Exception fault = message.getContent(Exception.class);
            inbound.setRequestStatus(HapInvokeInfo.REQUEST_FAILURE);
            inbound.setResponseTime(System.currentTimeMillis() - HapInvokeInfo.REQUEST_START_TIME.get());
            inboundService.inboundInvoke(HapInvokeInfo.HTTP_REQUEST.get(), inbound, fault);
            HapInvokeInfo.INBOUND.remove();
        } else if (outbound != null) {
            Exception fault = message.getContent(Exception.class);
            outboundService.outboundInvoke(outbound, fault);
            HapInvokeInfo.OUTBOUND.remove();
        }
    }

    protected void inboundInvoke(Message message) {
        Long startTime = System.currentTimeMillis();
        HapInterfaceInbound inbound = new HapInterfaceInbound();
        // 将信息共享，给OutInterceptor用
        HapInvokeInfo.INBOUND.set(inbound);
        HapInvokeInfo.REQUEST_START_TIME.set(startTime);
        inbound.setRequestTime(new Date());
        // 记录请求信息
        logRequestInfo(message);

        // 记录请求参数
        try {
            InputStream is = message.getContent(InputStream.class);
            if (is != null) {
                logInputStream(message, is);
            } else {
                Reader reader = message.getContent(Reader.class);
                if (reader != null) {
                    logReader(message, reader);
                }
            }
        } catch (Exception e) {
            throw new Fault(e);
        }
    }

    protected void outboundInvoke(HapInterfaceOutbound outbound, Message message) {
        // 记录返回参数
        Exception fault = message.getContent(Exception.class);
        Object responseCode = message.get(Message.RESPONSE_CODE);
        if (responseCode != null)
            outbound.setResponseCode(responseCode.toString());
        if ("200".equalsIgnoreCase(outbound.getResponseCode())) {
            try {
                InputStream is = message.getContent(InputStream.class);
                if (is != null) {
                    logInputStream(message, is);
                } else {
                    Reader reader = message.getContent(Reader.class);
                    if (reader != null) {
                        logReader(message, reader);
                    }
                }
            } catch (Exception e) {
                throw new Fault(e);
            }
            outbound.setRequestStatus(HapInvokeInfo.REQUEST_SUCESS);
        } else {
            outbound.setRequestStatus(HapInvokeInfo.REQUEST_FAILURE);
        }
        outboundService.outboundInvoke(outbound, null);
        HapInvokeInfo.OUTBOUND.remove();

    }

    protected void logRequestInfo(Message message) {
        String httpMethod = (String) message.get(Message.HTTP_REQUEST_METHOD);
        HapInterfaceInbound inbound = HapInvokeInfo.INBOUND.get();
        if (httpMethod != null) {
            inbound.setRequestMethod(httpMethod);
        }

        String uri = (String) message.get(Message.REQUEST_URI);
        HttpServletRequest http = (HttpServletRequest) message.get("HTTP.REQUEST");
        if (uri != null) {
            if (http.getContextPath() != null) {
                uri = uri.replaceAll(http.getContextPath(), "");
            }
            inbound.setInterfaceUrl(uri);
            String query = (String) message.get(Message.QUERY_STRING);
            if (query != null) {
                inbound.setRequestHeaderParameter(query);
            }
        }
        HapInvokeInfo.HTTP_REQUEST.set(http);

        // 获得serviceName
        Endpoint ep = message.getExchange().getEndpoint();
        EndpointInfo endpoint = ep.getEndpointInfo();
        String serviceName = endpoint.getService().getName().getLocalPart();
        inbound.setInterfaceName(serviceName);
    }

    protected void logInputStream(Message message, InputStream is) throws Exception {

        CachedOutputStream bos = new CachedOutputStream();
        // use the appropriate input stream and restore it later
        InputStream bis = is instanceof DelegatingInputStream ? ((DelegatingInputStream) is).getInputStream() : is;

        // we can stream the rest
        IOUtils.copy(bis, bos);
        bos.flush();
        bis = bos.getInputStream();
        HapInterfaceOutbound outbound = HapInvokeInfo.OUTBOUND.get();
        HapInterfaceInbound inbound = HapInvokeInfo.INBOUND.get();
        if (inbound != null) {
            inbound.setRequestBodyParameter(IOUtils.toString(bos.getInputStream()));
        } else if (outbound != null) {
            outbound.setResponseContent(IOUtils.toString(bos.getInputStream()));
        }

        // restore the delegating input stream or the input stream
        if (is instanceof DelegatingInputStream) {
            ((DelegatingInputStream) is).setInputStream(bis);
        } else {
            message.setContent(InputStream.class, bis);
        }
        bos.close();
    }

    protected void logReader(Message message, Reader reader) throws Exception {

        CachedWriter writer = new CachedWriter();
        IOUtils.copyAndCloseInput(reader, writer);
        message.setContent(Reader.class, writer.getReader());
        HapInterfaceOutbound outbound = HapInvokeInfo.OUTBOUND.get();
        HapInterfaceInbound inbound = HapInvokeInfo.INBOUND.get();
        if (outbound == null) {
            inbound.setRequestBodyParameter(IOUtils.toString(writer.getReader()));
        } else {
            outbound.setResponseContent(IOUtils.toString(writer.getReader()));
        }
    }

}
