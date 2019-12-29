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
@Table(name="tb_lottery")
public class Lottery implements Serializable {

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

    /** 图标 */
    @Column(name = "icon",nullable = false)
    @NotBlank
    private String icon;

    /** 彩票类型 */
    @Column(name = "level",nullable = false)
    @NotNull
    private Integer level;

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

    public void copy(Lottery source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}