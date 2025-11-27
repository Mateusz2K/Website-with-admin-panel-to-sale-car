package pl.konkretnefury.konkretnefury.service;

import org.springframework.stereotype.Service;
import pl.konkretnefury.konkretnefury.modele.CarOption;
import pl.konkretnefury.konkretnefury.repository.CarOptionRepository;

import java.util.List;
import java.util.UUID;

@Service
public class CarOptionService {

    private final CarOptionRepository carOptionRepository;

    public CarOptionService(CarOptionRepository carOptionRepository) {
        this.carOptionRepository = carOptionRepository;
    }

    public List<CarOption> findAll() {
        return carOptionRepository.findAll();
    }

    public void save(CarOption carOption) {
        carOptionRepository.save(carOption);
    }

    public void delete(UUID id) {
        carOptionRepository.deleteById(id);
    }
}
