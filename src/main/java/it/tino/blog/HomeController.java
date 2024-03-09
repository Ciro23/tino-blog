package it.tino.blog;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {

    @GetMapping({"/", "home"})
    public String home() {
        return "home";
    }

    @GetMapping("login")
    public String login(Principal principal) {
        if (principal == null) {
            return "login";
        }

        return "redirect:/admin/articles";
    }
}
