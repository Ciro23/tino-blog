package it.tino.blog.admin;

import it.tino.blog.article.Article;
import it.tino.blog.article.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final ArticleRepository articleRepository;

    @GetMapping("admin/articles")
    public String articlesManager(Model model) {
        model.addAttribute("articles", articleRepository.findAll());
        return "/article/manager";
    }

    @GetMapping("admin/articles/new")
    public String showNewArticleForm(Model model) {
        model.addAttribute("article", new Article());
        return "/article/new";
    }

    @PostMapping("admin/articles/save")
    public String saveNewArticle(@ModelAttribute Article article) {
        articleRepository.save(article);
        return "redirect:/admin/articles";
    }
}
