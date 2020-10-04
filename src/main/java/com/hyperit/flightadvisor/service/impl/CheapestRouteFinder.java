package com.hyperit.flightadvisor.service.impl;

import com.hyperit.flightadvisor.bean.FlightSearchResponse;
import com.hyperit.flightadvisor.bean.RouteResponse;
import com.hyperit.flightadvisor.bean.Node;
import com.hyperit.flightadvisor.entity.Airport;
import com.hyperit.flightadvisor.entity.Route;
import com.hyperit.flightadvisor.service.AirportService;
import com.hyperit.flightadvisor.service.RouteFinder;
import com.hyperit.flightadvisor.service.RouteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

import static java.util.Optional.empty;
import static java.util.Optional.of;

@Log4j2
@Service
@RequiredArgsConstructor
public class CheapestRouteFinder implements RouteFinder {

    private final RouteService routeService;
    private final AirportService airportService;

    /**
     * Find cheapest route for all airports of requested cities.
     */
    public FlightSearchResponse find(List<Airport> airportsFrom, List<Airport> airportsTo) {
        List<Node> possibleRoutes = new ArrayList<>();
        for (Airport airportFrom : airportsFrom) {
            for (Airport airportTo : airportsTo) {
                List<Node> result = this.getCheapestPathBetweenAirports(airportFrom, airportTo);
                possibleRoutes.addAll(result);
            }
        }

        return this.getCheapestRoute(possibleRoutes);
    }

    /**
     * Find cheapest path between given airports.
     */
    private List<Node> getCheapestPathBetweenAirports(Airport airportFrom, Airport airportTo) {
        List<Node> possibleRoutes = new ArrayList<>();

        List<Airport> allAirports = airportService.findAll();
        List<Route> allRoutes = routeService.findAll();
        Map<Integer, Node> nodes = this.getAirportsWithPossibleDestinations(allAirports, allRoutes);

        Optional<Node> result = this.getCheapestPath(nodes.get(airportFrom.getId()), nodes.get(airportTo.getId()));
        result.ifPresent(possibleRoutes::add);

        return possibleRoutes;
    }

    /**
     * Find cheapest path between given airports nodes.
     */
    public Optional<Node> getCheapestPath(Node source, Node destination) {
        Set<Node> checkedNodes = new HashSet<>();
        Set<Node> uncheckedNodes = new HashSet<>();

        source.setPrice(BigDecimal.ZERO);
        uncheckedNodes.add(source);

        while (uncheckedNodes.size() != 0) {
            Node currentNode = getLowestDistanceNode(uncheckedNodes);
            uncheckedNodes.remove(currentNode);
            for (Map.Entry<Node, BigDecimal> associatedPair : currentNode.getAssociatedNodes().entrySet()) {
                Node associatedNode = associatedPair.getKey();
                BigDecimal priceOfRoute = associatedPair.getValue();
                if (!checkedNodes.contains(associatedNode)) {
                    calculateMinimumPrice(associatedNode, priceOfRoute, currentNode);
                    uncheckedNodes.add(associatedNode);
                }
            }
            checkedNodes.add(currentNode);
        }

        for (Node node : checkedNodes) {
            if (node.getAirportId().equals(destination.getAirportId())) {
                return of(node);
            }
        }

        return empty();
    }

    /**
     * Create airport nodes and link them using routes.
     */
    private Map<Integer, Node> getAirportsWithPossibleDestinations(List<Airport> airports, List<Route> routes) {
        Map<Integer, Node> nodes = new HashMap<>();
        airports.forEach(airport -> {
            Node node = new Node(airport.getId(), airport.getName());
            nodes.put(node.getAirportId(), node);
        });

        routes.forEach(route -> nodes.get(route.getSourceAirport().getId())
                .addDestination(nodes.get(route.getDestinationAirport().getId()), route.getPrice()));

        return nodes;
    }

