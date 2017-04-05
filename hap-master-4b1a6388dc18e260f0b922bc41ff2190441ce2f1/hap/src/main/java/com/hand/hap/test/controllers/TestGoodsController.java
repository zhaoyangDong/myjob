package com.hand.hap.test.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.test.dto.TestGoods;
import com.hand.hap.test.dto.TestUser;
import com.hand.hap.test.service.ITestGoodsservice;


@Controller
public class TestGoodsController extends BaseController{
	@Autowired
	private ITestGoodsservice testgoodsservice;
	
	
	
	
	@RequestMapping(value = "/test/goods/selectallgoods")
    @ResponseBody
    public ResponseData query(TestGoods testgoods, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
IRequest requestContext = createRequestContext(request);
List<TestGoods> list = testgoodsservice.selectAll();
return new ResponseData(list);
}

	

}
