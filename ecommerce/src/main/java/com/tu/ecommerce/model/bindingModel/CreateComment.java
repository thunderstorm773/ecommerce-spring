package com.tu.ecommerce.model.bindingModel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateComment {

    @NotBlank(message = "Content is required")
    @Size(min = 20, message = "Content must be at least 20 characters long")
    private String content;

    @NotBlank(message = "Username is required")
    private String username;

    @NotNull(message = "Product id is required")
    private Long productId;
}
