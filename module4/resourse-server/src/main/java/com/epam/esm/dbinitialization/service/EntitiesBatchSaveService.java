package com.epam.esm.dbinitialization.service;

import com.epam.esm.certificate.model.GiftCertificateModel;
import com.epam.esm.dbinitialization.generator.EntityType;
import com.epam.esm.dbinitialization.generator.GeneratorFactory;
import com.epam.esm.dbinitialization.generator.impl.RandomInvoicesGenerator;
import com.epam.esm.tag.model.TagModel;
import com.epam.esm.user.model.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.epam.esm.dbinitialization.generator.EntityType.*;

@Profile("dev")
@Component
@RequiredArgsConstructor
public class EntitiesBatchSaveService implements EntitiesSaveService {

    @Value("${hibernate.jdbc.batch_size}")
    private int batchSize;
    private final GeneratorFactory generatorFactory;
    private final EntityInitializationService initializer;

    @Override
    public boolean checkDatabaseIsEmpty(){
        try {
            if (!initializer.checkDataExist()) {
                return true;
            }
        }catch(Exception ignore){
            //return false if database not exists
        }
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void saveToDatabase(EntityType entityType, List<?> list) {
        for (int i = 0; i < list.size(); i = i + batchSize) {
            int toIndex = Math.min(i + batchSize, list.size());
            switch (entityType) {
                case CERTIFICATE ->
                        initializer.initializeCertificates((List<GiftCertificateModel>) list.subList(i, toIndex));
                case TAG -> initializer.initializeTags((List<TagModel>) list.subList(i, toIndex));
                case USER -> initializer.initializeUsers((List<UserModel>) list.subList(i, toIndex));
            }
        }
    }

    @Override
    public void setUserOrderRelations(int ordersPerUser, List<UserModel> users, List<GiftCertificateModel> certificates) {
        if (users.isEmpty() || certificates.isEmpty() || ordersPerUser == 0) {
            return;
        }
        int minId = Collections.min(certificates, Comparator.comparing(GiftCertificateModel::getId)).getId();
        int maxId = Collections.max(certificates, Comparator.comparing(GiftCertificateModel::getId)).getId();
        for (int i = 0; i < ordersPerUser; i++) {
            for (int j = 0; j < users.size(); j = j + batchSize) {
                int toIndex = Math.min(j + batchSize, users.size());
                RandomInvoicesGenerator generator = (RandomInvoicesGenerator) generatorFactory.getGenerator(INVOICE);
                generator.setMinId(minId);
                generator.setMaxId(maxId);
                initializer.setUserOrders(users.subList(j, toIndex), generator);
            }
        }
    }

    @Override
    public void setCertificateTagsRelations(List<GiftCertificateModel> certificates, List<TagModel> tags) {
        for (int i = 0; i < certificates.size(); i = i + batchSize) {
            int toIndex = Math.min(i + batchSize, certificates.size());
            initializer.setCertificateTags(certificates.subList(i, toIndex), tags);
        }
    }
}
