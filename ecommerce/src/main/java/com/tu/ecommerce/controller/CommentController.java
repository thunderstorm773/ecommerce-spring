package com.tu.ecommerce.controller;

import com.tu.ecommerce.model.viewModel.CommentView;
import com.tu.ecommerce.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("product/{id}")
    public Page<CommentView> getProductsByCategoryId(@PathVariable("id") Long productId, Pageable pageable) {
        return this.commentService.getAllCommentsByProduct(productId, pageable);
    }

    @DeleteMapping("{id}")
    public CommentView deleteComment(@PathVariable("id") Long id) {
        return this.commentService.deleteComment(id);
    }
}
