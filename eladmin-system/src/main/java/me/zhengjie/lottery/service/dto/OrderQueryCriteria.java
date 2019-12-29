package me.zhengjie.lottery.service.dto;

import lombok.Data;
import java.util.List;
import me.zhengjie.annotation.Query;

/**
* @author xiaoxiao
* @date 2019-12-29
*/
@Data
public class OrderQueryCriteria{

    /** 精确 */
    @Query
    private Long orderId;

    /** 精确 */
    @Query
    private String issue;
}