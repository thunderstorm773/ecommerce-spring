package com.tu.ecommerce.model.viewModel;

import lombok.Data;

import java.util.Date;

@Data
public class CouponAdminView {

    private String discountCode;

    private Integer discountPercent;

    private Date validFrom;

    private Date validTo;

    private Date lastUpdated;

    private Boolean status;
}
