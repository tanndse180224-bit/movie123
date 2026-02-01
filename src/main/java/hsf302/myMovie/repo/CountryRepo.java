package hsf302.myMovie.repo;

import hsf302.myMovie.models.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepo extends JpaRepository<Country,Integer> {
}
