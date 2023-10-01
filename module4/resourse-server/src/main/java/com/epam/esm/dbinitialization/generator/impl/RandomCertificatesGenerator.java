package com.epam.esm.dbinitialization.generator.impl;

import com.epam.esm.certificate.model.GiftCertificateModel;
import com.epam.esm.dbinitialization.generator.EntityType;
import com.epam.esm.dbinitialization.generator.RandomEntitiesGenerator;
import com.github.javafaker.Faker;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Profile("dev")
@Component
@RequiredArgsConstructor
@Slf4j
public class RandomCertificatesGenerator implements RandomEntitiesGenerator<GiftCertificateModel> {

    private final Faker faker;

    @Getter @Setter
    private int minPrice = 20;
    @Getter @Setter
    private int maxPrice;

    public List<GiftCertificateModel> generateUniqEntities(int size) {
        List<GiftCertificateModel> result = new ArrayList<>();
        if (maxPrice == 0){
            maxPrice = (minPrice + size) * 10;
        }
        List<Integer> nums = getListOfUniqNums(size, minPrice, maxPrice);
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

    @Override
    public EntityType getType() {
        return EntityType.CERTIFICATE;
    }
}
