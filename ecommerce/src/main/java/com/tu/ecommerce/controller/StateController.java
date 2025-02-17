package com.tu.ecommerce.controller;

import com.tu.ecommerce.model.viewModel.StateView;
import com.tu.ecommerce.service.StateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/states")
public class StateController {

    private final StateService stateService;

    public StateController(StateService stateService) {
        this.stateService = stateService;
    }

    @GetMapping("country/{code}")
    public List<StateView> getStatesByCountryCode(@PathVariable("code") String countryCode) {
        return this.stateService.getStatesByCountryCode(countryCode);
    }
}
