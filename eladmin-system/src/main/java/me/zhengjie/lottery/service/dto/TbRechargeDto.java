package me.zhengjie.lottery.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author xiaoxiao
* @date 2020-01-01
*/
@Data
public class TbRechargeDto implements Serializable {

    /** 主键id */
    private Long id;

    /** 单号 */
    private Long orderId;

    /** 用户id */
    private Long userId;

    /** 充值金额 */
    private Long rechargePrice;

    /** 实际到账 */
    private Long actualPrice;

    /** 充值前金额 */
    private Long preRecharge;

    /** 充值方式 */
    private Integer type;

    /** 备注 */
    private String bak;

    /** 状态 */
    private Integer stat;

    /** 创建时间 */
    private Timestamp createTime;

    /** 更新时间 */
    private Timestamp updateTime;
}