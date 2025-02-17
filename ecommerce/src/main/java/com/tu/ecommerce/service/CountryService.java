package com.tu.ecommerce.service;

import com.tu.ecommerce.dao.CountryRepository;
import com.tu.ecommerce.entity.Country;
import com.tu.ecommerce.model.viewModel.CountryView;
import com.tu.ecommerce.util.ModelMapperUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {

    private final CountryRepository countryRepository;

    private final ModelMapperUtil modelMapperUtil;

    public CountryService(CountryRepository countryRepository,
                          ModelMapperUtil modelMapperUtil) {

        this.countryRepository = countryRepository;
        this.modelMapperUtil = modelMapperUtil;
    }

    public List<CountryView> getAllCountries() {
        List<Country> countries = this.countryRepository.findAll();
        return this.modelMapperUtil.convertAll(countries, CountryView.class);
    }
}
