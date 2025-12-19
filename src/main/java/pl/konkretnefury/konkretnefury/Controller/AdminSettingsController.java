package pl.konkretnefury.konkretnefury.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import pl.konkretnefury.konkretnefury.service.SystemSettingService;

@Controller
@RequestMapping("/admin/settings")
public class AdminSettingsController {

    private final SystemSettingService settingService;

    public AdminSettingsController(SystemSettingService settingService) {
        this.settingService = settingService;
    }

    @GetMapping
    public String settingsPage(Model model) {
        model.addAttribute("defaultOfferImageUrl", settingService.getDefaultOfferImageUrl());
        return "admin/settings/settings-page";
    }

    @PostMapping("/upload-default-image")
    public String uploadDefaultImage(@RequestParam("defaultImage") MultipartFile file) {
        settingService.saveDefaultOfferImage(file);
        return "redirect:/admin/settings";
    }
}
