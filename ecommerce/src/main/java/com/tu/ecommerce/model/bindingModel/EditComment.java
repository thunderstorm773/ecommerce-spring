package com.tu.ecommerce.model.bindingModel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EditComment {

    @NotBlank(message = "Content is required")
    @Size(min = 20, message = "Content must be at least 20 characters long")
    private String content;
}
