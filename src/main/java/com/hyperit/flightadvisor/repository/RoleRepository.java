package com.hyperit.flightadvisor.repository;

import com.hyperit.flightadvisor.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {

    Role findByName(String name);

}
