package com.tu.ecommerce.model.viewModel;

import lombok.Data;

import java.util.Date;

@Data
public class CouponView {

    private Long id;

    private String discountCode;

    private Integer discountPercent;

    private Date validFrom;

    private Date validTo;

    private Boolean status;
}
