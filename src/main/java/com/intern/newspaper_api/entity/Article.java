package com.intern.newspaper_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Article implements Serializable {
    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    private String url;

    @Column(unique = true)
    private String slug;

    private String author;

    @Column(columnDefinition = "text")
    private String opening;

    private String thumbnail;

    @Column(columnDefinition = "text")
    private String detail;

    private Timestamp publishedDate;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;
}
