package com.tu.ecommerce.model.viewModel;

import lombok.Data;

import java.util.Date;

@Data
public class CommentView {

    private Long id;

    private String content;

    private String username;

    private Date dateCreated;
}
