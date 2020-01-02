package me.zhengjie.lottery.service;

import me.zhengjie.lottery.domain.Withdraw;
import me.zhengjie.lottery.service.dto.WithdrawDto;
import me.zhengjie.lottery.service.dto.WithdrawQueryCriteria;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @author 小小
* @date 2020-01-01
*/
public interface WithdrawService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(WithdrawQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<WithdrawDto>
    */
    List<WithdrawDto> queryAll(WithdrawQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id ID
     * @return WithdrawDto
     */
    WithdrawDto findById(Long id);

    /**
    * 创建
    * @param resources /
    * @return WithdrawDto
    */
    WithdrawDto create(Withdraw resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(Withdraw resources);

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
    void download(List<WithdrawDto> all, HttpServletResponse response) throws IOException;
}