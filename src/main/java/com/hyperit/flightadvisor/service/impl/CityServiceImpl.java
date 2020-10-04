package com.hyperit.flightadvisor.service.impl;

import com.hyperit.flightadvisor.bean.CityFilter;
import com.hyperit.flightadvisor.bean.CitySearchResponse;
import com.hyperit.flightadvisor.exception.BadRequestException;
import com.hyperit.flightadvisor.repository.CityRepository;
import com.hyperit.flightadvisor.entity.City;
import com.hyperit.flightadvisor.entity.Country;
import com.hyperit.flightadvisor.mapper.CityMapper;
import com.hyperit.flightadvisor.service.CityService;
import com.hyperit.flightadvisor.service.CountryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static java.lang.String.format;

@Log4j2
@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final CountryService countryService;
    private final CityMapper cityMapper;

    @Override
    @Transactional
    public City addCity(City city) {
        log.debug(format("Adding city %s from country %s", city.getName(), city.getCountry()));

        Country country = countryService.findOrCreate(city.getCountry().getName());
        city.setCountry(country);
        if (cityRepository.existsByNameAndCountryName(city.getName(), city.getCountry().getName())) {
            log.error(format("City %s from country %s already exists.", city.getName(), city.getCountry().getName()));
            throw new BadRequestException(
                    format("City %s from country %s already exists.", city.getName(), city.getCountry().getName()));
        }

        return cityRepository.save(city);

    }

    @Override
    public List<CitySearchResponse> findCities(String cityName, Integer noOfComments) {
        log.debug(format("Searching for city with name %s", cityName));

        CityFilter filter = new CityFilter()
                .setName(cityName)
                .setNoOfComments(noOfComments);

        return cityMapper.search(filter);
    }
}
