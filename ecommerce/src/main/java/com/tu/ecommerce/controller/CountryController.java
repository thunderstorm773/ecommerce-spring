package com.tu.ecommerce.controller;

import com.tu.ecommerce.model.viewModel.CountryView;
import com.tu.ecommerce.service.CountryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("")
    public List<CountryView> getAllCountries() {
        return this.countryService.getAllCountries();
    }
}
