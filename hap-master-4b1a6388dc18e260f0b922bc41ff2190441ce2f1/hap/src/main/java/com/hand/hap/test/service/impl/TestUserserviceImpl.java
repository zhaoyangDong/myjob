package com.hand.hap.test.service.impl;




import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hap.test.dto.TestUser;

import com.hand.hap.test.service.ITestUserservice;


@Service
@Transactional
public class TestUserserviceImpl extends BaseServiceImpl<TestUser> implements ITestUserservice {
	
 
	
}
