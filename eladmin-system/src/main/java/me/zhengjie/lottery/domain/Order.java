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
@Table(name="tb_order")
public class Order implements Serializable {

    /** 主键id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** 注单号 */
    @Column(name = "order_id",nullable = false)
    @NotNull
    private Long orderId;

    /** 用户名 */
    @Column(name = "user_name",nullable = false)
    @NotBlank
    private String userName;

    /** 彩票类型 */
    @Column(name = "type")
    private String type;

    /** 玩法 */
    @Column(name = "play_name",nullable = false)
    @NotBlank
    private String playName;

    /** 彩票期号 */
    @Column(name = "issue",nullable = false)
    @NotBlank
    private String issue;

    /** 开奖结果 */
    @Column(name = "open",nullable = false)
    @NotBlank
    private String open;

    /** 投注内容 */
    @Column(name = "content")
    private String content;

    /** 倍数 */
    @Column(name = "multiple")
    private Long multiple;

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

    public void copy(Order source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}