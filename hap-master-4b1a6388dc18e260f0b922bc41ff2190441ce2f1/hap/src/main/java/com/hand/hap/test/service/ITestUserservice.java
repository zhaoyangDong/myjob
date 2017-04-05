package com.hand.hap.test.service;


import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hap.test.dto.TestUser;

public interface ITestUserservice extends IBaseService<TestUser>,ProxySelf<ITestUserservice> {
	
}
