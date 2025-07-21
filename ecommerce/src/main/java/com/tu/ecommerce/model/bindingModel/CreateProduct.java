package com.tu.ecommerce.model.bindingModel;

import com.tu.ecommerce.model.validation.Category;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateProduct {

    @NotBlank(message = "Name is required")
    @Size(min = 4, message = "Name must be at least 4 characters long")
    private String name;

    @NotBlank(message = "Description is required")
    @Size(min = 10, message = "Description must be at least 10 characters long")
    private String description;

    @NotNull(message = "Unit price is required")
    @DecimalMin(value = "0.5", message = "Unit price must be at least 0.5")
    private BigDecimal unitPrice;

    @NotBlank(message = "Image URL is required")
    private String imageUrl;

    @NotNull(message = "Units in stock is required")
    @Min(value = 1, message = "Units in stock must be at least 1")
    private Integer unitsInStock;

    @NotNull(message = "Category id is required")
    @Category
    private Long categoryId;

    @NotNull(message = "Is active is required")
    private Boolean isActive;
}
