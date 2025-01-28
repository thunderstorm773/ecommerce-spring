package com.tu.ecommerce.model.viewModel;

import com.tu.ecommerce.entity.ProductCategory;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "productCategoryView", types = {ProductCategory.class})
public interface ProductCategoryView {

    String getCategoryName();
}
