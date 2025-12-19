package pl.konkretnefury.konkretnefury.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.konkretnefury.konkretnefury.modele.SystemSetting;
import pl.konkretnefury.konkretnefury.repository.SystemSettingRepository;

@Service
public class SystemSettingService {

    public static final String DEFAULT_OFFER_IMAGE_KEY = "default_offer_image";
    private final SystemSettingRepository settingRepository;
    private final FileStorageService fileStorageService;

    public SystemSettingService(SystemSettingRepository settingRepository, FileStorageService fileStorageService) {
        this.settingRepository = settingRepository;
        this.fileStorageService = fileStorageService;
    }

    public String getDefaultOfferImageUrl() {
        return settingRepository.findById(DEFAULT_OFFER_IMAGE_KEY)
                .map(SystemSetting::getSettingValue)
                .orElse("/img/placeholder.png"); // Domyślna wartość, jeśli nic nie ma w bazie
    }

    public void saveDefaultOfferImage(MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            String path = fileStorageService.storeIcon(file); // Używamy tego samego folderu co dla ikon marek
            SystemSetting setting = new SystemSetting(DEFAULT_OFFER_IMAGE_KEY, path);
            settingRepository.save(setting);
        }
    }
}
