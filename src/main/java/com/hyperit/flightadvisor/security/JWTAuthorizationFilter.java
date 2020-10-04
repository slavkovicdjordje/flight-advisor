package com.hyperit.flightadvisor.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final JwtConfiguration jwtConfiguration;


    public JWTAuthorizationFilter(AuthenticationManager authManager, JwtConfiguration jwtConfiguration) {
        super(authManager);
        this.jwtConfiguration = jwtConfiguration;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HttpHeaders.AUTHORIZATION);

        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null) {
            DecodedJWT parsedToken = JWT.require(Algorithm.HMAC512(jwtConfiguration.getSecretKey().getBytes()))
                    .build()
                    .verify(header.replace("Bearer ", ""));
            String username = parsedToken.getSubject();
            List<GrantedAuthority> authorities = parsedToken.getClaim("roles").asList(String.class).stream()
                    .map(authority -> new SimpleGrantedAuthority(authority))
                    .collect(toList());
            Integer userId = parsedToken.getClaim("userId").asInt();

            if (username != null) {
                return new CustomUsernamePasswordAuthenticationToken(username, null, userId, authorities);
            }
        }
        return null;
    }
}
