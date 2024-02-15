package com.springboot.blog.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.Set;
import lombok.Data;

@Data
public class PostDto {

    private long id;

    // title should not be null or empty
    // title should have at least 2 characters
    @NotEmpty(message = "Title cannot be empty")
    @Size(min = 2, message = "Title must have at least 2 characters")
    private String title;

    // post description should not be null or empty
    // post description should have at least 10 characters
    @NotEmpty(message = "Description cannot be empty")
    @Size(min = 10, message = "Description must have at least 10 characters")
    private String description;

    // post content should not be null or empty
    @NotEmpty(message = "Content cannot be empty")
    private String content;
    private Set<CommentDto> comments;

    private Long categoryId;


}
