package com.tu.ecommerce.service;

import com.tu.ecommerce.dao.SystemParameterRepository;
import com.tu.ecommerce.entity.SystemParameter;
import com.tu.ecommerce.model.viewModel.SystemParameterView;
import com.tu.ecommerce.util.ModelMapperUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@Transactional
public class SystemParameterService {

    private final SystemParameterRepository systemParameterRepository;

    private final ModelMapperUtil modelMapperUtil;

    public SystemParameterService(SystemParameterRepository systemParameterRepository,
                                  ModelMapperUtil modelMapperUtil) {

        this.systemParameterRepository = systemParameterRepository;
        this.modelMapperUtil = modelMapperUtil;
    }

    public Page<SystemParameterView> getAllSystemParameters(Pageable pageable) {
        Page<SystemParameter> systemParameters = this.systemParameterRepository.findAll(pageable);
        return this.modelMapperUtil.convertToPage(pageable, systemParameters, SystemParameterView.class);
    }

    public SystemParameterView getSystemParameter(Long id) {
        Optional<SystemParameter> systemParameter = this.systemParameterRepository.findById(id);
        return systemParameter.map(value -> this.modelMapperUtil.getModelMapper().map(value, SystemParameterView.class))
                .orElse(null);
    }

    public SystemParameterView getSystemParameterByCode(String code) {
        SystemParameter systemParameter = this.systemParameterRepository.findByCode(code);
        if (systemParameter == null) {
            return null;
        }

        return this.modelMapperUtil.getModelMapper().map(systemParameter, SystemParameterView.class);
    }
}
