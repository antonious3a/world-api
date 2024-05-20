package dev.antonio3a.worldapi.domain.services;

import dev.antonio3a.worldapi.domain.repositories.CountryLanguageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CountryLanguageService {

    private final CountryLanguageRepository countryLanguageRepository;
}
