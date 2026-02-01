package hsf302.myMovie.services;

import hsf302.myMovie.models.Country;
import hsf302.myMovie.repo.CountryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryService {

    @Autowired
    private CountryRepo countryRepository;

    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    public Optional<Country> getCountryById(int id) {
        return countryRepository.findById(id);
    }

    public Country saveCountry(Country country) {
        return countryRepository.save(country);
    }

    public void deleteCountry(int id) {
        countryRepository.deleteById(id);
    }
}
