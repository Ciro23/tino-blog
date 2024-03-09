package it.tino.blog.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/admin")
public class AdminController {

    @GetMapping("articles")
    public String articlesManager() {
        return "article/manager";
    }

    @GetMapping("articles/new")
    public String showNewArticleForm() {
        return "article/new";
    }
}
