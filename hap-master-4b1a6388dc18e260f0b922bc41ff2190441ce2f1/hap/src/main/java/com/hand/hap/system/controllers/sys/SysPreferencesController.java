/*
 * #{copyright}#
 */
package com.hand.hap.system.controllers.sys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.util.WebUtils;

import com.hand.hap.core.ILanguageProvider;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.Language;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.system.dto.SysPreferences;
import com.hand.hap.system.service.ISysPreferencesService;

/**
 * 系统首选项Controller.
 * 
 * @author zhangYang
 *
 */
@Controller
public class SysPreferencesController extends BaseController {
    
    private static final String LOCALE_NAME = "locale";

    @Autowired
    private ISysPreferencesService sysPreferencesService;
    
    @Autowired
    private ILanguageProvider languageProvider;
    
    /**
     * 系统首选项界面
     * 
     * @param request HttpRequest
     * @return ResponseData 返回保存首选项集合，保存错误返回false
     * 
     */
    @RequestMapping(value = "/sys/um/sys_preferences.html")
    @ResponseBody
    public ModelAndView sysPreferences(final HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(getViewPath() + "/sys/um/sys_preferences");
        List<Language> languages = languageProvider.getSupportedLanguages();
        mv.addObject("languages", languages);
        return mv;
    }

    /**
     * 系统首选项保存
     * 
     * @param request
     *            统一上下文
     * @param sysPreferences
     *            系统首选项信息集合
     * @return ResponseData 返回保存首选项集合，保存错误返回false
     * 
     */
    @RequestMapping(value = "/sys/preferences/savePreferences")
    @ResponseBody
    public ResponseData savePreferences(final HttpServletRequest request, @RequestBody List<SysPreferences> sysPreferences, BindingResult result) {
        IRequest requestContext = createRequestContext(request);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        } else {
            for(SysPreferences preference: sysPreferences ) {
                preference.setUserId(requestContext.getUserId());
                if(LOCALE_NAME.equalsIgnoreCase(preference.getPreferences())){
                    WebUtils.setSessionAttribute(request, SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, StringUtils.parseLocaleString(preference.getPreferencesValue()));
                }
            }
            
            List<SysPreferences> lists = sysPreferencesService.saveSysPreferences(createRequestContext(request),sysPreferences);
            return new ResponseData(lists);
        }

    }

    /**
     * 查询当前用户首选项集合
     * 
     * @param request
     * @param sysPreferences
     *            根据SysPreferences.accountId;SysPreferences.preferencesLevel查询条件
     *            查询当前首选项
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/sys/preferences/queryPreferences")
    @ResponseBody
    public ResponseData queryPreferences(final HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        SysPreferences preference = new SysPreferences();
        preference.setUserId(requestContext.getUserId());
        List<SysPreferences> lists = sysPreferencesService.querySysPreferences(requestContext, preference);
        return new ResponseData(lists);
    }
}
