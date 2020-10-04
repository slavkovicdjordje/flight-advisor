package com.hyperit.flightadvisor.service.impl;

import com.hyperit.flightadvisor.bean.UserRole;
import com.hyperit.flightadvisor.exception.BadRequestException;
import com.hyperit.flightadvisor.repository.RoleRepository;
import com.hyperit.flightadvisor.repository.UserRepository;
import com.hyperit.flightadvisor.service.UserService;
import com.hyperit.flightadvisor.entity.Role;
import com.hyperit.flightadvisor.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static java.util.Collections.singletonList;
import static net.bytebuddy.utility.RandomString.make;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    UserService userService;

    @Mock
    UserRepository userRepository;
    @Mock
    RoleRepository roleRepository;
    @Mock
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository, roleRepository, passwordEncoder);
    }

    @Test
    public void saveUser_ExistingUserException() {
        User user = mock(User.class);
        when(userRepository.existsByUsername(user.getUsername())).thenReturn(true);

        BadRequestException result = assertThrows(BadRequestException.class, () -> userService.saveUser(user));

        assertEquals("Username is already in use.", result.getMessage());
        verify(roleRepository, never()).findByName(any());
        verify(passwordEncoder, never()).encode(any());
        verify(userRepository, never()).save(any());
    }

    @Test
    public void saveUser_Success() {
        User userMock = mock(User.class);
        Role roleMock = mock(Role.class);
        String encodedPassword = make();
        when(userRepository.existsByUsername(userMock.getUsername())).thenReturn(false);
        when(roleRepository.findByName(UserRole.ROLE_USER.name())).thenReturn(roleMock);
        when(passwordEncoder.encode(userMock.getPassword())).thenReturn(encodedPassword);
        when(userRepository.save(userMock)).thenReturn(userMock);

        User result = userService.saveUser(userMock);

        assertThat(result).isEqualTo(userMock);
        verify(userMock).setPassword(encodedPassword);
        verify(userMock).setRoles(singletonList(roleMock));
    }
}