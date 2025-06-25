package com.tu.ecommerce.controller;

import com.tu.ecommerce.model.bindingModel.CreateComment;
import com.tu.ecommerce.model.viewModel.CommentView;
import com.tu.ecommerce.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("product/{id}")
    public Page<CommentView> getCommentsByProduct(@PathVariable("id") Long productId, Pageable pageable) {
        return this.commentService.getAllCommentsByProduct(productId, pageable);
    }

    @PostMapping("add")
    public CommentView createComment(@Valid @RequestBody CreateComment createComment,
                                     @AuthenticationPrincipal Jwt jwt) {
        return this.commentService.createComment(createComment, jwt);
    }

    @DeleteMapping("delete/{id}")
    public CommentView deleteComment(@PathVariable("id") Long id,
                                     @AuthenticationPrincipal Jwt jwt) {
        return this.commentService.deleteComment(id, jwt);
    }
}
