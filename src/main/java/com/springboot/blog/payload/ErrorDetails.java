package com.springboot.blog.payload;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class ErrorDetails {

    private Date time;
    private String message;
    private String details;
}
