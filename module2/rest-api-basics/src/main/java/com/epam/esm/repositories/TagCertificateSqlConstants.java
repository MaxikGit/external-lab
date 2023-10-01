package com.epam.esm.repositories;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TagCertificateSqlConstants {

    public static final String SQL_SELECT_ALL =
            "SELECT * FROM gift_certificate_tag";
    public static final String SQL_SELECT_ONE =
            "SELECT * FROM gift_certificate_tag WHERE gift_certificate_id=? AND tag_id=?";
    public static final String SQL_SELECT_BY_TAG =
            "SELECT * FROM gift_certificate_tag WHERE tag_id=?";
    public static final String SQL_SELECT_BY_CERTIFICATE =
            "SELECT * FROM gift_certificate_tag WHERE gift_certificate_id=?";
    public static final String SQL_INSERT =
            "INSERT INTO gift_certificate_tag VALUES (?, ?)";
    public static final String SQL_COUNT_IDS =
            "SELECT count(tag_id) FROM gift_certificate_tag WHERE gift_certificate_id=? AND tag_id=?";
    public static final String SQL_DELETE =
            "DELETE FROM gift_certificate_tag WHERE gift_certificate_id=? AND tag_id=?";
}