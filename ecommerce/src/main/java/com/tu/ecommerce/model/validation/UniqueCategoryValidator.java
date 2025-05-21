package com.tu.ecommerce.model.validation;

import com.tu.ecommerce.model.viewModel.ProductCategoryView;
import com.tu.ecommerce.service.ProductCategoryService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class UniqueCategoryValidator implements ConstraintValidator<UniqueCategory, String> {

    private final ProductCategoryService productCategoryService;

    public UniqueCategoryValidator(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @Override
    public boolean isValid(String categoryName, ConstraintValidatorContext constraintValidatorContext) {
        ProductCategoryView productCategory = this.productCategoryService.getProductCategoryByName(categoryName);
        return productCategory == null;
    }
}
