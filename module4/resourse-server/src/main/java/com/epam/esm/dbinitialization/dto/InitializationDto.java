package com.epam.esm.dbinitialization.dto;

import lombok.Data;
import org.springframework.context.annotation.Profile;

import javax.validation.constraints.Min;

@Profile("dev")
@Data
public class InitializationDto {

    @Min(value = 0, message = "remove this field or make it 0")
    private int certificates;

    @Min(value = 0, message = "remove this field or make it 0")
    private int tags;

    @Min(value = 0, message = "remove this field or make it 0")
    private int users;

    @Min(value = 0, message = "remove this field or make it 0")
    private int ordersPerUser;
}
