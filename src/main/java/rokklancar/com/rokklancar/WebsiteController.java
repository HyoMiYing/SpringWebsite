package rokklancar.com.rokklancar;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebsiteController {

    @GetMapping("/")
    public String homepage() {
        return "index.html";
    }

    @GetMapping("/ferdydurke")
    public String ferdydurke() {
        return "audiobook_player.html";
    }
}
