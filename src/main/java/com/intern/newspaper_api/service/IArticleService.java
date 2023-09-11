package com.intern.newspaper_api.service;

import com.intern.newspaper_api.entity.Article;
import com.intern.newspaper_api.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IArticleService {

    Page<Article> findByCategory(Category category, Pageable pageable);

    Page<Article> findAll(Pageable pageable);

    <S extends Article> S save(S entity);

    boolean existsBySlug(String slug);

    Optional<Article> findBySlug(String slug);
}
