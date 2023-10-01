package com.epam.esm.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    @Min(value = 1, message = "ID should be greater than 0")
    private Integer id;

    @DecimalMin(value = "0", message = "Cost should be greater than zero")
    private BigDecimal cost;

    private String createDate;

    private Integer userId;
}
