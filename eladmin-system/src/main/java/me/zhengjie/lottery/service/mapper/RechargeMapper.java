package me.zhengjie.lottery.service.mapper;

import me.zhengjie.base.BaseMapper;
import me.zhengjie.lottery.domain.Recharge;
import me.zhengjie.lottery.service.dto.RechargeDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author xiaoxiao
* @date 2020-01-01
*/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RechargeMapper extends BaseMapper<RechargeDto, Recharge> {

}