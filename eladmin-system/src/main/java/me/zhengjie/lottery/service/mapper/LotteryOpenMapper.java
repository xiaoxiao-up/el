package me.zhengjie.lottery.service.mapper;

import me.zhengjie.base.BaseMapper;
import me.zhengjie.lottery.domain.LotteryOpen;
import me.zhengjie.lottery.service.dto.LotteryOpenDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author xiaoxiao
* @date 2019-12-29
*/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LotteryOpenMapper extends BaseMapper<LotteryOpenDto, LotteryOpen> {

}