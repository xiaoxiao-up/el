package me.zhengjie.lottery.service;

import me.zhengjie.lottery.domain.LotteryPlay;
import me.zhengjie.lottery.service.dto.LotteryPlayDto;
import me.zhengjie.lottery.service.dto.LotteryPlayQueryCriteria;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @author 小小
* @date 2019-12-29
*/
public interface LotteryPlayService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(LotteryPlayQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<LotteryPlayDto>
    */
    List<LotteryPlayDto> queryAll(LotteryPlayQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id ID
     * @return LotteryPlayDto
     */
    LotteryPlayDto findById(Long id);

    /**
    * 创建
    * @param resources /
    * @return LotteryPlayDto
    */
    LotteryPlayDto create(LotteryPlay resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(LotteryPlay resources);

    /**
    * 多选删除
    * @param ids /
    */
    void deleteAll(Long[] ids);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<LotteryPlayDto> all, HttpServletResponse response) throws IOException;
}