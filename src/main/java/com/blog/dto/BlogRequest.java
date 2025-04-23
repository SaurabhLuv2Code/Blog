package com.blog.dto;

import com.blog.entity.BlogStatus;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Data
public class BlogRequest {
    private Long blogId;

    private String title;

    private String description;

    private Boolean status;

    private BlogStatus blogStatus;
}
