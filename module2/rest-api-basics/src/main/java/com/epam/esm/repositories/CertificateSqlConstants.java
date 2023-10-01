package com.epam.esm.repositories;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CertificateSqlConstants {

    static final String SQL_UPDATE = """
            UPDATE gift_certificate
            SET name=?, description=?, price=?, duration=?, last_update_date=?
            WHERE id=?
            """;
    static final String SQL_SELECT_ALL = "SELECT * FROM Gift_certificate";
    static final String SQL_SELECT_ONE = "SELECT * FROM Gift_certificate WHERE id=?";
    static final String SQL_INSERT = """
            INSERT INTO gift_certificate(name, description, price, duration, create_date, last_update_date)
            VALUES(?, ?, ?, ?, ?, ?)
            """;
    static final String SQL_SEARCH_BY_NAME =
            "SELECT * FROM gift_certificate WHERE name LIKE ? OR description LIKE ?";
    static final String SQL_SEARCH_BY_TAG_N_NAME = """
            SELECT GC.* FROM gift_certificate GC
            JOIN gift_certificate_tag GcT on GC.id = GcT.gift_certificate_id
            JOIN Tag T on T.id = GcT.tag_id
            WHERE T.name LIKE ? AND (GC.name LIKE ? OR GC.description LIKE ?)
            """;
    static final String SQL_DELETE = "DELETE FROM gift_certificate WHERE id=?";
    static final String SQL_COUNT_IDS = "SELECT count(c.id) FROM Gift_certificate c WHERE id=?";
}