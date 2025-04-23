package com.blog.service;


import com.blog.dto.BlogRequest;
import com.blog.dto.BlogResponse;
import com.blog.dto.FilterRequest;
import com.blog.entity.Blog;
import com.blog.entity.BlogStatus;
import com.blog.repository.BlogRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;



import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class BlogService {


    private final BlogRepository blogRepository;

    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    //Create Blog
    public BlogResponse createBlog(BlogRequest blogRequest) {
        // Create a new Blog entity from the DTO
        Blog blog = Blog.builder()
                .title(blogRequest.getTitle())
                .description(blogRequest.getDescription())
                .status(true)
                .blogStatus(blogRequest.getBlogStatus() != null ? blogRequest.getBlogStatus() : BlogStatus.DRAFTED)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // Save the Blog entity to the database
        Blog savedBlog = blogRepository.save(blog);

        // Return a BlogResponse with only status and createdAt
        return BlogResponse.builder()
                .status(savedBlog.getStatus())
                .blogStatus(savedBlog.getBlogStatus())
                .createdAt(savedBlog.getCreatedAt())
                .build();
    }



    //Get All Blog
    public Page<BlogResponse> filterBlogs(FilterRequest filterRequest) {

        Pageable pageable = filterRequest.getPageNumber() == -1 && filterRequest.getPageSize() == -1
                ? Pageable.unpaged()  // Fixed to use Pageable
                : PageRequest.of(filterRequest.getPageNumber(), filterRequest.getPageSize(),
                Sort.by(Sort.Direction.fromString(filterRequest.getSortDirection()), filterRequest.getFilter()));

        return blogRepository.findAllBlog(filterRequest.getSearch(),filterRequest.getBlogStatus(), pageable);
    }


    //Get Blog By Id
    public Optional<Blog> getBlogById(Long id) {
        return blogRepository.findById(id)
                .filter(Blog::getStatus); // return Optional.empty() if status is false
    }

    public Blog updateBlog(Long id, BlogRequest request) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog not found with id: " + id));

        if (!Boolean.TRUE.equals(blog.getStatus())) {
            throw new RuntimeException("Cannot update a deleted/inactive blog.");
        }

        blog.setTitle(request.getTitle());
        blog.setDescription(request.getDescription());
        if (request.getBlogStatus() != null) {
            blog.setBlogStatus(request.getBlogStatus());
        }
        blog.setUpdatedAt(LocalDateTime.now());

        return blogRepository.save(blog);
    }

    public void deleteBlog(Long id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog not found with id: " + id));

        blog.setStatus(false);
        blog.setUpdatedAt(LocalDateTime.now());

        blogRepository.save(blog);
    }

}
