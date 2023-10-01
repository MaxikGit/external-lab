package com.epam.esm.dbinitialization.util;

import com.epam.esm.certificate.model.GiftCertificateModel;
import com.epam.esm.order.dto.InvoiceDto;
import com.epam.esm.tag.model.TagModel;
import com.epam.esm.user.model.UserModel;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Profile("dev")
@Component
@RequiredArgsConstructor
@Slf4j
public class RandomWordsGenerator {

    private final Faker faker;

    public List<UserModel> generateUniqUsers(int size) {
        List<UserModel> result = new ArrayList<>();
        List<String> userEmails = getListOfUniqEmails(size);
        for (int i = 0; i < size; i++) {
            UserModel user = new UserModel();
            user.setName(faker.name().firstName());
            user.setLastName(faker.name().lastName());
            user.setEmail(userEmails.get(i));
            result.add(user);
        }
        return result;
    }

    public List<GiftCertificateModel> generateCertificates(int size) {
        int minPrice = 20;
        List<GiftCertificateModel> result = new ArrayList<>();
        List<Integer> nums = getListOfUniqNums(size, minPrice, (minPrice + size) * 10);
        for (int i = 0; i < size; i++) {
            result.add(GiftCertificateModel.builder()
                    .name(nums.get(i) + "$")
                    .description(faker.weather().description())
                    .price(BigDecimal.valueOf(nums.get(i)).multiply(BigDecimal.valueOf(1.33)))
                    .duration(faker.random().nextInt(1, 60))
                    .build());
        }
        return result;
    }

    public List<TagModel> generateTag(int size) {
        Set<TagModel> result = new HashSet<>();
        while (size != result.size()) {
            TagModel tag = new TagModel();
            tag.setName(faker.commerce().productName());
            result.add(tag);
        }
        return new ArrayList<>(result);
    }

    public Set<InvoiceDto> generateInvoices(int setSize, int minId, int maxId){
        return getListOfUniqNums(setSize, minId, maxId)
                .stream()
                .map(x -> new InvoiceDto(x, faker.random().nextInt(1, 10)))
                .collect(Collectors.toSet());
    }

    private List<String> getListOfUniqEmails(int listSize) {
        Set<String> result = new HashSet<>();
        while (listSize != result.size()) {
            result.add(faker.internet().safeEmailAddress());
        }
        return new ArrayList<>(result);
    }

    private List<Integer> getListOfUniqNums(int size, int min, int max) {
        if ((size > max - min) || (max - min < 0))
            return List.of(min);
        return ThreadLocalRandom.current()
                .ints(min, max + 1).distinct()
                .limit(size).boxed()
                .sorted()
                .toList();
    }
}
