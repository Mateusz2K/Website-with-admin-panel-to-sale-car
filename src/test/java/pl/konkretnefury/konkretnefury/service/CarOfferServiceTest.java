package pl.konkretnefury.konkretnefury.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.konkretnefury.konkretnefury.modele.CarOffer;
import pl.konkretnefury.konkretnefury.repository.CarOfferImageRepo;
import pl.konkretnefury.konkretnefury.repository.CarOfferRepo;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarOfferServiceTest {

    @Mock
    private CarOfferRepo carOfferRepository;
    @Mock
    private CarOfferImageRepo carOfferImageRepository;
    @Mock
    private FileStorageService fileStorageService;

    @InjectMocks
    private CarOfferService carOfferService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(carOfferService, "carPhotoUploadDir", "test-uploads");
    }

    @Test
    void deleteOffer_shouldDeleteOfferAndFiles_whenOfferExists() {
        // ARRANGE
        UUID offerId = UUID.randomUUID();
        // ZMIANA: Usunięto niepotrzebne "when(...)", ponieważ metoda deleteOffer
        // nie potrzebuje już wyniku findById do działania.

        // ACT
        carOfferService.deleteOffer(offerId);

        // ASSERT (Sprawdź)
        // Sprawdzamy, czy metoda deleteById na repozytorium została wywołana DOKŁADNIE RAZ
        // z poprawnym ID. To potwierdza, że logika serwisu doszła do tego punktu.
        verify(carOfferRepository, times(1)).deleteById(offerId);
    }

    @Test
    void deleteOffer_shouldNotThrowError_whenOfferDoesNotExist() {
        // ARRANGE
        UUID offerId = UUID.randomUUID();
        // ZMIANA: Usunięto niepotrzebne "when(...)"
        
        // ACT
        carOfferService.deleteOffer(offerId);

        // ASSERT
        // Sprawdzamy, czy deleteById zostało wywołane, ponieważ logika
        // usuwania plików nie przerywa już operacji.
        verify(carOfferRepository, times(1)).deleteById(offerId);
    }
    
    @Test
    void saveOfferWithImages_shouldUpdateExistingOffer_whenIdIsNotNull() {
        // ARRANGE
        UUID offerId = UUID.randomUUID();
        CarOffer formOffer = new CarOffer();
        formOffer.setId(offerId);
        formOffer.setRok(2022);

        CarOffer existingOffer = new CarOffer();
        existingOffer.setId(offerId);
        existingOffer.setRok(2020);

        when(carOfferRepository.findById(offerId)).thenReturn(Optional.of(existingOffer));
        when(carOfferRepository.save(any(CarOffer.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // ACT
        carOfferService.saveOfferWithImages(formOffer, new MultipartFile[0]);

        // ASSERT
        verify(carOfferRepository).save(argThat(savedOffer -> savedOffer.getRok() == 2022));
    }
}
