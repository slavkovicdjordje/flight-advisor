package com.hyperit.flightadvisor.repository;

import com.hyperit.flightadvisor.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends JpaRepository<Route, Integer> {

}
