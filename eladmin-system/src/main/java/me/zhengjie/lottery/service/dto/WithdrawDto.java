package me.zhengjie.lottery.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author 小小
* @date 2020-01-01
*/
@Data
public class WithdrawDto implements Serializable {

    /** 主键id */
    private Long id;

    /** 单号 */
    private Long orderId;

    /** 用户id */
    private Long userId;

    /** 手续费 */
    private Long fee;

    /** 提现金额 */
    private Long withdrawPrice;

    /** 开户银行 */
    private String bank;

    /** 银行类型 */
    private String bankType;

    /** 开户姓名 */
    private String bankName;

    /** 银行账号 */
    private String bankAccount;

    /** 状态 */
    private Integer stat;

    /** 创建时间 */
    private Timestamp createTime;

    /** 更新时间 */
    private Timestamp updateTime;
}