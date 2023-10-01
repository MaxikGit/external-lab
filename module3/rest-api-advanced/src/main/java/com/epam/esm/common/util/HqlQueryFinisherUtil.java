package com.epam.esm.common.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class HqlQueryFinisherUtil {

    public static String addOrderBy(String hqlQuery, Class<?> aClass, String sortField, String sortDirection) {
        return hqlQuery +
                " ORDER BY m." +
                getSchemaFieldName(aClass, sortField) +
                ("asc".equalsIgnoreCase(sortDirection) ? " ASC" : " DESC");
    }

    private static String getSchemaFieldName(Class<?> aClass, String sortField) {
        String result = EntitiesFieldsToSchemaMapper.CLASS_FIELDS_NAME_MAP.get(aClass).get(sortField);
        return result == null ? "id" : result;
    }

    public static String createTotalPagesQuery(String baseQuery) {
        StringBuilder sb = new StringBuilder("SELECT count(m.id) ");
        if (baseQuery.startsWith("SELECT")) {
            sb.append(baseQuery.substring(baseQuery.indexOf("FROM")));
        } else {
            sb.append(baseQuery);
        }
        int orderIndex = sb.indexOf("ORDER");
        if (orderIndex > 0) {
            sb.delete(orderIndex, sb.length());
        }
        return sb.toString();
    }
}
