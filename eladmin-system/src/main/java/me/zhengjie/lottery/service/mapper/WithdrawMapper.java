package me.zhengjie.lottery.service.mapper;

import me.zhengjie.base.BaseMapper;
import me.zhengjie.lottery.domain.Withdraw;
import me.zhengjie.lottery.service.dto.WithdrawDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author 小小
* @date 2020-01-01
*/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WithdrawMapper extends BaseMapper<WithdrawDto, Withdraw> {

}