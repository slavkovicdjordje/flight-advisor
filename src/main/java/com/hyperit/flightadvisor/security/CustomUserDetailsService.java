package com.hyperit.flightadvisor.security;

import com.hyperit.flightadvisor.exception.BadRequestException;
import com.hyperit.flightadvisor.repository.UserRepository;
import com.hyperit.flightadvisor.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BadRequestException(format("There is no user with username: %s", username)));

        return new UserDetails(username, user.getPassword(), user.getId(), true,
                true, true, true, user.getRoles());
    }
}
