package com.tu.ecommerce.model.bindingModel;

import com.tu.ecommerce.model.validation.UniqueCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateCategory {

    @NotBlank(message = "Category Name is required")
    @Size(min = 2, message = "Category Name must be at least 2 characters long")
    @UniqueCategory
    private String categoryName;
}
