package pl.konkretnefury.konkretnefury.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.konkretnefury.konkretnefury.service.LoginAttemptService;

@Controller
@RequestMapping("/admin/security")
public class AdminSecurityController {

    private final LoginAttemptService loginAttemptService;

    public AdminSecurityController(LoginAttemptService loginAttemptService) {
        this.loginAttemptService = loginAttemptService;
    }

    @GetMapping
    public String blockedIPs(Model model) {
        model.addAttribute("blockedIPs", loginAttemptService.getBlockedIPs());
        return "admin/security/blocked-ips";
    }

    @PostMapping("/unblock")
    public String unblockIP(@RequestParam String ip) {
        loginAttemptService.unblockIP(ip);
        return "redirect:/admin/security";
    }
}
