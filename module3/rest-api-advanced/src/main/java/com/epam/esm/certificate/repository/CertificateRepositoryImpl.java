package com.epam.esm.certificate.repository;

import com.epam.esm.certificate.model.GiftCertificateModel;
import com.epam.esm.common.repository.BaseCrudRepositoryImpl;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.epam.esm.certificate.repository.CertificateHqlConstants.*;
import static com.epam.esm.common.util.HqlQueryFinisherUtil.addOrderBy;

@Repository
public class CertificateRepositoryImpl extends BaseCrudRepositoryImpl<GiftCertificateModel> implements CertificateRepository {

    public CertificateRepositoryImpl() {
        super(GiftCertificateModel.class);
    }

    @Override
    public PagedModel<GiftCertificateModel> findByName(String name, String sortField, String sort, int offset, int size) {
        String hqlQuery = addOrderBy(HQL_SEARCH_BY_NAME, GiftCertificateModel.class, sortField, sort);
        return createPagedResults(hqlQuery, Map.of(1, name + "%"), offset, size);
    }

    @Override
    public PagedModel<GiftCertificateModel> findByTagAndName(String nameKey, List<String> tagNames, String sortField,
                                                             String sort, int offset, int size) {
        String hqlQuery = addOrderBy(hqlFindByTagListAndName(tagNames.size()),
                GiftCertificateModel.class, sortField, sort);
        Map<Integer, Object> queryParams = new HashMap<>();
        int positionInQuery = 1;
        queryParams.put(positionInQuery++, nameKey + "%");
        for (String tagName : tagNames) {
            queryParams.put(positionInQuery++, tagName + "%");
        }
        return createPagedResults(hqlQuery, queryParams, offset, size);
    }

    @Override
    public List<GiftCertificateModel> findByIds(Set<Integer> ids) {
        return entityManager.createQuery(HQL_SEARCH_BY_IDS, GiftCertificateModel.class)
                .setParameter(1, ids)
                .getResultList();
    }
}
