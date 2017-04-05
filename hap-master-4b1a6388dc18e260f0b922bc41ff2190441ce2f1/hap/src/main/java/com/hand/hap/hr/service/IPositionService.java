package com.hand.hap.hr.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.hr.dto.Position;
import com.hand.hap.system.service.IBaseService;

/**
 * 岗位服务接口.
 * 
 * @author hailin.xu@hand-china.com
 */
public interface IPositionService extends IBaseService<Position>, ProxySelf<IPositionService>{

	
    public List<Position> queryPosition(IRequest requestContext, Position position, int page, int pagesize);

}
