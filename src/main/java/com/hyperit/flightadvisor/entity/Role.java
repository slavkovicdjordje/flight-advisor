package com.hyperit.flightadvisor.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    private Integer id;
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }
}

