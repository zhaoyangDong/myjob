package com.hand.hap.HapExam.service;

import java.util.List;

import com.hand.hap.HapExam.dto.Order;
import com.hand.hap.HapExam.dto.Order2;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;

public interface OrderService extends IBaseService<Order2>, ProxySelf<OrderService> {
	public List<Order2> selectorder(IRequest requestContext, Order2 order2, int page, int pagesize);

}
