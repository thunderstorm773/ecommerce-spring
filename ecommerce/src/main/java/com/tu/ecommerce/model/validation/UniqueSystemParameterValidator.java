package com.tu.ecommerce.model.validation;

import com.tu.ecommerce.model.viewModel.SystemParameterView;
import com.tu.ecommerce.service.SystemParameterService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class UniqueSystemParameterValidator implements ConstraintValidator<UniqueSystemParameter, String> {

    private final SystemParameterService systemParameterService;

    public UniqueSystemParameterValidator(SystemParameterService systemParameterService) {
        this.systemParameterService = systemParameterService;
    }

    @Override
    public boolean isValid(String code, ConstraintValidatorContext constraintValidatorContext) {
        SystemParameterView systemParameter = this.systemParameterService.getSystemParameterByCode(code);
        return systemParameter == null;
    }
}
