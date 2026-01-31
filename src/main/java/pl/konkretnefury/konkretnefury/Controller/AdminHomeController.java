package pl.konkretnefury.konkretnefury.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.konkretnefury.konkretnefury.service.BlogService;
import pl.konkretnefury.konkretnefury.service.CarOfferService;
import pl.konkretnefury.konkretnefury.service.StatisticsService;

@Controller
@RequestMapping("/admin")
public class AdminHomeController {

    private final CarOfferService carOfferService;
    private final BlogService blogService;
    private final StatisticsService statisticsService;

    public AdminHomeController(CarOfferService carOfferService, BlogService blogService, StatisticsService statisticsService) {
        this.carOfferService = carOfferService;
        this.blogService = blogService;
        this.statisticsService = statisticsService;
    }

    @GetMapping
    public String home(Model model) {
        model.addAttribute("activeOfferCount", carOfferService.countActiveOffers());
        model.addAttribute("recentOffers", carOfferService.findLast5Offers());
        model.addAttribute("recentBlogPosts", blogService.findLast5Posts());
        model.addAttribute("totalVisits", statisticsService.getTotalVisits());
        model.addAttribute("popularOffers", statisticsService.getMostPopularOffers());
        
        model.addAttribute("chartData", statisticsService.getVisitsLast7Days());
        // ZMIANA: Przekazanie danych dla wykresu kołowego
        model.addAttribute("pieChartData", statisticsService.getVisitsByPageType());

        return "admin/home";
    }
}
