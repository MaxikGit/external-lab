package com.epam.esm.dbinitialization.generator;

import com.epam.esm.certificate.model.GiftCertificateModel;
import com.epam.esm.order.dto.InvoiceDto;
import com.epam.esm.tag.model.TagModel;
import com.epam.esm.user.model.UserModel;
import lombok.Getter;

@Getter
public enum EntityType {
    CERTIFICATE(GiftCertificateModel.class),
    TAG(TagModel.class),
    USER(UserModel.class),
    INVOICE(InvoiceDto.class);

    private final Class<?> typeClass;

    <T>EntityType(T typeClass) {
        this.typeClass = typeClass.getClass();
    }

}
