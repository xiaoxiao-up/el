package me.zhengjie.lottery.service.mapper;

import me.zhengjie.base.BaseMapper;
import me.zhengjie.lottery.domain.ChaseOrder;
import me.zhengjie.lottery.service.dto.ChaseOrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author lottery
* @date 2019-12-29
*/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ChaseOrderMapper extends BaseMapper<ChaseOrderDto, ChaseOrder> {

}