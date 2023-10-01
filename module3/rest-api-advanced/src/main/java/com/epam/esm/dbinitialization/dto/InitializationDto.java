package com.epam.esm.dbinitialization.dto;

import lombok.Data;
import org.springframework.context.annotation.Profile;

import javax.validation.constraints.Min;

@Profile("dev")
@Data
public class InitializationDto {

    @Min(value = 1, message = "should be at least 1")
    private int certificates;

    @Min(value = 1, message = "should be at least 1")
    private int tags;

    @Min(value = 1, message = "should be at least 1")
    private int users;

    @Min(value = 1, message = "should be at least 1")
    private int ordersPerUser;
}
