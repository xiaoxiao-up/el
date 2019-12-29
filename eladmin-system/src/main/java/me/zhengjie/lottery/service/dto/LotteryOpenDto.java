package me.zhengjie.lottery.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author xiaoxiao
* @date 2019-12-29
*/
@Data
public class LotteryOpenDto implements Serializable {

    /** 主键id */
    private Long id;

    /** 彩票编码 */
    private String code;

    /** 彩票名称 */
    private String name;

    /** 期数 */
    private Long issue;

    /** 开奖时间 */
    private Timestamp openTime;

    /** 开奖号码 */
    private String openNumber;

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