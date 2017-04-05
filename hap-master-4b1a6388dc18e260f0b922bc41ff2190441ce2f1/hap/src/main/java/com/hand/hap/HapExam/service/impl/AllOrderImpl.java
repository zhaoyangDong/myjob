package com.hand.hap.HapExam.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.HapExam.dto.AllOrder;
import com.hand.hap.HapExam.mapper.AllOrderMapper;
import com.hand.hap.HapExam.service.IAllOrderService;


@Service
@Transactional
public class AllOrderImpl implements IAllOrderService{
	
	@Autowired
	private AllOrderMapper allordermapper;

	@Override
	public List<AllOrder> selectAllorder() {
		
		return allordermapper.selectAllorder();
	}

}
