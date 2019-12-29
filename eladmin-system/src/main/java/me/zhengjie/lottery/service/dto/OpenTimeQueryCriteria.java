package me.zhengjie.lottery.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.util.List;
import me.zhengjie.annotation.Query;

/**
* @author xiaoxiao
* @date 2019-12-29
*/
@Data
public class OpenTimeQueryCriteria{

    /** 精确 */
    @Query
    private String code;

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String name;

    /** 精确 */
    @Query
    private Long issue;
    /** BETWEEN */
    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;
}