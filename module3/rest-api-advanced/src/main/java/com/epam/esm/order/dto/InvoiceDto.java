package com.epam.esm.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
public class InvoiceDto {

    @Min(value = 1, message = "ID should be greater than 0")
    private int productId;

    @EqualsAndHashCode.Exclude
    @Min(value = 1, message = "Quantity should be greater than 0")
    private int quantity;
}
