package com.epam.esm.common.repository;

import java.util.Map;

public interface PaginatedRepository {

    Long getTotalElements(String hqlQuery, Map<Integer, Object> queryParams);

    int getTotalPages(int size, long totalElements);
}
