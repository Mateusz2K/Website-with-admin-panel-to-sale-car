package pl.konkretnefury.konkretnefury.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.konkretnefury.konkretnefury.service.CarOfferService;

@Controller
public class HomeController {

    private final CarOfferService carOfferService;

    public HomeController(CarOfferService carOfferService) {
        this.carOfferService = carOfferService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("featuredOffers", carOfferService.getFeaturedOffers());
        model.addAttribute("activePage", "home");
        return "home";
    }
}
