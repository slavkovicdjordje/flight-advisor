package com.hyperit.flightadvisor.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
public class CustomUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private Integer userId;

    public CustomUsernamePasswordAuthenticationToken(Object principal, Object credentials, Integer userId,
                                                     Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
        setUserId(userId);
    }
}
