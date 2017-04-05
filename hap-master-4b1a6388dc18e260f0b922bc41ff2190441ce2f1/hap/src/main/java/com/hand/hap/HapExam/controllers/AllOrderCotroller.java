package com.hand.hap.HapExam.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hand.hap.HapExam.dto.AllOrder;
import com.hand.hap.HapExam.dto.Order;
import com.hand.hap.HapExam.service.IAllOrderService;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;

@Controller
public class AllOrderCotroller extends BaseController{
	
	@Autowired
	private IAllOrderService iorderservice;
	
	
	@RequestMapping(value = "/HapExam/AllOrder/query")
    @ResponseBody
    public ResponseData query(HttpServletRequest request) {
     List<AllOrder> list= iorderservice.selectAllorder();
        return new ResponseData(list);
    }

}
