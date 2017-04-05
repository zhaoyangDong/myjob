package com.hand.hap.hr.mapper;

import java.util.List;

import com.hand.hap.hr.dto.Position;
import com.hand.hap.mybatis.common.Mapper;

public interface PositionMapper extends Mapper<Position> {

    List<Position> selectPositionLov(Long positionId);

    List<Position> getPositionByEmployeeCode(String userId);

    Position getPositionByCode(String entityId);
    
    List<Position> queryPosition(Position position);
}
