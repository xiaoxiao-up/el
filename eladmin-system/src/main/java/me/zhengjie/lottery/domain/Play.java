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
@Table(name="tb_play")
public class Play implements Serializable {

    /** 主键id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** 玩法名称 */
    @Column(name = "name",nullable = false)
    @NotBlank
    private String name;

    /** 玩法简介 */
    @Column(name = "title")
    private String title;

    /** 赔率 */
    @Column(name = "odds",nullable = false)
    @NotNull
    private Long odds;

    /** 投注限制 */
    @Column(name = "order_max",nullable = false)
    @NotNull
    private Long orderMax;

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

    public void copy(Play source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}