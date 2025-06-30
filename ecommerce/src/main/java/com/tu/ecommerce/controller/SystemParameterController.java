package com.tu.ecommerce.controller;

import com.tu.ecommerce.model.viewModel.*;
import com.tu.ecommerce.service.SystemParameterService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/system-parameters")
public class SystemParameterController {

    private final SystemParameterService systemParameterService;

    public SystemParameterController(SystemParameterService systemParameterService) {
        this.systemParameterService = systemParameterService;
    }


    @GetMapping("code/{code}")
    public SystemParameterView getSystemParameter(@PathVariable("code") String code) {
        return this.systemParameterService.getSystemParameterByCode(code);
    }
}
