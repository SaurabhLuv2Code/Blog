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
    private int pageNumber; // Page number to fetch
    private int pageSize;   // Size of each page
    private String sortDirection; // Sorting direction (ASC/DESC)
    private String filter;
}
