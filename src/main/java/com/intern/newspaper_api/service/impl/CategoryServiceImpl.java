package com.intern.newspaper_api.service.impl;

import com.intern.newspaper_api.entity.Category;
import com.intern.newspaper_api.repository.ICategoryRepositoty;
import com.intern.newspaper_api.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements ICategoryService {
    private final ICategoryRepositoty categoryRepositoty;

    @Autowired
    public CategoryServiceImpl(ICategoryRepositoty categoryRepositoty) {
        this.categoryRepositoty = categoryRepositoty;
    }

    @Override
    public boolean existsBySlug(String slug) {
        return categoryRepositoty.existsBySlug(slug);
    }

    @Override
    public Optional<Category> findBySlug(String slug) {
        return categoryRepositoty.findBySlug(slug);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepositoty.findAll();
    }

    @Override
    public <S extends Category> S save(S entity) {
        return categoryRepositoty.save(entity);
    }
}
