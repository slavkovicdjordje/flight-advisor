package com.hyperit.flightadvisor.repository;

import com.hyperit.flightadvisor.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByUsername(String username);
    Optional<User> findById(Integer id);
    Optional<User> findByUsername(String username);

}
