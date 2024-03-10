package it.tino.blog.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminController {

    @GetMapping("admin/articles")
    public String articlesManager() {
        return "/article/manager";
    }

    @GetMapping("admin/articles/new")
    public String showNewArticleForm() {
        return "/article/new";
    }

    @PostMapping("admin/articles/save")
    public String saveNewArticle() {
        return "redirect:/admin/articles";
    }
}
