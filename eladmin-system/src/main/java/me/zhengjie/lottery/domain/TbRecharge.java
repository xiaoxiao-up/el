package me.zhengjie.lottery.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author xiaoxiao
* @date 2020-01-01
*/
@Entity
@Data
@Table(name="tb_recharge")
public class TbRecharge implements Serializable {

    /** 主键id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** 单号 */
    @Column(name = "order_id",nullable = false)
    @NotNull
    private Long orderId;

    /** 用户id */
    @Column(name = "user_id")
    private Long userId;

    /** 充值金额 */
    @Column(name = "recharge_price",nullable = false)
    @NotNull
    private Long rechargePrice;

    /** 实际到账 */
    @Column(name = "actual_price",nullable = false)
    @NotNull
    private Long actualPrice;

    /** 充值前金额 */
    @Column(name = "pre_recharge",nullable = false)
    @NotNull
    private Long preRecharge;

    /** 充值方式 */
    @Column(name = "type",nullable = false)
    @NotNull
    private Integer type;

    /** 备注 */
    @Column(name = "bak")
    private String bak;

    /** 状态 */
    @Column(name = "stat",nullable = false)
    @NotNull
    private Integer stat;

    /** 创建时间 */
    @Column(name = "create_time")
    private Timestamp createTime;

    /** 更新时间 */
    @Column(name = "update_time")
    private Timestamp updateTime;

    public void copy(TbRecharge source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}