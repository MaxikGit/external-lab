package com.epam.esm.order.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class OrderCertificateId implements Serializable {

    @Column(name = "orders_id")
    private Integer ordersId;

    @Column(name = "gift_certificate_id")
    private Integer giftCertificateId;
}
