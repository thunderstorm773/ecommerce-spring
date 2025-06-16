package com.tu.ecommerce.service;

import com.tu.ecommerce.dao.CommentRepository;
import com.tu.ecommerce.entity.Comment;
import com.tu.ecommerce.model.viewModel.CommentView;
import com.tu.ecommerce.util.ModelMapperUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    private final ModelMapperUtil modelMapperUtil;

    public CommentService(CommentRepository commentRepository,
                          ModelMapperUtil modelMapperUtil) {

        this.commentRepository = commentRepository;
        this.modelMapperUtil = modelMapperUtil;
    }

    public Page<CommentView> getAllCommentsByProduct(Long productId, Pageable pageable) {
        Page<Comment> comments = this.commentRepository.findAllByProduct_Id(productId, pageable);
        return this.modelMapperUtil.convertToPage(pageable, comments, CommentView.class);
    }

    public CommentView deleteComment(Long id) {
        Comment comment = this.commentRepository.findById(id).orElse(null);
        if (comment == null) {
            throw new RuntimeException("Comment does not exists");
        }

        this.commentRepository.delete(comment);
        return this.modelMapperUtil.getModelMapper().map(comment, CommentView.class);
    }
}
