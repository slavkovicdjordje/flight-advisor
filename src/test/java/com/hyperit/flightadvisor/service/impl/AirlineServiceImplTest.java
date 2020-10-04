package com.hyperit.flightadvisor.service.impl;

import com.hyperit.flightadvisor.repository.AirlineRepository;
import com.hyperit.flightadvisor.service.AirlineService;
import com.hyperit.flightadvisor.entity.Airline;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AirlineServiceImplTest {

    AirlineService airlineService;

    @Mock
    AirlineRepository airlineRepository;

    @BeforeEach
    void setUp() {
        airlineService = new AirlineServiceImpl(airlineRepository);
    }

    @Test
    public void saveAll_Success() {
        Set<Airline> airlinesSetMock = mock(Set.class);
        List<Airline> airlinesListMock = mock(List.class);
        when(airlineRepository.saveAll(airlinesSetMock)).thenReturn(airlinesListMock);

        airlineService.saveAll(airlinesSetMock);

        verify(airlineRepository, times(1)).saveAll(airlinesSetMock);
    }

}