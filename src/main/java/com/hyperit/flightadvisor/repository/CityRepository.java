package com.hyperit.flightadvisor.repository;

import com.hyperit.flightadvisor.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {

    boolean existsByNameAndCountryName(String name, String country);

}
