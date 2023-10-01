package com.epam.esm.order.repository;

import lombok.experimental.UtilityClass;

@UtilityClass
public class OrderHqlConstants {

    static final String HQL_SEARCH_BY_USER = "FROM OrderModel m WHERE m.user.id=?1";
}