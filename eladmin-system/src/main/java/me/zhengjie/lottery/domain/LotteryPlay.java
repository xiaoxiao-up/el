package me.zhengjie.lottery.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author 小小
* @date 2019-12-29
*/
@Entity
@Data
@Table(name="tb_lottery_play")
public class LotteryPlay implements Serializable {

    /** 主键id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** 玩法名称 */
    @Column(name = "lottery_id",nullable = false)
    @NotBlank
    private String lotteryId;

    /** 玩法简介 */
    @Column(name = "lottery_name")
    private String lotteryName;

    /** 赔率 */
    @Column(name = "play_id",nullable = false)
    @NotNull
    private Long playId;

    /** 状态 */
    @Column(name = "stat",nullable = false)
    @NotNull
    private Integer stat;

    /** 创建时间 */
    @Column(name = "create_time")
    @CreationTimestamp
    private Timestamp createTime;

    /** 更新时间 */
    @Column(name = "update_time")
    @UpdateTimestamp
    private Timestamp updateTime;

    public void copy(LotteryPlay source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}