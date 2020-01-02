package me.zhengjie.lottery.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author xiaoxiao
* @date 2020-01-01
*/
@Data
public class PlayDto implements Serializable {

    /** 主键id */
    private Long id;

    /** 玩法名称 */
    private String name;

    /** 玩法简介 */
    private String title;

    /** 赔率 */
    private Long odds;

    /** 投注限制 */
    private Long orderMax;

    /** 状态 */
    private Integer stat;

    /** 创建时间 */
    private Timestamp createTime;

    /** 更新时间 */
    private Timestamp updateTime;
}