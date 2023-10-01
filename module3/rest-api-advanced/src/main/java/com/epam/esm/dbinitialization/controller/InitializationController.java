package com.epam.esm.dbinitialization.controller;

import com.epam.esm.dbinitialization.dto.InitializationDto;
import com.epam.esm.dbinitialization.service.DataInitializationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@Profile("dev")
@RestController
@RequestMapping("/generate")
@RequiredArgsConstructor
public class InitializationController {

    private final DataInitializationService.Starter initializationService;

    @PostMapping
    public Map<String, String> initializeData(@Valid @RequestBody InitializationDto dto) {
        return initializationService.generate(dto);
    }
}
