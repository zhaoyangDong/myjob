package com.hand.hap.HapExam.mapper;

import java.util.List;

import com.hand.hap.HapExam.dto.Order;
import com.hand.hap.HapExam.dto.Order2;
import com.hand.hap.hr.dto.Position;
import com.hand.hap.mybatis.common.Mapper;

public interface OrderMapper extends Mapper<Order2>{
	
	public List<Order2> selectorder(Order2 order2);

}
