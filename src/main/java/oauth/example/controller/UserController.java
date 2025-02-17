package oauth.example.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class UserController {
    @GetMapping("/user")
    public String userProfile(
            @AuthenticationPrincipal OAuth2User principal,
            Model model
    ) {
        model.addAttribute("name", principal.getAttribute("name"));
        model.addAttribute("email", principal.getAttribute("email"));
        return "user";
    }
    @GetMapping("/login")
    public String login() {
        return "redirect:/oauth2/authorization/github";
    }
    @GetMapping("/")
    public String home() {
        return "index";
    }
}
