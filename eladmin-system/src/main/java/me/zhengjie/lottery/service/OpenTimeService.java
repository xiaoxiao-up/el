package me.zhengjie.lottery.service;

import me.zhengjie.lottery.domain.OpenTime;
import me.zhengjie.lottery.service.dto.OpenTimeDto;
import me.zhengjie.lottery.service.dto.OpenTimeQueryCriteria;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @author xiaoxiao
* @date 2019-12-29
*/
public interface OpenTimeService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(OpenTimeQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<OpenTimeDto>
    */
    List<OpenTimeDto> queryAll(OpenTimeQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id ID
     * @return OpenTimeDto
     */
    OpenTimeDto findById(Long id);

    /**
    * 创建
    * @param resources /
    * @return OpenTimeDto
    */
    OpenTimeDto create(OpenTime resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(OpenTime resources);

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
    void download(List<OpenTimeDto> all, HttpServletResponse response) throws IOException;
}