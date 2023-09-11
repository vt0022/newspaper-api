package com.intern.newspaper_api.controller;

import com.intern.newspaper_api.entity.Article;
import com.intern.newspaper_api.entity.Category;
import com.intern.newspaper_api.model.ArticleModel;
import com.intern.newspaper_api.model.ResponseModel;
import com.intern.newspaper_api.service.IArticleService;
import com.intern.newspaper_api.service.ICategoryService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/newspaper/article")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class ArticleController {
    private final IArticleService articleService;
    private final ICategoryService categoryService;
    private final ModelMapper modelMapper;
    Sort newerSort = Sort.by(Sort.Direction.DESC, "id");
    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    @Autowired
    public ArticleController(IArticleService articleService, ICategoryService categoryService, ModelMapper modelMapper) {
        this.articleService = articleService;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllArticles(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size) {
        // Paging with page i and size n
        Pageable pageable = PageRequest.of(page, size, newerSort);
        ResponseModel responseModel = new ResponseModel();
        try {
            Page<Article> articles = articleService.findAll(pageable);
            // Filter out null category
            List<Article> foundArticles = articles.getContent().stream()
                    .filter(article -> article.getCategory() != null)
                    .collect(Collectors.toList());
            responseModel.setStatus("success");
            responseModel.setMessage("retrieve all articles successfully");
            responseModel.setData(modelMapper.map(foundArticles, new TypeToken<List<ArticleModel>>(){}.getType()));
        } catch (Exception e) {
            responseModel.setStatus("error");
            responseModel.setMessage(e.toString());
            responseModel.setData(null);
            logger.error(e.toString());
        }
        return ResponseEntity.ok(responseModel);
    }

    @GetMapping("/{slug}")
    public ResponseEntity<?> getArticleDetail(@PathVariable String slug) {
        ResponseModel responseModel = new ResponseModel();
        try {
            Article article = articleService.findBySlug(slug).get();
            responseModel.setStatus("success");
            responseModel.setMessage("get detail of an article successfully");
            responseModel.setData(modelMapper.map(article, ArticleModel.class));
        } catch (Exception e) {
            responseModel.setStatus("error");
            responseModel.setMessage(e.getMessage());
            responseModel.setData(null);
            logger.error(e.toString());// or e.getMessage()
        }
        return ResponseEntity.ok(responseModel);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<?> getArticlesByCategory(@PathVariable String category,
                                                   @RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size) {
        // Paging with page i and size n
        Pageable pageable = PageRequest.of(page, size, newerSort);
        ResponseModel responseModel = new ResponseModel();
        try {
            Category thisCategory = categoryService.findBySlug(category).get();

            Page<Article> articles = articleService.findByCategory(thisCategory, pageable);
            // Filter out null category
            List<Article> foundArticles = articles.getContent().stream()
                    .filter(article -> article.getCategory() != null)
                    .collect(Collectors.toList());
            responseModel.setStatus("success");
            responseModel.setMessage("retrieve articles of this category successfully");
            responseModel.setData(modelMapper.map(foundArticles, new TypeToken<List<ArticleModel>>(){}.getType()));
        } catch (Exception e) {
            responseModel.setStatus("error");
            responseModel.setMessage(e.getMessage());
            responseModel.setData(null);
            logger.error(e.toString());// or e.getMessage()
        }
        return ResponseEntity.ok(responseModel);
    }
}
