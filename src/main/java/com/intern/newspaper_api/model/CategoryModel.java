package com.intern.newspaper_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.intern.newspaper_api.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryModel implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private String url;
    private String slug;
    @JsonIgnore
    private Set<Article> articles = new HashSet<>();
}
