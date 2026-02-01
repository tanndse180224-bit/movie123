package hsf302.myMovie.controllers;

import hsf302.myMovie.models.Country;
import hsf302.myMovie.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/countries")
public class CountryController {

    @Autowired
    private CountryService countryService;

    @GetMapping
    public String getAllCountries(Model model) {
        List<Country> countries = countryService.getAllCountries();
        model.addAttribute("countries", countries);
        return "countries/index";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("country", new Country());
        return "countries/create";
    }

    @PostMapping("/save")
    public String saveCountry(@ModelAttribute Country country) {
        countryService.saveCountry(country);
        return "redirect:/countries";
    }


    @GetMapping("/delete/{id}")
    public String deleteCountry(@PathVariable int id) {
        countryService.deleteCountry(id);
        return "redirect:/countries";
    }
}
