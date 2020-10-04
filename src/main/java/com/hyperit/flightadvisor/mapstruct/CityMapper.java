package com.hyperit.flightadvisor.mapstruct;

import com.hyperit.flightadvisor.bean.AddCityDto;
import com.hyperit.flightadvisor.entity.City;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CityMapper {

    CityMapper INSTANCE = Mappers.getMapper(CityMapper.class);

    @Mappings({
            @Mapping(target = "country.name", source = "countryName")
    })
    City addCityRequestToCity(AddCityDto addCityDto);

    @Mappings({
            @Mapping(target = "countryName", source = "country.name")
    })
    AddCityDto cityToAddCityDto(City city);

}
