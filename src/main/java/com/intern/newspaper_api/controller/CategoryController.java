package com.intern.newspaper_api.controller;

import com.intern.newspaper_api.entity.Category;
import com.intern.newspaper_api.model.CategoryModel;
import com.intern.newspaper_api.model.ResponseModel;
import com.intern.newspaper_api.service.ICategoryService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/newspaper/category")
@CrossOrigin(origins = "${CORS_URL}", maxAge = 3600)
public class CategoryController {
    private final ICategoryService categoryService;
    private final ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);


    @Autowired
    public CategoryController(ICategoryService categoryService, ModelMapper modelMapper) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("")
    public ResponseEntity<?> getAllCategories() {
        ResponseModel responseModel = new ResponseModel();
        try {
            List<Category> categories = categoryService.findAll();
            responseModel.setStatus("success");
            responseModel.setMessage("retrieve all categories successfully");
            responseModel.setData(modelMapper.map(categories, new TypeToken<List<CategoryModel>>() {}.getType()));
        } catch (Exception e) {
            responseModel.setStatus("error");
            responseModel.setMessage(e.getMessage());
            responseModel.setData(null);
            logger.error(e.getMessage());
        }
        return ResponseEntity.ok(responseModel);
    }
}
