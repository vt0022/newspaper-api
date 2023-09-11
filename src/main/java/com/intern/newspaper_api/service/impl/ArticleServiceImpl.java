package com.intern.newspaper_api.service.impl;

import com.intern.newspaper_api.entity.Article;
import com.intern.newspaper_api.entity.Category;
import com.intern.newspaper_api.repository.IArticleRepository;
import com.intern.newspaper_api.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ArticleServiceImpl implements IArticleService {
    private final IArticleRepository articleRepository;

    @Autowired
    public ArticleServiceImpl(IArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public Page<Article> findByCategory(Category category, Pageable pageable) {
        return articleRepository.findByCategory(category, pageable);
    }

    @Override
    public Page<Article> findAll(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }

    @Override
    public <S extends Article> S save(S entity) {
        return articleRepository.save(entity);
    }

    @Override
    public boolean existsBySlug(String slug) {
        return articleRepository.existsBySlug(slug);
    }

    @Override
    public Optional<Article> findBySlug(String slug) {
        return articleRepository.findBySlug(slug);
    }
}
