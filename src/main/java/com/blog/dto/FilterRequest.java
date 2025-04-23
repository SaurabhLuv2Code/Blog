package com.blog.dto;


import com.blog.entity.BlogStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FilterRequest {

    private String search;
    private BlogStatus blogStatus;
    private int pageNumber = 0;
    private int pageSize = 20;
    private String sortDirection = "DESC";
    private String filter = "createdAt";
}
