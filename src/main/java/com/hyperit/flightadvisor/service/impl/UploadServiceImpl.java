package com.hyperit.flightadvisor.service.impl;

import com.hyperit.flightadvisor.bean.AirportDto;
import com.hyperit.flightadvisor.bean.RouteDto;
import com.hyperit.flightadvisor.exception.UploadException;
import com.hyperit.flightadvisor.entity.Airline;
import com.hyperit.flightadvisor.entity.Airport;
import com.hyperit.flightadvisor.entity.City;
import com.hyperit.flightadvisor.entity.Country;
import com.hyperit.flightadvisor.entity.Route;
import com.hyperit.flightadvisor.service.AirlineService;
import com.hyperit.flightadvisor.service.AirportService;
import com.hyperit.flightadvisor.service.CountryService;
import com.hyperit.flightadvisor.service.RouteService;
import com.hyperit.flightadvisor.service.UploadService;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.lang.Integer.valueOf;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Log4j2
@Service
@RequiredArgsConstructor
public class UploadServiceImpl implements UploadService {

    private final AirportService airportService;
    private final RouteService routeService;
    private final CountryService countryService;
    private final AirlineService airlineService;

    @Override
    public void uploadAirports(MultipartFile file) {
        log.debug("Uploading airports.");


        try (Reader reader = new InputStreamReader(file.getInputStream())) {
            CsvToBean<AirportDto> csvData = getData(reader, AirportDto.class);
            Map<String, Map<String, City>> countryCityMap = getCountryCityMap();

            List<Airport> airports = csvData.stream()
                    .filter(a -> isCityValid(a, countryCityMap))
                    .map(a -> createAirport(a, countryCityMap.get(a.getCountry()).get(a.getCity())))
                    .collect(toList());

            saveNewAirports(airports);
        } catch (IOException e) {
            throw new UploadException("Exception on airports upload", e);
        }
    }

    @Override
    public void uploadRoutes(MultipartFile file) {
        log.debug("Uploading routes.");

        try (Reader reader = new InputStreamReader(file.getInputStream())) {
            CsvToBean<RouteDto> csvData = getData(reader, RouteDto.class);
            Map<String, Airport> airports = airportService.findAll().stream()
                    .collect(toMap(airport -> airport.getId().toString(), airport -> airport));

            Predicate<RouteDto> validRoutePredicate = route -> isRouteValid(route, airports);
            Function<RouteDto, Route> mapToRoute = routeDto -> createRoute(routeDto, airports);

            List<Route> routes = csvData.stream()
                    .filter(validRoutePredicate)
                    .map(mapToRoute)
                    .collect(toList());

            Set<Airline> airlines = new HashSet<>();
            routes.forEach(route -> airlines.add(route.getAirline()));

            airlineService.saveAll(airlines);
            routeService.saveAll(routes);
        } catch (IOException e) {
            log.error("Exception on airports upload", e);
            throw new UploadException("Exception on airports upload", e);
        }
    }

    /**
     * Get data from reader.
     *
     * @param reader Reader.
     * @param type   Type of data to be read.
     * @return CsvToBean
     */
    private <T> CsvToBean<T> getData(Reader reader, Class<T> type) {
        return new CsvToBeanBuilder<T>(reader)
                .withSeparator(',')
                .withIgnoreLeadingWhiteSpace(true)
                .withIgnoreEmptyLine(true)
                .withType(type)
                .build();
    }

    /**
     * Get cities map grouped by country.
     *
     * @return Map<String, Map < String, City>>
     */
    private Map<String, Map<String, City>> getCountryCityMap() {
        return countryService.findAll().stream()
                .collect(toMap(Country::getName,
                        country -> country.getCities().stream()
                                .collect(toMap(City::getName,
                                        city -> city))));
    }

    private boolean isCityValid(AirportDto airport, Map<String, Map<String, City>> countryCityMap) {
        return countryCityMap.get(airport.getCountry()) != null
                && countryCityMap.get(airport.getCountry()).get(airport.getCity()) != null
                && countryCityMap.get(airport.getCountry()).get(airport.getCity()).getName().equals(airport.getCity());
    }

    private boolean isRouteValid(RouteDto route, Map<String, Airport> airports) {
        return !route.getAirlineId().equals("N")
                && !route.getSourceAirportId().equals("N")
                && !route.getDestinationAirportId().equals("N")
                && airports.get(route.getDestinationAirportId()) != null
                && airports.get(route.getSourceAirportId()) != null;
    }


    private Airport createAirport(AirportDto a, City city) {
        return new Airport()
                .setId(a.getId())
                .setName(a.getName())
                .setCity(city)
                .setIATA(a.getIATA().equals("N") ? null : a.getIATA())
                .setICAO(a.getICAO().equals("N") ? null : a.getICAO())
                .setLatitude(a.getLatitude())
                .setLongitude(a.getLongitude())
                .setAltitude(a.getAltitude())
                .setTimezone(a.getTimezone())
                .setDST(a.getDST())
                .setOlson(a.getOlsonTimeZone())
                .setType(a.getType())
                .setSource(a.getSource());
    }

    private void saveNewAirports(List<Airport> airports) {
        Set<Integer> existingAirportIds = airportService.getAllIds();
        airports = airports.stream()
                .filter(a -> !existingAirportIds.contains(a.getId()))
                .collect(toList());
        airportService.saveAll(airports);
    }

    private Route createRoute(RouteDto route, Map<String, Airport> airports) {
        Airline airline = new Airline()
                .setId(valueOf(route.getAirlineId()))
                .setName(route.getAirline());

        return new Route()
                .setAirline(airline)
                .setSourceAirport(airports.get(route.getSourceAirportId()))
                .setDestinationAirport(airports.get(route.getDestinationAirportId()))
                .setCodeshare(route.getCodeshare())
                .setStops(route.getStops())
                .setEquipment(route.getEquipment())
                .setPrice(route.getPrice());
    }
}
