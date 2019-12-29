package me.zhengjie.lottery.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author xiaoxiao
* @date 2019-12-29
*/
@Data
public class LotteryDto implements Serializable {

    /** 主键id */
    private Long id;

    /** 彩票编码 */
    private String code;

    /** 彩票名称 */
    private String name;

    /** 图标 */
    private String icon;

    /** 彩票类型 */
    private Integer level;

    /** 状态 */
    private Integer stat;

    /** 创建时间 */
    private Timestamp createTime;

    /** 更新时间 */
    private Timestamp updateTime;
}