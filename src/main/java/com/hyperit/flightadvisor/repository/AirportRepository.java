package com.hyperit.flightadvisor.repository;

import com.hyperit.flightadvisor.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Integer> {

    @Query("SELECT a.id from Airport a")
    Set<Integer> getAllIds();

}
