/*
 * #{copyright}#
 */
package com.hand.hap.core.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeansException;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import com.hand.hap.system.service.IAccessService;
import com.hand.hap.system.service.IKendoLovService;
import com.hand.hap.system.service.ILovService;

import freemarker.template.SimpleHash;

/**
 * @author njq.niu@hand-china.com
 *
 *         2016年1月31日
 */
public class DefaultFreeMarkerView extends FreeMarkerView {

    private IKendoLovService kendoLovService;
    private ILovService lovService;
    private IAccessService accessService;
    private FreeMarkerBeanProvider beanProvider;

    protected FreeMarkerConfig autodetectConfiguration() throws BeansException {
        kendoLovService = getApplicationContext().getBean(IKendoLovService.class);
        lovService = getApplicationContext().getBean(ILovService.class);
        accessService = getApplicationContext().getBean(IAccessService.class);
        beanProvider = getApplicationContext().getBean(FreeMarkerBeanProvider.class);
        return super.autodetectConfiguration();
    }

    protected SimpleHash buildTemplateModel(Map<String, Object> model, HttpServletRequest request,
            HttpServletResponse response) {
        SimpleHash fmModel = super.buildTemplateModel(model, request, response);
        accessService.setRequest(request);
        fmModel.put("lovProvider", kendoLovService);
        fmModel.put("lovService", lovService);
        fmModel.put("accessService", accessService);
        Map<String, Object> beans = beanProvider.getAvailableBean();
        if (beans != null) {
            fmModel.putAll(beans);
        }
        return fmModel;
    }
}
