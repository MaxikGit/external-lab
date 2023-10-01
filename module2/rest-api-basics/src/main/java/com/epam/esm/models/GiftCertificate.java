package com.epam.esm.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class GiftCertificate {
    private Integer id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer duration;
    @EqualsAndHashCode.Exclude
    private LocalDateTime createDate;
    @EqualsAndHashCode.Exclude
    private LocalDateTime lastUpdateDate;
}
