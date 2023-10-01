package com.epam.esm.common.repository;

import lombok.experimental.UtilityClass;

@UtilityClass
public class BaseHqlConstants {

    private static final String HQL_SELECT_ALL = "FROM %s m";

    public static String hqlSelectAll(Class<?> clazz){
        return String.format(HQL_SELECT_ALL, clazz.getSimpleName());
    }
}