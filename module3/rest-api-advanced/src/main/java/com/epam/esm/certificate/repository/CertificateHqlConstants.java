package com.epam.esm.certificate.repository;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CertificateHqlConstants {

    static final String HQL_SEARCH_BY_NAME =
            "FROM GiftCertificateModel m WHERE m.name LIKE ?1 OR m.description LIKE ?1";
    static final String HQL_SEARCH_BY_IDS =
            "FROM GiftCertificateModel m WHERE m.id IN (?1)";
    private static final String SEARCH_BY_TAG_LIST_BASE = """
            SELECT m
            FROM GiftCertificateModel m
                %s
            WHERE (%s) AND (m.name LIKE ?1 OR m.description LIKE ?1)
                     """;
    private static final String SEARCH_BY_TAG_LIST_JOIN = "JOIN m.tags t";
    private static final String SEARCH_BY_TAG_LIST_LIKE = "t%d.name LIKE ?%d";

    /**
     * Method used to construct HQL query with list of tag names as parameters
     * """
     * SELECT m
     * FROM GiftCertificateModel m
     * JOIN m.tags t1
     * JOIN m.tags t2
     * ...
     * WHERE (t1.name LIKE ?1 AND t2.name LIKE ?2 AND ...)
     * AND (m.name LIKE ?1 OR m.description LIKE ?1)
     * """;
     *
     * @param size size of list with tag names
     * @return hql query string
     */
    public static String hqlFindByTagListAndName(int size) {
        int startingParamNum = 2;
        StringBuilder sbJoin = new StringBuilder(SEARCH_BY_TAG_LIST_JOIN)
                .append(startingParamNum).append("\n");
        StringBuilder sbLike = new StringBuilder(String
                .format(SEARCH_BY_TAG_LIST_LIKE, startingParamNum, startingParamNum));
        startingParamNum++;
        size--;

        for (int i = startingParamNum; i < (size + startingParamNum); i++) {
            sbJoin.append(SEARCH_BY_TAG_LIST_JOIN)
                    .append(i)
                    .append("\n");
            sbLike.append(" AND ")
                    .append(String.format(SEARCH_BY_TAG_LIST_LIKE, i, i));
        }
        return String.format(SEARCH_BY_TAG_LIST_BASE, sbJoin, sbLike);
    }
}