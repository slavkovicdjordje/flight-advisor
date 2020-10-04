package com.hyperit.flightadvisor.mapper;

import com.hyperit.flightadvisor.bean.CityFilter;
import com.hyperit.flightadvisor.bean.CitySearchResponse;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityMapper {

    List<CitySearchResponse> search(CityFilter cityFilter);
}
