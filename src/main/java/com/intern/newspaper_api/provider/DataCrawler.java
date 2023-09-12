package com.intern.newspaper_api.provider;

import com.intern.newspaper_api.constant.Constants;
import com.intern.newspaper_api.entity.Article;
import com.intern.newspaper_api.entity.Category;
import com.intern.newspaper_api.service.IArticleService;
import com.intern.newspaper_api.service.ICategoryService;
import com.intern.newspaper_api.utility.TimestampConversion;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class DataCrawler {
    private final IArticleService articleService;
    private final ICategoryService categoryService;
    private static final Logger logger = LoggerFactory.getLogger(DataCrawler.class);


    @Autowired
    public DataCrawler(IArticleService articleService, ICategoryService categoryService) {
        this.articleService = articleService;
        this.categoryService = categoryService;
    }

    public void categoryCrawl() {
        try {
            Document doc = Jsoup.connect(Constants.ROOT_URL).get();

            Element element = doc.getElementsByClass("menu-nav").first();

            for (Element e : element.select("a")) {
                Category category = new Category();
                if (!e.attr("title").equals("Trang chủ")) {
                    String url = e.attr("href");
                    String slug = url.replaceAll("^/|\\.htm$", "");
                    if (!categoryService.existsBySlug(slug)) {
                        category.setName(e.attr("title"));
                        category.setUrl(url);
                        category.setSlug(slug);
                        categoryService.save(category);
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.toString());
        }
    }

    public void articleDetailCrawl(Article article) {
        try {
            // Crawl detail of article
            Document docDetail = Jsoup.connect(Constants.ROOT_URL + article.getUrl()).get();
            // Some page is magazine
            Element eleArticle = docDetail.getElementsByClass("detail__cmain").first();
            if (docDetail.getElementsByClass("detail__magazine").first() == null) {
                article.setAuthor(eleArticle.getElementsByClass("author-info").first().child(0).attr("title"));
                // Exclude related articles
                Element detailElement = docDetail.getElementsByClass("detail-cmain").first();
                // Exclude a specific div by its class or ID
                Elements excludedElements = detailElement.select(".kbwscwl-relatedbox");
                if (excludedElements != null) {
                    excludedElements.remove(); // Remove the div
                }
                String modifiedHtml = detailElement.outerHtml();
                article.setDetail(modifiedHtml);

                String publishedDate = new TimestampConversion().convert(eleArticle.getElementsByClass("detail-time").first().text());
                article.setPublishedDate(Timestamp.valueOf(publishedDate));
                // Category
                String categorySlug = eleArticle.getElementsByClass("detail-cate")
                        .first().child(0).attr("href")
                        .replaceAll("^/|\\.htm$", "");
                ;
                Category category = categoryService.findBySlug(categorySlug).orElse(null);
//                    if (!category.isPresent()) {
//                        Category newCategory = new Category();
//                        newCategory.setName(categoryName);
//                        String[] names = categoryName.split(" ");
//                        newCategory.setUrl(ca);
//                    } // New category
                article.setCategory(category);
            }
            System.out.println("Crawl categories at " + new Date());
        } catch (Exception e) {
            logger.error(e.toString());
        }
    }
    @Scheduled(cron = "0 */30 * * * ?")
    public void articleCrawl() {
        try {
            // Add to a list to set order
            List<Article> articles = new ArrayList<>();
            // Connect to Jsoup
            Document doc = Jsoup.connect(Constants.LASTEST_URL).get();
            // Find news wrapped in boxes in the main section
            Elements elements = doc.getElementsByClass("list__listing-main")// section__middle-news section__mn-main
                    .first()
                    .getElementsByClass("box-category-item");
            // Iterate each element
            for (Element e : elements) {
                Article article = new Article();
                // Find slug
                String url = e.child(1).attr("href");
                String slug = url.replaceAll("^/|\\.htm$", "");
                // Check existing article
                if (!articleService.existsBySlug(slug)) {
                    article.setTitle(e.child(1).attr("title"));
                    article.setUrl(url);
                    article.setSlug(slug);
                    // Static image or gif
                    String thumbnail = e.child(1).child(0).attr("src") != null
                            ? e.child(1).child(0).attr("src") : e.child(1).child(0).attr("poster");
                    article.setThumbnail(thumbnail);
                    article.setOpening(e.getElementsByClass("box-category-sapo").text()); // in p tag
                    // Crawl detail of article
                    articleDetailCrawl(article);
                    // Add to list
                    articles.add(article);
                }
            }
            // Reverse the list to make the newest article to the last
            Collections.reverse(articles);
            // Add to database
            for (Article a : articles) {
                articleService.save(a);
            }
            System.out.println("Crawl articles at " + new Date());
        } catch (Exception e) {
            logger.error(e.toString());
        }
    }
}
