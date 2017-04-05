/*
 * #{copyright}#
 */
package com.hand.hap.system.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.exception.BaseException;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.dto.DemoUser;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.system.mapper.DemoUserMapper;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;

/**
 * @author njq.niu@hand-china.com 仅用于演示ligerUI部分测试
 *
 *         2016年1月21日
 */
@Controller
public class LigerUIDemoController extends BaseController {

    @Autowired
    private DemoUserMapper demoUserMapper;

    @RequestMapping(value = "/log/{name}/{level}")
    public ResponseData testLogBack(@PathVariable String name, @PathVariable String level) {
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        lc.reset();
        Logger logger = lc.exists(name);
        logger.setLevel(Level.valueOf(level));

        return new ResponseData();
    }

    @RequestMapping(value = "/demo/form/load", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData demoFormData() {
        Map<String, Object> map = new HashMap<>();
        map.put("ShipName", "测试");
        map.put("creationTime", "2016-03-03 12:11:11");
        List<Object> list = new ArrayList<>();
        list.add(map);
        return new ResponseData(list);
    }

    @RequestMapping(value = "/demo/user/query", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData selectUsers(@ModelAttribute DemoUser user,
            @RequestParam(defaultValue = DEFAULT_PAGE) Integer page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) Integer pagesize) {
        PageHelper.startPage(page, pagesize);
        return new ResponseData(demoUserMapper.select(user));
    }

    @RequestMapping(value = "/demo/user/submit")
    @ResponseBody
    public ResponseData submitUsers(@RequestBody List<DemoUser> users, BindingResult result, HttpServletRequest request)
            throws BaseException {
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        } else {
            for (DemoUser user : users) {
                switch (user.get__status()) {
                case DTOStatus.ADD:
                    demoUserMapper.insertSelective(user);
                    break;
                case DTOStatus.UPDATE:
                    demoUserMapper.updateByPrimaryKeySelective(user);
                    break;
                case DTOStatus.DELETE:
                    demoUserMapper.deleteByPrimaryKey(user.getId());
                    break;
                default:
                    break;
                }
            }
            return new ResponseData(users);
        }
    }

    @RequestMapping("/demo/checkboxlist")
    @ResponseBody
    public List<Object> getArray(){
        List<Object> list = new ArrayList<>();

        list.add(new Country("prc","中国"));
        list.add(new Country("usa","美国"));
        list.add(new Country("jpa","日本"));
        list.add(new Country("ger","德国"));
        list.add(new Country("ers","俄罗斯"));
        return list;
    }

    public static class Country {
        String id;
        String name;
        public Country (String id,String name){
            this.id=id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
