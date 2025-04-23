package com.blog.dto;

import com.blog.entity.BlogStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BlogResponse {
    private Long blogId;
    private String title;
    private String description;
    private Boolean status;
    private BlogStatus blogStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
