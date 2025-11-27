package pl.konkretnefury.konkretnefury.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.konkretnefury.konkretnefury.modele.CarOption;
import pl.konkretnefury.konkretnefury.modele.TypeOfCarOptions;
import pl.konkretnefury.konkretnefury.service.CarOptionService;

import java.util.UUID;

@Controller
@RequestMapping("/admin/options")
public class AdminCarOptionController {

    private final CarOptionService carOptionService;

    public AdminCarOptionController(CarOptionService carOptionService) {
        this.carOptionService = carOptionService;
    }

    @GetMapping
    public String listOptions(Model model) {
        model.addAttribute("options", carOptionService.findAll());
        model.addAttribute("categories", TypeOfCarOptions.values());
        model.addAttribute("newOption", new CarOption());
        return "admin/options/option-list";
    }

    @PostMapping
    public String saveOption(@ModelAttribute("newOption") CarOption carOption) {
        // --- KROK DIAGNOSTYCZNY ---
        // Sprawdzamy, co dokładnie trafia do kontrolera z formularza.
        System.out.println("--- DIAGNOSTYKA: Otrzymano nazwę opcji: '" + carOption.getName() + "' ---");
        // --- KONIEC KROKU DIAGNOSTYCZNEGO ---

        carOptionService.save(carOption);
        return "redirect:/admin/options";
    }

    @GetMapping("/delete/{id}")
    public String deleteOption(@PathVariable UUID id) {
        carOptionService.delete(id);
        return "redirect:/admin/options";
    }
}