    /**
     * Calculate total amount for given list of routes.
     */
    private BigDecimal calculateTotalAmount(List<RouteResponse> responseRoutes) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (RouteResponse routeResponse : responseRoutes) {
            totalAmount = totalAmount.add(routeResponse.getAmount());
        }
        return totalAmount;
    }

    /**
     * Create {@link RouteResponse} from given nodes.
     */
    private RouteResponse createRouteResponse(Node sourceNode, Node destinationNode, List<Route> routes) {
        return new RouteResponse()
                .setDestination(destinationNode.getAirportName())
                .setSource(sourceNode.getAirportName())
                .setAmount(getRoutePrice(routes, sourceNode.getAirportId(), destinationNode.getAirportId()));

    }

    /**
     * Get cheapest price for route with given sourceAirportId and destinationAirportId.
     */
    private BigDecimal getRoutePrice(List<Route> routes, Integer sourceAirportId, Integer destinationAirportId) {
        BigDecimal price = BigDecimal.valueOf(Integer.MAX_VALUE);
        for (Route route : routes) {
            if (route.getSourceAirport().getId().equals(sourceAirportId) &&
                    route.getDestinationAirport().getId().equals(destinationAirportId)
                    && price.compareTo(route.getPrice()) > 0) {
                price = route.getPrice();
            }
        }
        return price;
    }

    /**
     * Get cheapest route from given list of routes.
     */
    public FlightSearchResponse getCheapestRoute(List<Node> possibleRoutes) {
        List<Route> allRoutes = routeService.findAll();

        List<FlightSearchResponse> searchResponses = new ArrayList<>();
        for (Node node : possibleRoutes) {
            FlightSearchResponse response = new FlightSearchResponse();
            List<RouteResponse> responseRoutes = new ArrayList<>();

            List<Node> shortestPath = node.getShortestPath();
            for (int i = 1; i < shortestPath.size(); i++) {
                Node destinationNode = shortestPath.get(i);
                Node sourceNode = shortestPath.get(i).getShortestPath().get(
                        shortestPath.get(i).getShortestPath().size() - 1);

                RouteResponse routeResponse = createRouteResponse(sourceNode, destinationNode, allRoutes);
                responseRoutes.add(routeResponse);
            }

            Node sourceNode = shortestPath.get(shortestPath.size() - 1);
            RouteResponse routeResponse = createRouteResponse(sourceNode, node, allRoutes);
            responseRoutes.add(routeResponse);

            response.setTotalAmount(calculateTotalAmount(responseRoutes));
            response.setRoutes(responseRoutes);

            searchResponses.add(response);
        }

        FlightSearchResponse cheapestRoute = null;
        for (FlightSearchResponse response : searchResponses) {
            if (cheapestRoute == null) {
                cheapestRoute = response;
            } else if (response.getTotalAmount().compareTo(cheapestRoute.getTotalAmount()) < 0) {
                cheapestRoute = response;
            }
        }

        return cheapestRoute;
    }

    /**
     * Get node with lowest distance.
     */
    private Node getLowestDistanceNode(Set<Node> unsettledNodes) {
        Node lowestDistanceNode = null;
        BigDecimal lowestDistance = BigDecimal.valueOf(Integer.MAX_VALUE);
        for (Node node : unsettledNodes) {
            BigDecimal nodeDistance = node.getPrice();
            if (nodeDistance.compareTo(lowestDistance) < 0) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }

    /**
     * Calculate minimum distance from source airport to given node.
     */
    private void calculateMinimumPrice(Node evaluationNode,
                                       BigDecimal priceOfRoute, Node sourceNode) {
        BigDecimal sourcePrice = sourceNode.getPrice();
        if (sourcePrice.add(priceOfRoute).compareTo(evaluationNode.getPrice()) < 0) {
            List<Node> shortestPath = new ArrayList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
            evaluationNode.setPrice(sourcePrice.add(priceOfRoute));
        }
    }
}
