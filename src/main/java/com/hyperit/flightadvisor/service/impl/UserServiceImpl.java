package com.hyperit.flightadvisor.service.impl;

import com.hyperit.flightadvisor.bean.UserRole;
import com.hyperit.flightadvisor.exception.BadRequestException;
import com.hyperit.flightadvisor.repository.RoleRepository;
import com.hyperit.flightadvisor.repository.UserRepository;
import com.hyperit.flightadvisor.entity.Role;
import com.hyperit.flightadvisor.entity.User;
import com.hyperit.flightadvisor.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static java.lang.String.format;
import static java.util.Collections.singletonList;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(User user) {
        log.debug("Saving user with username: " + user.getUsername());

        if (userRepository.existsByUsername(user.getUsername())) {
            log.error("There is already registered user with username: " + user.getUsername());
            throw new BadRequestException("Username is already in use.");
        }
        Role role = roleRepository.findByName(UserRole.ROLE_USER.name());
        user.setRoles(singletonList(role));
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public User findById(Integer id) {
        log.debug(format("Fetching user with id %d.", id));

        return userRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(format("There is no user with id: %s", id)));
    }
}
