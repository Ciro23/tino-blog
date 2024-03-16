package it.tino.blog;

import it.tino.blog.article.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ArticleRepository articleRepository;

    @GetMapping({"/", "home"})
    public String home(Model model) {
        model.addAttribute("articles", articleRepository.findAll());
        return "home";
    }

    @GetMapping("/login")
    public String login(Principal principal) {
        if (principal == null) {
            return "login";
        }

        return "redirect:/admin/articles";
    }
}
