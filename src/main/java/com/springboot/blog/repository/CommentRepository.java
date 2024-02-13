package com.springboot.blog.repository;

import com.springboot.blog.entity.Comment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Object> findByIdAndPostId(Long commentId, Long postId);
    List<Comment> findByPostId(Long postId);
}
