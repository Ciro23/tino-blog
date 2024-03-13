package it.tino.blog.article;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleRepository articleRepository;

    @GetMapping("/articles/{id}")
    public String showFullArticle(@PathVariable UUID id, Model model) {
        Article article = articleRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Article not found"));
        model.addAttribute("article", article);

        return "article/article";
    }
}
