package com.epam.esm.dbinitialization.service;

import com.epam.esm.certificate.model.GiftCertificateModel;
import com.epam.esm.certificate.repository.CertificateRepository;
import com.epam.esm.dbinitialization.generator.impl.RandomInvoicesGenerator;
import com.epam.esm.order.service.OrderService;
import com.epam.esm.tag.model.TagModel;
import com.epam.esm.tag.repository.TagRepository;
import com.epam.esm.user.model.UserModel;
import com.epam.esm.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

@Profile("dev")
@Component
@RequiredArgsConstructor
public class EntityInitializationService {

    private final Random rnd;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final CertificateRepository certificateRepository;
    private final OrderService orderService;

    @Transactional
    public void initializeCertificates(List<GiftCertificateModel> certificates) {
        certificateRepository.saveAll(certificates);
    }

    @Transactional
    public void initializeTags(List<TagModel> tags) {
        tagRepository.saveAll(tags);
    }

    @Transactional
    public void initializeUsers(List<UserModel> users) {
        userRepository.saveAll(users);
    }

    @Transactional
    public void setCertificateTags(List<GiftCertificateModel> certificates, List<TagModel> tags) {
        certificates.forEach(x -> {
            x.setTags(getRandomTagSublist(rnd.nextInt(1, 5), tags));
            certificateRepository.save(x);
        });
    }

    @Transactional
    public void setUserOrders(List<UserModel> users, RandomInvoicesGenerator generator) {
        users.forEach(x -> orderService.placeOrder(x.getName(),
                new HashSet<>(generator.generateUniqEntities(rnd.nextInt(1, 10)))));
    }

    @Transactional
    public boolean checkDataExist() {
        return certificateRepository.findByNameStartingWith("", PageRequest.of(0, 1))
                .getTotalElements() > 0
                || userRepository.findAll(PageRequest.of(0, 1))
                .getTotalElements() > 0
                || tagRepository.findAll(PageRequest.of(0, 1))
                .getTotalElements() > 0;
    }

    private List<TagModel> getRandomTagSublist(int size, List<TagModel> sourceLst) {
        if (size > sourceLst.size())
            return sourceLst;
        List<TagModel> tags = new ArrayList<>(sourceLst);
        Collections.shuffle(tags);
        return tags.subList(0, size);
    }
}

