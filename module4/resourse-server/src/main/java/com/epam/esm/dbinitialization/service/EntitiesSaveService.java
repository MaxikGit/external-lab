package com.epam.esm.dbinitialization.service;

import com.epam.esm.certificate.model.GiftCertificateModel;
import com.epam.esm.dbinitialization.generator.EntityType;
import com.epam.esm.tag.model.TagModel;
import com.epam.esm.user.model.UserModel;

import java.util.List;

public interface EntitiesSaveService {

    boolean checkDatabaseIsEmpty();

    @SuppressWarnings("unchecked")
    void saveToDatabase(EntityType entityType, List<?> list);

    void setUserOrderRelations(int ordersPerUser, List<UserModel> users, List<GiftCertificateModel> certificates);

    void setCertificateTagsRelations(List<GiftCertificateModel> certificates, List<TagModel> tags);
}
