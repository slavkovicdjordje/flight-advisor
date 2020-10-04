package com.hyperit.flightadvisor.service.impl;

import com.hyperit.flightadvisor.bean.CityFilter;
import com.hyperit.flightadvisor.bean.CitySearchResponse;
import com.hyperit.flightadvisor.exception.BadRequestException;
import com.hyperit.flightadvisor.mapper.CityMapper;
import com.hyperit.flightadvisor.repository.CityRepository;
import com.hyperit.flightadvisor.service.CityService;
import com.hyperit.flightadvisor.service.CountryService;
import com.hyperit.flightadvisor.entity.City;
import com.hyperit.flightadvisor.entity.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Random;

import static java.lang.String.format;
import static net.bytebuddy.utility.RandomString.make;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CityServiceImplTest {

    CityService cityService;

    @Mock
    CityRepository cityRepository;
    @Mock
    CountryService countryService;
    @Mock
    CityMapper cityMapper;

    private final String CITY_NAME = make();
    private final String COUNTRY_NAME = make();

    @BeforeEach
    void setUp() {
        cityService = new CityServiceImpl(cityRepository, countryService, cityMapper);
    }

    @Test
    public void addCity_ExistingCityException() {
        Country country = new Country();
        country.setName(COUNTRY_NAME);

        City city = new City();
        city.setName(CITY_NAME);
        city.setCountry(country);


        when(countryService.findOrCreate(city.getCountry().getName())).thenReturn(country);
        when(cityRepository.existsByNameAndCountryName(city.getName(), city.getCountry().getName())).thenReturn(true);

        BadRequestException result = assertThrows(BadRequestException.class, () -> cityService.addCity(city));

        assertEquals(format("City %s from country %s already exists.", city.getName(), city.getCountry().getName()),
                result.getMessage());
        verify(cityRepository, never()).save(any());

    }

    @Test
    public void addCity_Success() {
        Country country = new Country();
        country.setName(COUNTRY_NAME);

        City city = new City();
        city.setName(CITY_NAME);
        city.setCountry(country);


        when(countryService.findOrCreate(city.getCountry().getName())).thenReturn(country);
        when(cityRepository.existsByNameAndCountryName(city.getName(), city.getCountry().getName())).thenReturn(false);
        when(cityRepository.save(city)).thenReturn(city);

        City result = cityService.addCity(city);

        assertThat(result).isEqualTo(city);

    }

    @Test
    public void findCities_Success() {
        String name = make();
        Random random = new Random();
        Integer noOfComments = random.nextInt();
        CityFilter filter = new CityFilter()
                .setName(name)
                .setNoOfComments(noOfComments);
        List<CitySearchResponse> citySearchResponse = mock(List.class);
        when(cityMapper.search(filter)).thenReturn(citySearchResponse);

        List<CitySearchResponse> result = cityService.findCities(name, noOfComments);

        assertThat(result).isEqualTo(citySearchResponse);
    }

}