package com.hand.hap.HapExam.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.hand.hap.HapExam.dto.Order;
import com.hand.hap.HapExam.dto.Order2;
import com.hand.hap.HapExam.service.OrderService;
import com.hand.hap.core.IRequest;
import com.hand.hap.excel.dto.ColumnInfo;
import com.hand.hap.excel.dto.ExportConfig;
import com.hand.hap.excel.service.IExportService;
import com.hand.hap.excel.service.IHapExcelImportService;
import com.hand.hap.function.dto.Function;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;


@Controller
public class OredrContronller extends BaseController{
	
	@Autowired
	private OrderService orderservice;
	
	 @Autowired
	 IExportService excelService;
	 
	 @Autowired
	  IHapExcelImportService iImportService;

	 @Autowired
	 ObjectMapper objectMapper;
	
	
	@RequestMapping(value = "/HapExam/Order/query")
    @ResponseBody
    public ResponseData query(HttpServletRequest request,
    		Order2 order2,Order order,@RequestParam(defaultValue = DEFAULT_PAGE) int page,@RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
		
		System.out.println(order.getCompanyId()+"吃啥呢地理");
		IRequest iRequest = createRequestContext(request);
     List<Order2> list= orderservice.selectorder(iRequest, order2, page, pagesize);
        return new ResponseData(list);
    }
	
	
	
	
	
	  @RequestMapping(value = "/HapExam/Order/export")
	    public void createXLS(HttpServletRequest request, @RequestParam String config,
	            HttpServletResponse httpServletResponse) {
		  IRequest requestContext = createRequestContext(request);
	        try {
	            JavaType type = objectMapper.getTypeFactory().constructParametrizedType(ExportConfig.class,
	                    ExportConfig.class, Order.class, ColumnInfo.class);
	            ExportConfig<Function, ColumnInfo> exportConfig = objectMapper.readValue(config, type);
	            excelService.exportAndDownloadExcel("com.hand.hap.HapExam.mapper.OrderMapper.selectorder",
	                    exportConfig, request, httpServletResponse, requestContext);
	            
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }





	
	

}
