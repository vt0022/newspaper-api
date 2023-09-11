package com.intern.newspaper_api.repository;

import com.intern.newspaper_api.entity.Article;
import com.intern.newspaper_api.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICategoryRepositoty extends JpaRepository<Category, Integer> {
    boolean existsBySlug(String slug);
    Optional<Category> findBySlug(String slug);
}
