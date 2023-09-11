package com.intern.newspaper_api.repository;

import com.intern.newspaper_api.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.intern.newspaper_api.entity.Article;

import java.util.Optional;

@Repository
public interface IArticleRepository extends JpaRepository<Article, Integer> {
    boolean existsBySlug(String slug);
    Optional<Article> findBySlug(String slug);
    Page<Article> findByCategory(Category category, Pageable pageable);
}
