package com.hyperit.flightadvisor.service.impl;

import com.hyperit.flightadvisor.repository.CountryRepository;
import com.hyperit.flightadvisor.service.CountryService;
import com.hyperit.flightadvisor.entity.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static net.bytebuddy.utility.RandomString.make;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CountryServiceImplTest {

    CountryService countryService;

    @Mock
    CountryRepository countryRepository;

    @BeforeEach
    void setUp() {
        countryService = new CountryServiceImpl(countryRepository);
    }

    @Test
    public void findOrCreate_CreatedNew() {
        String name = make();
        Country country = new Country();
        country.setName(name);
        when(countryRepository.findByName(name)).thenReturn(null);
        when(countryRepository.save(any())).thenReturn(country);

        Country result = countryService.findOrCreate(name);

        assertThat(result).isEqualTo(country);
    }

    @Test
    public void findOrCreate_GetExisting() {
        String name = make();
        Country countryMock = mock(Country.class);
        when(countryRepository.findByName(name)).thenReturn(countryMock);

        Country result = countryService.findOrCreate(name);

        assertThat(result).isEqualTo(countryMock);
        verify(countryRepository, never()).save(any());
    }

    @Test
    public void findAll_Success() {
        List<Country> countriesMock = mock(List.class);
        when(countryRepository.findAll()).thenReturn(countriesMock);

        List<Country> result = countryService.findAll();

        assertThat(result).isEqualTo(countriesMock);
        verifyNoInteractions(countriesMock);
    }
}