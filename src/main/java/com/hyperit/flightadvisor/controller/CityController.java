package com.hyperit.flightadvisor.controller;

import com.hyperit.flightadvisor.bean.AddCityDto;
import com.hyperit.flightadvisor.bean.CitySearchResponse;
import com.hyperit.flightadvisor.entity.City;
import com.hyperit.flightadvisor.mapstruct.CityMapper;
import com.hyperit.flightadvisor.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("cities")
public class CityController {

    private final CityService cityService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public AddCityDto addCity(@RequestBody AddCityDto addCityDto) {

        City city = cityService.addCity(
                CityMapper.INSTANCE.addCityRequestToCity(addCityDto));

        return CityMapper.INSTANCE.cityToAddCityDto(city);
    }

    @GetMapping
//    @PreAuthorize("hasRole('ROLE_USER')")
    public List<CitySearchResponse> getCity(@RequestParam(value = "cityName", required = false) String cityName,
                                            @RequestParam(value = "noOfComments", required = false) Integer noOfComments) {

        return cityService.findCities(cityName, noOfComments);
    }

}
