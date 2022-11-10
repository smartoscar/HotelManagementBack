package com.oscar.springbootstudy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.oscar.springbootstudy.base.BasePojo;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>Title: RoomOrder</p>
 * <p>Description: </p>
 *
 * @author os
 * @version 1.0.0
 * @date 2022/11/3 14:52
 */
@Data
public class RoomOrder extends BasePojo {
    // 此注解在MB插入数据后自动回填ID
    @TableId(type = IdType.AUTO)
    private Long id;
    private String orderId;
    private Integer roomCode;
    private Integer customerId;
    private BigDecimal deposit;
    private Integer days;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime checkinTime;
    private LocalDateTime checkoutTime;
    private Integer status;
    private BigDecimal amount;
    private BigDecimal settlementAmount;
}
