package pl.konkretnefury.konkretnefury.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.konkretnefury.konkretnefury.service.BlogService;
import pl.konkretnefury.konkretnefury.service.CarOfferService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final CarOfferService carOfferService;
    private final BlogService blogService;

    // ZMIANA: Konstruktor prosi TYLKO o serwisy, których potrzebujemy.
    // Usunięto zależność od InMemoryUserDetailsManager.
    public AdminController(CarOfferService carOfferService, BlogService blogService) {
        this.carOfferService = carOfferService;
        this.blogService = blogService;
    }

    @GetMapping
    public String adminDashboard(Model model) {
        // Ta metoda teraz poprawnie doda wszystkie potrzebne dane do modelu.
        model.addAttribute("activeOfferCount", carOfferService.countActiveOffers());
        model.addAttribute("recentOffers", carOfferService.findLast5Offers());
        model.addAttribute("recentBlogPosts", blogService.findLast5Posts());

        return "admin/home";
    }
}
