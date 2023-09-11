package com.intern.newspaper_api.service;

import com.intern.newspaper_api.entity.Category;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {

    boolean existsBySlug(String slug);

    Optional<Category> findBySlug(String slug);

    List<Category> findAll();

    <S extends Category> S save(S entity);
}
