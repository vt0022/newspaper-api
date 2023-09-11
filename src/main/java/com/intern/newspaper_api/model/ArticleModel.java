package com.intern.newspaper_api.model;

import com.intern.newspaper_api.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleModel implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String title;
    private String url;
    private String slug;
    private String author;
    private String opening;
    private String thumbnail;
    private String detail;
    private Timestamp publishedDate;
    private Category category;
}
