package me.zhengjie.lottery.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author xiaoxiao
* @date 2020-01-01
*/
@Data
public class AccountChangeDto implements Serializable {

    /** 主键id */
    private Long id;

    /** 类型 */
    private Integer type;

    /** 用户id */
    private Long userId;

    /** 账变金额 */
    private Long change;

    /** 余额 */
    private Long balance;

    /** 备注 */
    private String remark;

    /** 状态 */
    private Integer stat;

    /** 创建时间 */
    private Timestamp createTime;

    /** 更新时间 */
    private Timestamp updateTime;
}