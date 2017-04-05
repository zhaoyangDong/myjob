package com.hand.hap.intergration.ws.interceptor;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.transport.http.Address;
import org.apache.cxf.transport.http.HTTPConduit;
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
public class HapInvokeOutInterceptor extends AbstractPhaseInterceptor<Message> {

    @Autowired
    private IHapInterfaceInboundService inboundService;

    @Autowired
    private IHapInterfaceOutboundService outboundService;

    public HapInvokeOutInterceptor() {
        super(Phase.PRE_STREAM);
    }

    @Override
    public void handleMessage(Message message) throws Fault {

        if (message.getContent(Exception.class) == null) {
            // 处理响应内容
            HapInterfaceInbound inbound = HapInvokeInfo.INBOUND.get();
            // 表示先执行了inInterceptor 是一次入站请求
            if (inbound != null) {
                inboundInvoke(inbound, message);
            } else {
                // 是一次出站请求
                outboundInvoke(message);
            }
        }

    }

    @Override
    public void handleFault(Message message) {

        HapInterfaceInbound inbound = HapInvokeInfo.INBOUND.get();
        HapInterfaceOutbound outbound = HapInvokeInfo.OUTBOUND.get();
        if (inbound != null) {
            // 先走inInterceptor ,表示是入站请求异常
            // 获取错误堆栈
            Exception fault = message.getContent(Exception.class);
            // 调用信息
            inbound.setRequestStatus(HapInvokeInfo.REQUEST_FAILURE);
            inbound.setResponseTime(System.currentTimeMillis() - HapInvokeInfo.REQUEST_START_TIME.get());
            inboundService.inboundInvoke(HapInvokeInfo.HTTP_REQUEST.get(), inbound, fault);
            // 用完后将内容清除
            HapInvokeInfo.INBOUND.remove();
        } else if (outbound != null) {
            // 出站请求异常
            Exception fault = message.getContent(Exception.class);
            outboundService.outboundInvoke(outbound, fault);
            HapInvokeInfo.OUTBOUND.remove();
        }
    }

    protected void outboundInvoke(Message message) {
        HapInterfaceOutbound outbound = new HapInterfaceOutbound();
        HapInvokeInfo.OUTBOUND.set(outbound);
        HapInvokeInfo.REQUEST_START_TIME.set(System.currentTimeMillis());
        outbound.setRequestTime(new Date());
        String url;
        Object address = message.get(HTTPConduit.KEY_HTTP_CONNECTION_ADDRESS);
        if (address instanceof Address) {
            url = ((Address) address).getString();
        } else {
            url = "";
        }
        outbound.setInterfaceUrl(url);
        // 获得serviceName
        Endpoint ep = message.getExchange().getEndpoint();
        EndpointInfo endpoint = ep.getEndpointInfo();
        String serviceName = endpoint.getService().getName().getLocalPart();
        outbound.setInterfaceName(serviceName);
        getXmlContent(message);
    }

    protected void inboundInvoke(HapInterfaceInbound inbound, Message message) {

        inbound.setResponseContent(getXmlContent(message));
        inbound.setResponseTime(System.currentTimeMillis() - HapInvokeInfo.REQUEST_START_TIME.get());
        inbound.setRequestStatus(HapInvokeInfo.REQUEST_SUCESS);
        inboundService.inboundInvoke(HapInvokeInfo.HTTP_REQUEST.get(), inbound, null);
        HapInvokeInfo.INBOUND.remove();
    }

    // 获取请求或者响应的xml
    protected String getXmlContent(Message message) {
        String content = "";
        try {

            OutputStream os = message.getContent(OutputStream.class);

            CachedOutputStream cs = new CachedOutputStream();

            message.setContent(OutputStream.class, cs);

            message.getInterceptorChain().doIntercept(message);

            // CachedOutputStream csnew = (CachedOutputStream)
            // message.getContent(OutputStream.class);
            InputStream in = cs.getInputStream();

            String xml = IOUtils.toString(in);

            // 这里对xml做处理，处理完后同理，写回流中
            content = xml;
            HapInterfaceOutbound invokeOut = HapInvokeInfo.OUTBOUND.get();
            if (invokeOut != null) {
                invokeOut.setRequestParameter(content);
            }

            IOUtils.copy(new ByteArrayInputStream(xml.getBytes()), os);

            cs.flush();
            cs.close();
            os.flush();

            message.setContent(OutputStream.class, os);
            os.close();

        } catch (Exception e) {
            throw new Fault(e);
        }
        return content;
    }

}
