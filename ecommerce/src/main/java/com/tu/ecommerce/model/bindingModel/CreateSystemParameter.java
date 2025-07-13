package com.tu.ecommerce.model.bindingModel;

import com.tu.ecommerce.model.validation.UniqueSystemParameter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateSystemParameter {

    @NotBlank(message = "Code is required")
    @Size(min = 3, message = "Code must be at least 3 characters long")
    @UniqueSystemParameter
    private String code;

    @NotBlank(message = "Value is required")
    private String value;

    @NotBlank(message = "Description is required")
    private String description;
}
