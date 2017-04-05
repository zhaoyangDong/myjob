/*
 * #{copyright}#
 */

package com.hand.hap.system.controllers.sys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.system.dto.Language;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.ILanguageService;

/**
 * @author shengyang.zhou@hand-china.com
 */
@Controller
@RequestMapping("/sys/language")
public class LanguageController extends BaseController {

    @Autowired
    private ILanguageService languageService;

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public @ResponseBody
    ResponseData query(HttpServletRequest request, Language example,
                       @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                       @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest iRequest = createRequestContext(request);
        return new ResponseData(languageService.select(iRequest, example, page, pagesize));
    }

    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData submit(HttpServletRequest request, @RequestBody List<Language> languageList,
            BindingResult result) {
        getValidator().validate(languageList, result);
        if (result.hasErrors()) {
            ResponseData rs = new ResponseData(false);
            rs.setMessage(getErrorMessage(result, request));
            return rs;
        }
        IRequest iRequest = createRequestContext(request);
        return new ResponseData(languageService.batchUpdate(iRequest, languageList));
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<Language> languageList) {
        languageService.batchDelete(languageList);
        return new ResponseData();
    }
}
