package me.zhengjie.lottery.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author xiaoxiao
* @date 2019-12-29
*/
@Data
public class OrderDto implements Serializable {

    /** 主键id */
    private Long id;

    /** 注单号 */
    private Long orderId;

    /** 用户名 */
    private String userName;

    /** 彩票类型 */
    private String type;

    /** 玩法 */
    private String playName;

    /** 彩票期号 */
    private String issue;

    /** 开奖结果 */
    private String open;

    /** 投注内容 */
    private String content;

    /** 倍数 */
    private Long multiple;

    /** 注数 */
    private Long number;

    /** 投注金额 */
    private Long amount;

    /** 中奖金额 */
    private Long winAmount;

    /** 状态0-不开启，1-开启 */
    private Integer stat;

    /** 创建时间 */
    private Timestamp createTime;

    /** 更新时间 */
    private Timestamp updateTime;
}