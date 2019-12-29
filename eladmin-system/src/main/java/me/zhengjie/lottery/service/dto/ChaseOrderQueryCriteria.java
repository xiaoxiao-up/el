package me.zhengjie.lottery.service.dto;

import lombok.Data;
import java.util.List;
import me.zhengjie.annotation.Query;

/**
* @author lottery
* @date 2019-12-29
*/
@Data
public class ChaseOrderQueryCriteria{

    /** 精确 */
    @Query
    private Long orderId;

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String userName;

    /** 精确 */
    @Query
    private Long issue;
}