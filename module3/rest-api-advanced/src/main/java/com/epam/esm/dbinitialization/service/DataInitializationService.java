package com.epam.esm.dbinitialization.service;

import com.epam.esm.certificate.model.GiftCertificateModel;
import com.epam.esm.certificate.repository.CertificateRepository;
import com.epam.esm.dbinitialization.dto.InitializationDto;
import com.epam.esm.dbinitialization.util.RandomWordsGenerator;
import com.epam.esm.order.service.OrderService;
import com.epam.esm.tag.model.TagModel;
import com.epam.esm.tag.repository.TagRepository;
import com.epam.esm.user.model.UserModel;
import com.epam.esm.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Profile("dev")
@RequiredArgsConstructor
@Service
public class DataInitializationService {

    private final Random rnd;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final CertificateRepository certificateRepository;
    private final OrderService orderService;
    private final RandomWordsGenerator generator;

    @Transactional
    public void initializeCertificates(List<GiftCertificateModel> certificates) {
        certificates.forEach(certificateRepository::save);
    }

    @Transactional
    public void initializeTags(List<TagModel> tags) {
        tags.forEach(tagRepository::save);
    }

    @Transactional
    public void initializeUsers(List<UserModel> users) {
        users.forEach(userRepository::save);
    }

    @Transactional
    public void setCertificateTags(List<GiftCertificateModel> certificates, List<TagModel> tags) {
        certificates.forEach(x -> {
            x.setTags(getRandomTagSublist(rnd.nextInt(1, 5), tags));
            certificateRepository.update(x);
        });
    }

    @Transactional
    public void setUserOrders(int idMin, int idMax, List<UserModel> users) {
        users.forEach(x -> orderService.placeOrder(x.getId(),
                generator.generateInvoices(rnd.nextInt(1, 10), idMin, idMax)));
    }

    @Transactional
    public boolean checkIfExist() {
        return certificateRepository.findByName("", "", "", 0, 1)
                .getMetadata().getTotalElements() > 0
                || userRepository.findAll("", "", 0, 1)
                .getMetadata().getTotalElements() > 0
                || tagRepository.findAll("", "", 0, 1)
                .getMetadata().getTotalElements() > 0;
    }

    private List<TagModel> getRandomTagSublist(int size, List<TagModel> sourceLst) {
        if (size > sourceLst.size())
            return sourceLst;
        List<TagModel> tags = new ArrayList<>(sourceLst);
        Collections.shuffle(tags);
        return tags.subList(0, size);
    }

    /**
     * This class generates only initial data in empty tables.
     * If tables are not empty, you should clean data, to avoid uniqueness constraints violation
     * with duplicate values.
     */
    @Component
    @PropertySource("/hibernate.properties")
    @RequiredArgsConstructor
    public class Starter {

        private final RandomWordsGenerator generator;
        @Value("${hibernate.jdbc.batch_size}")
        private int batchSize;

        public Map<String, String> generate(InitializationDto dto) {
            Map<String, String> result = new LinkedHashMap<>();
            if (checkIfExist()) {
                result.put("message", "initial data already generated");
                return result;
            }
            List<GiftCertificateModel> certificates = generator.generateCertificates(dto.getCertificates());
            result.put("created certificates", String.valueOf(certificates.size()));
            List<TagModel> tags = generator.generateTag(dto.getTags());
            result.put("created tags", String.valueOf(tags.size()));
            List<UserModel> users = generator.generateUniqUsers(dto.getUsers());
            result.put("created users", String.valueOf(users.size()));

            for (int i = 0; i < certificates.size(); i = i + batchSize) {
                int toIndex = Math.min(i + batchSize, certificates.size());
                initializeCertificates(certificates.subList(i, toIndex));
            }
            for (int i = 0; i < tags.size(); i = i + batchSize) {
                int toIndex = Math.min(i + batchSize, tags.size());
                initializeTags(tags.subList(i, toIndex));
            }
            for (int i = 0; i < users.size(); i = i + batchSize) {
                int toIndex = Math.min(i + batchSize, users.size());
                initializeUsers(users.subList(i, toIndex));
            }

            for (int i = 0; i < certificates.size(); i = i + batchSize) {
                int toIndex = Math.min(i + batchSize, certificates.size());
                setCertificateTags(certificates.subList(i, toIndex), tags);
            }
            result.put("created certificate-tag relations", "done");

            int minId = Collections.min(certificates, Comparator.comparing(GiftCertificateModel::getId)).getId();
            int maxId = Collections.max(certificates, Comparator.comparing(GiftCertificateModel::getId)).getId();
            for (int i = 0; i < dto.getOrdersPerUser(); i++) {
                for (int j = 0; j < users.size(); j = j + batchSize) {
                    int toIndex = Math.min(j + batchSize, users.size());
                    setUserOrders(minId, maxId, users.subList(j, toIndex));
                }
            }
            result.put("created user-order relations", String.valueOf(dto.getOrdersPerUser() * users.size()));
            return result;
        }
    }
}



