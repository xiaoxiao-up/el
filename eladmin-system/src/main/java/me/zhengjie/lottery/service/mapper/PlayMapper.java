package me.zhengjie.lottery.service.mapper;

import me.zhengjie.base.BaseMapper;
import me.zhengjie.lottery.domain.Play;
import me.zhengjie.lottery.service.dto.PlayDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author xiaoxiao
* @date 2020-01-01
*/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PlayMapper extends BaseMapper<PlayDto, Play> {

}