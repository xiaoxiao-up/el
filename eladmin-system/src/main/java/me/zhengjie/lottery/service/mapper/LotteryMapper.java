package me.zhengjie.lottery.service.mapper;

import me.zhengjie.base.BaseMapper;
import me.zhengjie.lottery.domain.Lottery;
import me.zhengjie.lottery.service.dto.LotteryDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author xiaoxiao
* @date 2019-12-29
*/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LotteryMapper extends BaseMapper<LotteryDto, Lottery> {

}