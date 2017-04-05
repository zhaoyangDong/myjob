package com.hand.hap.HapExam.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.hand.hap.HapExam.dto.Order;
import com.hand.hap.HapExam.dto.Order2;
import com.hand.hap.HapExam.mapper.OrderMapper;
import com.hand.hap.HapExam.service.OrderService;
import com.hand.hap.core.IRequest;
import com.hand.hap.hr.dto.Position;
import com.hand.hap.system.service.impl.BaseServiceImpl;

@Service
@Transactional
public class OrderImpl extends BaseServiceImpl<Order2> implements OrderService {
	@Autowired
	private OrderMapper ordermapper;

	@Override
	public List<Order2> selectorder(IRequest requestContext, Order2 order2, int page, int pagesize) {
		PageHelper.startPage(page, pagesize);
		return ordermapper.selectorder(order2);
	}

	

}
