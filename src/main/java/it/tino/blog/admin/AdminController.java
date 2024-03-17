package it.tino.blog.admin;

import it.tino.blog.article.Article;
import it.tino.blog.article.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final ArticleRepository articleRepository;

    @GetMapping("/admin/articles")
    public String articlesManager(Model model) {
        Set<Article> articles = new TreeSet<>(articleRepository.findAll());
        model.addAttribute("articles", articles);
        return "/article/manager";
    }

    @GetMapping("/admin/articles/new")
    public String showNewArticleForm(Model model) {
        model.addAttribute("article", new Article());
        return "/article/new";
    }

    @GetMapping("/articles/edit/{id}")
    public String showEditArticleForm(@PathVariable UUID id, Model model) {
        Article article = articleRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid article Id:" + id));
        model.addAttribute("article", article);
        return "/article/new";
    }

    @PostMapping("/admin/articles/save")
    public String saveNewArticle(@ModelAttribute Article article) {
        articleRepository.save(article);
        return "redirect:/admin/articles";
    }

    @GetMapping("articles/delete/{id}")
    public String deleteArticle(@PathVariable UUID id) {
        articleRepository.deleteById(id);
        return "redirect:/admin/articles";
    }
}
