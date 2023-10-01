package com.epam.esm.repositories;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TagSqlConstants {

    public static final String SQL_UPDATE = "UPDATE tag SET name=? WHERE id=?";
    public static final String SQL_SELECT_ALL = "SELECT * FROM tag";
    public static final String SQL_SELECT_ONE = "SELECT * FROM tag WHERE id=?";
    public static final String SQL_SELECT_BY_NAME = "SELECT * FROM tag WHERE name=?";
    public static final String SQL_INSERT = "INSERT INTO tag (name) VALUES (?)";
    public static final String SQL_SEARCH_BY_NAME = "SELECT * FROM tag WHERE name LIKE ?";
    public static final String SQL_COUNT_IDS = "SELECT count(id) FROM tag WHERE id=?";
    public static final String SQL_COUNT_NAMES = "SELECT count(id) FROM tag WHERE name=?";
    public static final String SQL_DELETE = "DELETE FROM tag WHERE id=?";
}