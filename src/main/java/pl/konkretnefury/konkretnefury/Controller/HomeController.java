package pl.konkretnefury.konkretnefury.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.konkretnefury.konkretnefury.service.CarOfferService;
import pl.konkretnefury.konkretnefury.service.StatisticsService;

@Controller
public class HomeController {

    private final CarOfferService carOfferService;
    private final StatisticsService statisticsService; // NOWA ZALEŻNOŚĆ

    public HomeController(CarOfferService carOfferService, StatisticsService statisticsService) {
        this.carOfferService = carOfferService;
        this.statisticsService = statisticsService;
    }

    @GetMapping("/")
    public String home(Model model) {
        // ZMIANA: Rejestracja wizyty
        statisticsService.recordVisit("HOME", null);

        model.addAttribute("featuredOffers", carOfferService.getFeaturedOffers());
        model.addAttribute("activePage", "home");
        return "home";
    }
}
