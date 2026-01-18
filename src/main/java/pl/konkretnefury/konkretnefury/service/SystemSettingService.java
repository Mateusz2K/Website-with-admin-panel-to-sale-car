package pl.konkretnefury.konkretnefury.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.konkretnefury.konkretnefury.modele.SystemSetting;
import pl.konkretnefury.konkretnefury.repository.SystemSettingRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class SystemSettingService {

    public static final String DEFAULT_OFFER_IMAGE_KEY = "default_offer_image";
    public static final String DEFAULT_WARRANTY_DESCRIPTION_KEY = "default_warranty_description";
    // ZMIANA: Nowy klucz dla domyślnego opisu
    public static final String DEFAULT_OFFER_DESCRIPTION_KEY = "default_offer_description";
    
    private final SystemSettingRepository settingRepository;
    private final FileStorageService fileStorageService;

    @Value("${file.upload-dir.icons}")
    private String iconUploadDir;

    public SystemSettingService(SystemSettingRepository settingRepository, FileStorageService fileStorageService) {
        this.settingRepository = settingRepository;
        this.fileStorageService = fileStorageService;
    }

    public String getDefaultOfferImageUrl() {
        return settingRepository.findById(DEFAULT_OFFER_IMAGE_KEY)
                .map(SystemSetting::getSettingValue)
                .orElse("/img/placeholder.png");
    }

    public void saveDefaultOfferImage(MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            Optional<SystemSetting> oldSetting = settingRepository.findById(DEFAULT_OFFER_IMAGE_KEY);
            if (oldSetting.isPresent()) {
                String oldPathUrl = oldSetting.get().getSettingValue();
                if (oldPathUrl != null && !oldPathUrl.equals("/img/placeholder.png")) {
                    try {
                        String fileName = oldPathUrl.substring(oldPathUrl.lastIndexOf("/") + 1);
                        Path oldFilePath = Paths.get(iconUploadDir, fileName);
                        Files.deleteIfExists(oldFilePath);
                    } catch (IOException e) {
                        System.err.println("Nie udało się usunąć starej domyślnej grafiki: " + e.getMessage());
                    }
                }
            }
            String path = fileStorageService.storeDefaultImageOffer(file);
            SystemSetting setting = new SystemSetting(DEFAULT_OFFER_IMAGE_KEY, path);
            settingRepository.save(setting);
        }
    }

    public String getDefaultWarrantyDescription() {
        return settingRepository.findById(DEFAULT_WARRANTY_DESCRIPTION_KEY)
                .map(SystemSetting::getSettingValue)
                .orElse("");
    }

    public void saveDefaultWarrantyDescription(String description) {
        SystemSetting setting = new SystemSetting(DEFAULT_WARRANTY_DESCRIPTION_KEY, description);
        settingRepository.save(setting);
    }

    // ZMIANA: Metody dla domyślnego opisu oferty
    public String getDefaultOfferDescription() {
        return settingRepository.findById(DEFAULT_OFFER_DESCRIPTION_KEY)
                .map(SystemSetting::getSettingValue)
                .orElse("");
    }

    public void saveDefaultOfferDescription(String description) {
        SystemSetting setting = new SystemSetting(DEFAULT_OFFER_DESCRIPTION_KEY, description);
        settingRepository.save(setting);
    }
}
