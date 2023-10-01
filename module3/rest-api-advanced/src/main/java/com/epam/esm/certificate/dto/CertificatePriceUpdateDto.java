package com.epam.esm.certificate.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CertificatePriceUpdateDto{

    @NotNull(message = "ID should not be null")
    @Min(value = 1, message = "ID should be greater than 0")
    private Integer id;

    @NotNull(message = "Price should not be null")
    @DecimalMin(value = "10", message = "Price should be greater than 10")
    private BigDecimal price;
}
