package me.zhengjie.lottery.service;

import me.zhengjie.lottery.domain.AccountChange;
import me.zhengjie.lottery.service.dto.AccountChangeDto;
import me.zhengjie.lottery.service.dto.AccountChangeQueryCriteria;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @author xiaoxiao
* @date 2020-01-01
*/
public interface AccountChangeService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(AccountChangeQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<AccountChangeDto>
    */
    List<AccountChangeDto> queryAll(AccountChangeQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id ID
     * @return AccountChangeDto
     */
    AccountChangeDto findById(Long id);

    /**
    * 创建
    * @param resources /
    * @return AccountChangeDto
    */
    AccountChangeDto create(AccountChange resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(AccountChange resources);

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
    void download(List<AccountChangeDto> all, HttpServletResponse response) throws IOException;
}