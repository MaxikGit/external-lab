package com.epam.esm.utils;

import com.epam.esm.models.GiftCertificate;
import com.epam.esm.models.Tag;
import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class SortingOrderUtil {

    private static final Map<Class<?>, Map<String, String>> CLASS_FIELDS_NAME_MAP = new HashMap<>();

    static {
        Map<String, String> mapEntitySchemaTag = Map.of("id", "id", "name", "name");
        Map<String, String> mapEntitySchemaCertificate = Map.of("id", "id", "name", "name",
                "description", "description", "duration", "duration",
                "createDate", "create_date", "lastUpdateDate", "last_update_date");
        CLASS_FIELDS_NAME_MAP.put(Tag.class, mapEntitySchemaTag);
        CLASS_FIELDS_NAME_MAP.put(GiftCertificate.class, mapEntitySchemaCertificate);
    }

    public String addOrderBy(Class<?> aClass, String sortField, String sortDirection) {
        return " ORDER BY " + getSchemaFieldName(aClass, sortField) +
                ("asc".equalsIgnoreCase(sortDirection) ? " ASC" : " DESC");
    }

    private static String getSchemaFieldName(Class<?> aClass, String sortField) {
        String result = CLASS_FIELDS_NAME_MAP.get(aClass).get(sortField);
        return result == null ? "id" : result;
    }
}
