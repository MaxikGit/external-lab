package com.epam.esm.order.model;

import com.epam.esm.certificate.model.GiftCertificateModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name = "orders_gift_certificate")
public class OrderCertificateModel {

    @EmbeddedId
    private OrderCertificateId id = new OrderCertificateId();

    private int count;

    @ManyToOne
    @JoinColumn(name = "orders_id")
    @MapsId("ordersId")
    private OrderModel order;

    @ManyToOne
    @JoinColumn(name = "gift_certificate_id")
    @MapsId("giftCertificateId")
    private GiftCertificateModel certificate;

    public OrderCertificateModel(int count, OrderModel order, GiftCertificateModel certificate) {
        this.count = count;
        this.order = order;
        this.certificate = certificate;
    }
}
