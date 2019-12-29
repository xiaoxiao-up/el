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
* @author xiaoxiao
* @date 2019-12-29
*/
@Entity
@Data
@Table(name="tb_open_time")
public class OpenTime implements Serializable {

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

    /** 开始时间 */
    @Column(name = "start_time",nullable = false)
    @NotNull
    private Timestamp startTime;

    /** 截止时间 */
    @Column(name = "end_time",nullable = false)
    @NotNull
    private Timestamp endTime;

    /** 创建时间 */
    @Column(name = "create_time")
    @CreationTimestamp
    private Timestamp createTime;

    /** 更新时间 */
    @Column(name = "update_time")
    @UpdateTimestamp
    private Timestamp updateTime;

    public void copy(OpenTime source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}