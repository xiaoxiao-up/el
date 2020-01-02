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
@Table(name="tb_account_change")
public class AccountChange implements Serializable {

    /** 主键id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** 类型 */
    @Column(name = "type",nullable = false)
    @NotNull
    private Integer type;

    /** 用户id */
    @Column(name = "user_id")
    private Long userId;

    /** 账变金额 */
    @Column(name = "change",nullable = false)
    @NotNull
    private Long change;

    /** 余额 */
    @Column(name = "balance",nullable = false)
    @NotNull
    private Long balance;

    /** 备注 */
    @Column(name = "remark",nullable = false)
    @NotBlank
    private String remark;

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

    public void copy(AccountChange source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}