package com.tu.ecommerce.service;

import com.tu.ecommerce.dao.StateRepository;
import com.tu.ecommerce.entity.State;
import com.tu.ecommerce.model.viewModel.StateView;
import com.tu.ecommerce.util.ModelMapperUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StateService {

    private final StateRepository stateRepository;

    private final ModelMapperUtil modelMapperUtil;

    public StateService(StateRepository stateRepository, ModelMapperUtil modelMapperUtil) {

        this.stateRepository = stateRepository;
        this.modelMapperUtil = modelMapperUtil;
    }

    public List<StateView> getStatesByCountryCode(String countryCode) {
        List<State> states = this.stateRepository.findStateByCountry_Code(countryCode);
        return this.modelMapperUtil.convertAll(states, StateView.class);
    }
}
