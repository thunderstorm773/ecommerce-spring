package com.tu.ecommerce.model.validation;

import com.tu.ecommerce.model.viewModel.ProductCategoryView;
import com.tu.ecommerce.service.ProductCategoryService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class CategoryValidator implements ConstraintValidator<Category, Long> {

    private final ProductCategoryService productCategoryService;

    public CategoryValidator(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @Override
    public boolean isValid(Long categoryId, ConstraintValidatorContext constraintValidatorContext) {
        ProductCategoryView productCategory = this.productCategoryService.getProductCategoryById(categoryId);
        return productCategory != null;
    }
}
