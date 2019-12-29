package me.zhengjie.lottery.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author xiaoxiao
* @date 2019-12-29
*/
@Data
public class OpenTimeDto implements Serializable {

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

    /** 开始时间 */
    private Timestamp startTime;

    /** 截止时间 */
    private Timestamp endTime;

    /** 创建时间 */
    private Timestamp createTime;

    /** 更新时间 */
    private Timestamp updateTime;
}