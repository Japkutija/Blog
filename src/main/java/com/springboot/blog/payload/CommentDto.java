package com.springboot.blog.payload;

import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentDto {

    private Long id;

    // name should not be null or empty
    @NotEmpty(message = "Name cannot be empty")
    private String name;

    // email should not be null or empty
    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email should be valid")
    private String email;

    // body should not be null or empty
    // body should have at least 10 characters
    @NotEmpty(message = "Body cannot be empty")
    @Size(min = 10, message = "Body must have at least 10 characters")
    private String body;
}
