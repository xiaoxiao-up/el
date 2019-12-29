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
* @date 2019-12-29
*/
@Entity
@Data
@Table(name="tb_lottery_open")
public class LotteryOpen implements Serializable {

    /** 主键id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** 彩票编码 */
    @Column(name = "code",unique = true,nullable = false)
    @NotBlank
    private String code;

    /** 彩票名称 */
    @Column(name = "name",nullable = false)
    @NotBlank
    private String name;

    /** 期数 */
    @Column(name = "issue",nullable = false)
    @NotNull
    private Long issue;

    /** 开奖时间 */
    @Column(name = "open_time",nullable = false)
    @NotNull
    private Timestamp openTime;

    /** 开奖号码 */
    @Column(name = "open_number",nullable = false)
    @NotBlank
    private String openNumber;

    /** 注数 */
    @Column(name = "number",nullable = false)
    @NotNull
    private Long number;

    /** 投注金额 */
    @Column(name = "amount",nullable = false)
    @NotNull
    private Long amount;

    /** 中奖金额 */
    @Column(name = "win_amount",nullable = false)
    @NotNull
    private Long winAmount;

    /** 状态0-不开启，1-开启 */
    @Column(name = "stat",nullable = false)
    @NotNull
    private Integer stat;

    /** 创建时间 */
    @Column(name = "create_time")
    private Timestamp createTime;

    /** 更新时间 */
    @Column(name = "update_time")
    private Timestamp updateTime;

    public void copy(LotteryOpen source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}