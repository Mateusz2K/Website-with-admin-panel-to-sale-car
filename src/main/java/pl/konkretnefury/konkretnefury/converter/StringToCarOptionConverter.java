package pl.konkretnefury.konkretnefury.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.konkretnefury.konkretnefury.modele.CarOption;
import pl.konkretnefury.konkretnefury.repository.CarOptionRepository;

import java.util.UUID;

@Component
public class StringToCarOptionConverter implements Converter<String, CarOption> {

    private final CarOptionRepository carOptionRepository;

    public StringToCarOptionConverter(CarOptionRepository carOptionRepository) {
        this.carOptionRepository = carOptionRepository;
    }

    @Override
    public CarOption convert(String source) {
        // Konwertuje ID w formacie String na UUID i wyszukuje encję w bazie
        return carOptionRepository.findById(UUID.fromString(source)).orElse(null);
    }
}
