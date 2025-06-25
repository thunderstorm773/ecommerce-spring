package com.tu.ecommerce.service;

import com.tu.ecommerce.dao.CommentRepository;
import com.tu.ecommerce.dao.ProductRepository;
import com.tu.ecommerce.entity.Comment;
import com.tu.ecommerce.entity.Product;
import com.tu.ecommerce.model.bindingModel.CreateComment;
import com.tu.ecommerce.model.viewModel.CommentView;
import com.tu.ecommerce.util.ModelMapperUtil;
import com.tu.ecommerce.util.UserUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;

    private final ProductRepository productRepository;

    private final ModelMapperUtil modelMapperUtil;

    public CommentService(CommentRepository commentRepository,
                          ProductRepository productRepository,
                          ModelMapperUtil modelMapperUtil) {

        this.commentRepository = commentRepository;
        this.productRepository = productRepository;
        this.modelMapperUtil = modelMapperUtil;
    }

    public Page<CommentView> getAllCommentsByProduct(Long productId, Pageable pageable) {
        Page<Comment> comments = this.commentRepository.findAllByProduct_IdOrderByIdDesc(productId, pageable);
        return this.modelMapperUtil.convertToPage(pageable, comments, CommentView.class);
    }

    public CommentView createComment(CreateComment createComment, Jwt jwt) {
        Product product = this.productRepository.findById(createComment.getProductId()).orElse(null);
        if (product == null) {
            throw new RuntimeException("Product does not exist");
        }

        Comment comment = this.modelMapperUtil.getModelMapper().map(createComment, Comment.class);
        comment.setProduct(product);
        comment.setUsername(UserUtil.getUsername(jwt));
        comment.setUserFullname(UserUtil.getUserFullname(jwt));

        this.commentRepository.save(comment);
        return this.modelMapperUtil.getModelMapper().map(comment, CommentView.class);
    }

    public CommentView deleteComment(Long id, Jwt jwt) {
        Comment comment = this.commentRepository.findById(id).orElse(null);
        if (comment == null) {
            throw new RuntimeException("Comment does not exists");
        }

        String loggedUsername = UserUtil.getUsername(jwt);
        List<String> loggedUserAuthorities = UserUtil.getUserAuthorities(jwt);
        if(!comment.getUsername().equals(loggedUsername) && !loggedUserAuthorities.contains("Admin")) {
            throw new RuntimeException("You do not have permission to delete this comment(author or admin)");
        }

        this.commentRepository.delete(comment);
        return this.modelMapperUtil.getModelMapper().map(comment, CommentView.class);
    }
}
