package pl.konkretnefury.konkretnefury.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.konkretnefury.konkretnefury.service.BlogService;
import pl.konkretnefury.konkretnefury.service.CarOfferService;

@Controller
@RequestMapping("/admin")
public class HomeController {

    private final CarOfferService carOfferService;
    private final BlogService blogService;

    public HomeController(CarOfferService carOfferService, BlogService blogService) {
        this.carOfferService = carOfferService;
        this.blogService = blogService;
    }

    @GetMapping("/")
    public String home(Model model) {
        // Pobieramy wszystkie promowane oferty, aby wyświetlić je na stronie głównej
        model.addAttribute("featuredOffers", carOfferService.getFeaturedOffers());
        model.addAttribute("blogPosts", blogService.findLast5Posts());
        return "admin/home";
    }
}
