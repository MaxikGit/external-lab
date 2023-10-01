package com.epam.esm.common.util;

import com.epam.esm.certificate.model.GiftCertificateModel;
import com.epam.esm.order.model.OrderModel;
import com.epam.esm.tag.model.TagModel;
import com.epam.esm.user.model.UserModel;
import lombok.experimental.UtilityClass;

import java.util.Map;

@UtilityClass
public class EntitiesFieldsToSchemaMapper {

    static final Map<Class<?>, Map<String, String>> CLASS_FIELDS_NAME_MAP = Map.of(
            TagModel.class, Map.of(
                    "id", "id",
                    "name", "name"
            ),
            GiftCertificateModel.class, Map.of(
                    "id", "id",
                    "name", "name",
                    "description", "description",
                    "duration", "duration",
                    "createDate", "createDate",
                    "lastUpdateDate", "lastUpdateDate"
            ),
            UserModel.class, Map.of(
                    "id", "id",
                    "name", "name",
                    "lastName", "lastName",
                    "email", "email"
            ),
            OrderModel.class, Map.of(
                    "id", "id",
                    "cost", "cost",
                    "createTime", "createTime"
            )
    );
}