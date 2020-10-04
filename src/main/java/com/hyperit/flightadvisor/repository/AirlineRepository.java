package com.hyperit.flightadvisor.repository;

import com.hyperit.flightadvisor.entity.Airline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirlineRepository extends JpaRepository<Airline, Integer> {

}
