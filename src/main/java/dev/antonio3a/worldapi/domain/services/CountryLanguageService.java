package dev.antonio3a.worldapi.domain.services;

import dev.antonio3a.worldapi.domain.entities.CountryLanguage;
import dev.antonio3a.worldapi.domain.repositories.CountryLanguageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryLanguageService {

    private final CountryLanguageRepository countryLanguageRepository;

    public List<CountryLanguage> getCountryLanguageByCountryId(String countryId) {
        return countryLanguageRepository.findByIdCountryCode(countryId);
    }

    public Page<CountryLanguage> getCountryLanguages(Pageable pageable) {
        return countryLanguageRepository.findAll(pageable);
    }
}
