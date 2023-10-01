package com.epam.esm.user.repository;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UserHqlConstants {

    static final String HQL_SEARCH_BY_NAME =
            "FROM UserModel m WHERE m.name LIKE ?1 OR m.lastName LIKE ?1";
    static final String HQL_COUNT_EMAILS = "SELECT count(id) FROM UserModel WHERE email=?1";
}