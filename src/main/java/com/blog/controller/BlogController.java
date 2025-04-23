package com.blog.controller;

import com.blog.dto.BlogRequest;
import com.blog.dto.BlogResponse;
import com.blog.dto.FilterRequest;
import com.blog.entity.Blog;
import com.blog.service.BlogService;
import com.blog.utils.ApiMessage;
import com.blog.utils.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class BlogController {

    @Autowired
    private BlogService blogService;

    @PostMapping(value = "/create_blog", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createBlog(@RequestBody BlogRequest blog) throws JsonProcessingException {

        ApiResponse apiResponse = new ApiResponse(HttpStatus.CREATED, true, blogService.createBlog(blog), "Blog created successfully.");
        return apiResponse.getResponse(apiResponse);
    }

    @PostMapping(value = "get_blog_list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getBlogList(@RequestBody FilterRequest filterRequest) throws JsonProcessingException {
        Page<BlogResponse> blogResponses = blogService.filterBlogs(filterRequest);
        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK, true, blogResponses, ApiMessage.Api_Message);
        return apiResponse.getResponse(apiResponse);
    }



    @GetMapping(value = "/get_blog_by_id", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getBlogById(@RequestParam("id") Long id) throws JsonProcessingException {
        Blog blog = blogService.getBlogById(id).orElse(null);
        if (blog == null) {
            ApiResponse apiResponse = new ApiResponse(HttpStatus.NOT_FOUND, false, null, "Blog not found or deleted.");
            return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
        }
        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK, true, blog, "Blog fetched successfully.");
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }



    @PutMapping(value = "/update_blog/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateBlog(@PathVariable Long id, @RequestBody BlogRequest blogRequest) throws JsonProcessingException {
        Blog updatedBlog = blogService.updateBlog(id, blogRequest);
        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK, true, updatedBlog, "Blog updated successfully.");
        return apiResponse.getResponse(apiResponse);
    }

    @DeleteMapping(value = "/delete_blog/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteBlog(@PathVariable Long id) throws JsonProcessingException {
        blogService.deleteBlog(id);
        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK, true, null, "Blog deleted successfully (soft delete).");
        return apiResponse.getResponse(apiResponse);
    }
}
