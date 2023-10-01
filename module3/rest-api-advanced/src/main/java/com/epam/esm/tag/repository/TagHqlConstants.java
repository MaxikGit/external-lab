package com.epam.esm.tag.repository;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TagHqlConstants {

    public static final String HQL_SEARCH_BY_NAME = "FROM TagModel m WHERE m.name LIKE ?1";
    public static final String HQL_GET_BY_NAME = "FROM TagModel m WHERE m.name=?1";
    public static final String HQL_COUNT_NAMES = "SELECT count(id) FROM TagModel WHERE name=?1";
    static final String HQL_USER_POPULAR_TAG_INFO = """
            SELECT t
            FROM TagModel t
            JOIN t.certificates c
            JOIN c.orderCertificateModels ocm
            JOIN ocm.order o
            JOIN o.user u
            WHERE u.id = ?1
            GROUP BY t
            ORDER BY count(t.id) DESC
            """;
}