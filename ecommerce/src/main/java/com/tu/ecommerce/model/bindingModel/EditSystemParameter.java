package com.tu.ecommerce.model.bindingModel;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EditSystemParameter {

    @NotBlank(message = "Value is required")
    private String value;

    @NotBlank(message = "Description is required")
    private String description;
}
