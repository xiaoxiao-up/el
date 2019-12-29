package me.zhengjie.lottery.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author 小小
* @date 2019-12-29
*/
@Data
public class LotteryPlayDto implements Serializable {

    /** 主键id */
    private Long id;

    /** 玩法名称 */
    private String lotteryId;

    /** 玩法简介 */
    private String lotteryName;

    /** 赔率 */
    private Long playId;

    /** 状态 */
    private Integer stat;

    /** 创建时间 */
    private Timestamp createTime;

    /** 更新时间 */
    private Timestamp updateTime;
}