package me.zhengjie.lottery.service.mapper;

import me.zhengjie.base.BaseMapper;
import me.zhengjie.lottery.domain.LotteryPlay;
import me.zhengjie.lottery.service.dto.LotteryPlayDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author 小小
* @date 2019-12-29
*/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LotteryPlayMapper extends BaseMapper<LotteryPlayDto, LotteryPlay> {

}