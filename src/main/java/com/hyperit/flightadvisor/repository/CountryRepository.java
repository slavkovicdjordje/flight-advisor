package com.hyperit.flightadvisor.repository;

import com.hyperit.flightadvisor.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {

    Country findByName(String name);
}
