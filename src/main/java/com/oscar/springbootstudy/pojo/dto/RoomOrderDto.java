package com.oscar.springbootstudy.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.oscar.springbootstudy.base.BaseDto;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME;

/**
 * <p>Title: RoomOrder</p>
 * <p>Description: </p>
 *
 * @author os
 * @version 1.0.0
 * @date 2022/11/3 14:52
 */
@Data
public class RoomOrderDto extends BaseDto {
    private Long id;
    private String orderId;
    private Integer roomCode;
    private Integer customerId;
    private BigDecimal deposit;
    private Integer days;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate startDate;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate endDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DATE_TIME)
    private LocalDateTime checkinTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DATE_TIME)
    private LocalDateTime checkoutTime;
    private Integer status;
    private BigDecimal amount;
    private BigDecimal settlementAmount;

    private String phone;
    private Integer identifyType;
    private String identifyId;
}
