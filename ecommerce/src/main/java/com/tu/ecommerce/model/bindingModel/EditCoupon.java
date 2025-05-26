package com.tu.ecommerce.model.bindingModel;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Date;

@Data
public class EditCoupon {

    @NotBlank(message = "Discount code is required")
    @Size(min = 4, message = "Discount code must be at least 4 characters long")
    private String discountCode;

    @NotNull(message = "Discount percent is required")
    @Min(value = 1, message = "Discount percent must be at least 1")
    @Max(value = 30, message = "Discount percent must be at most 30")
    private Integer discountPercent;

    @NotNull(message = "Valid from is required")
    @FutureOrPresent(message = "Valid from must be a date in the present or future")
    private Date validFrom;

    @NotNull(message = "Valid to is required")
    @Future(message = "Valid to must be a date in the future")
    private Date validTo;
}
