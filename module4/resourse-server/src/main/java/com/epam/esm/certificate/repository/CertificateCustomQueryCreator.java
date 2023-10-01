package com.epam.esm.certificate.repository;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Pageable;

import java.util.stream.Collectors;

@UtilityClass
public class CertificateCustomQueryCreator {

    private static final String SEARCH_BY_TAG_LIST_BASE = """
            SELECT m
            FROM GiftCertificateModel m
                %s
            WHERE (%s) AND (m.name LIKE ?1 OR m.description LIKE ?1)
            %s
                     """;
    private static final String SEARCH_BY_TAG_LIST_JOIN = "JOIN m.tags t";
    private static final String SEARCH_BY_TAG_LIST_LIKE = "t%d.name LIKE ?%d";

    /**
     * Method used to construct HQL query with list of tag names as parameters
     *
     * @param size        the size of the list with tag names
     * @param pageRequest container for paging and sorting information
     * @return resulting query, looking like:<br/>
     * SELECT m<br/>
     * FROM GiftCertificateModel m<br/>
     * JOIN m.tags t1<br/>
     * JOIN m.tags t2<br/>
     * ...<br/>
     * WHERE (t1.name LIKE ?1 AND t2.name LIKE ?2 AND ...)<br/>
     * AND (m.name LIKE ?1 OR m.description LIKE ?1);<br/>
     */
    public static String createFindByTagListAndNameQuery(int size, Pageable pageRequest) {
        int startingParamNum = 2;
        StringBuilder sbJoin = new StringBuilder(SEARCH_BY_TAG_LIST_JOIN).append(startingParamNum).append("\n");
        StringBuilder sbLike = new StringBuilder(String
                .format(SEARCH_BY_TAG_LIST_LIKE, startingParamNum, startingParamNum));
        startingParamNum++;
        size--;

        for (int i = startingParamNum; i < (size + startingParamNum); i++) {
            sbJoin.append(SEARCH_BY_TAG_LIST_JOIN)
                    .append(i)
                    .append("\n");
            sbLike.append(" AND ").append(String.format(SEARCH_BY_TAG_LIST_LIKE, i, i));
        }
        String orderBy = pageRequest.getSort().isSorted() ? addOrderBy(pageRequest) : "";
        return String.format(SEARCH_BY_TAG_LIST_BASE, sbJoin, sbLike, orderBy);
    }

    private static String addOrderBy(Pageable pageRequest) {
        return pageRequest.getSort().get()
                .map(x -> "m." + x.getProperty() + " " + x.getDirection().name())
                .collect(Collectors.joining(" ", " ORDER BY ", ""));
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