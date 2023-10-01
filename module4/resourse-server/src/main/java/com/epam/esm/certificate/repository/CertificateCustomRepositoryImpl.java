package com.epam.esm.certificate.repository;

import com.epam.esm.certificate.model.GiftCertificateModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.esm.certificate.repository.CertificateCustomQueryCreator.createFindByTagListAndNameQuery;
import static com.epam.esm.certificate.repository.CertificateCustomQueryCreator.createTotalPagesQuery;

@Repository
public class CertificateCustomRepositoryImpl implements CertificateCustomRepository {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public Page<GiftCertificateModel> findByTagsAndName(String name, List<String> tagNames, Pageable pageRequest) {
        Map<Integer, Object> queryParams = new HashMap<>();
        int positionInQuery = 1;
        queryParams.put(positionInQuery++, name + "%");
        for (String tagName : tagNames) {
            queryParams.put(positionInQuery++, tagName + "%");
        }
        String query = createFindByTagListAndNameQuery(tagNames.size(), pageRequest);
        return createPagedResults(query, queryParams, pageRequest);
    }

    private Page<GiftCertificateModel> createPagedResults(String hqlQuery, Map<Integer, Object> queryParamContainer,
                                                          Pageable pageRequest) {
        long totalElements = getTotalElements(hqlQuery, queryParamContainer);
        TypedQuery<GiftCertificateModel> query = entityManager.createQuery(hqlQuery, GiftCertificateModel.class);
        queryParamContainer.forEach(query::setParameter);
        List<GiftCertificateModel> list = query
                .setFirstResult(pageRequest.getPageNumber() * pageRequest.getPageSize())
                .setMaxResults(pageRequest.getPageSize())
                .getResultList();
        return new PageImpl<>(list, pageRequest, totalElements);
    }

    private Long getTotalElements(String hqlQuery, Map<Integer, Object> queryParams) {
        TypedQuery<Long> query = entityManager.createQuery(createTotalPagesQuery(hqlQuery), Long.class);
        queryParams.forEach(query::setParameter);
        return query.getSingleResult();
    }
}
