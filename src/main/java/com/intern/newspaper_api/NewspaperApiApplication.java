package com.intern.newspaper_api;

import com.intern.newspaper_api.provider.DataCrawler;
import com.intern.newspaper_api.service.IArticleService;
import com.intern.newspaper_api.service.ICategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NewspaperApiApplication implements CommandLineRunner {

	private final ICategoryService categoryService;
	private final IArticleService articleService;

	@Autowired
	public NewspaperApiApplication(ICategoryService categoryService, IArticleService articleService) {
		this.categoryService = categoryService;
		this.articleService = articleService;
	}

	public static void main(String[] args) {
		SpringApplication.run(NewspaperApiApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		new DataCrawler(articleService, categoryService).categoryCrawl();
		new DataCrawler(articleService, categoryService).articleCrawl();
	}
}
