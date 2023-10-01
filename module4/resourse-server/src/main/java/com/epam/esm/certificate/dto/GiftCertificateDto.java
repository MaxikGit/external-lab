package com.epam.esm.certificate.dto;


import com.epam.esm.common.validation.OnCreate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GiftCertificateDto {

    @Min(value = 1, message = "ID should be greater than 0")
    private Integer id;

    @NotEmpty(groups = OnCreate.class, message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String name;

    private String description;

    @DecimalMin(value = "10", message = "Price should be greater than 10")
    private BigDecimal price;

    @Min(value = 1, message = "Duration should be greater than 0")
    private Integer duration;

    private String createDate;
    private String lastUpdateDate;
}
