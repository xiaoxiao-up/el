package me.zhengjie.lottery.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author 小小
* @date 2020-01-01
*/
@Entity
@Data
@Table(name="tb_withdraw")
public class Withdraw implements Serializable {

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

    /** 手续费 */
    @Column(name = "fee",nullable = false)
    @NotNull
    private Long fee;

    /** 提现金额 */
    @Column(name = "withdraw_price",nullable = false)
    @NotNull
    private Long withdrawPrice;

    /** 开户银行 */
    @Column(name = "bank",nullable = false)
    @NotBlank
    private String bank;

    /** 银行类型 */
    @Column(name = "bank_type",nullable = false)
    @NotBlank
    private String bankType;

    /** 开户姓名 */
    @Column(name = "bank_name",nullable = false)
    @NotBlank
    private String bankName;

    /** 银行账号 */
    @Column(name = "bank_account",nullable = false)
    @NotBlank
    private String bankAccount;

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

    public void copy(Withdraw source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}