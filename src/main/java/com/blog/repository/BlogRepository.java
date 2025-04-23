package com.blog.repository;

import com.blog.dto.BlogResponse;
import com.blog.entity.Blog;
import com.blog.entity.BlogStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface BlogRepository extends JpaRepository<Blog, Long>{

    @Query("SELECT new com.blog.dto.BlogResponse(b.blogId, b.title, b.description, b.status, b.blogStatus, b.createdAt, b.updatedAt) " +
            "FROM Blog b " +
            "WHERE b.status = true " +
            "AND (LOWER(b.title) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(b.description) LIKE LOWER(CONCAT('%', :search, '%'))) " +
            "AND (:blogStatus IS NULL OR b.blogStatus = :blogStatus) " +
            "ORDER BY b.createdAt DESC")
    Page<BlogResponse> findAllBlog(@Param("search") String search,
                                   @Param("blogStatus") BlogStatus blogStatus,
                                   Pageable pageable);

}
