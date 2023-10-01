package com.epam.esm.dbinitialization.generator.impl;

import com.epam.esm.dbinitialization.generator.EntityType;
import com.epam.esm.dbinitialization.generator.RandomEntitiesGenerator;
import com.epam.esm.order.dto.InvoiceDto;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Profile("dev")
@Component
@RequiredArgsConstructor
@Slf4j
@Setter
public class RandomInvoicesGenerator implements RandomEntitiesGenerator<InvoiceDto> {

    private final Faker faker;
    private int minId = 1;
    private int maxId;

    @Override
    public List<InvoiceDto> generateUniqEntities(int size){
        if (maxId == 0){
            maxId = size;
        }
        return getListOfUniqNums(size, minId, maxId)
                .stream()
                .map(x -> new InvoiceDto(x, faker.random().nextInt(1, 10)))
                .toList();
    }

    @Override
    public EntityType getType() {return EntityType.INVOICE;}
}
