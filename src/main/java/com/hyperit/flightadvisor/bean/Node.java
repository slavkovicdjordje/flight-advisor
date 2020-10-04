package com.hyperit.flightadvisor.bean;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@RequiredArgsConstructor
public class Node {
    private final Integer airportId;
    private final String airportName;
    private List<Node> shortestPath = new LinkedList<>();
    private BigDecimal price = BigDecimal.valueOf(Integer.MAX_VALUE);
    private Map<Node, BigDecimal> associatedNodes = new HashMap<>();

    public void addDestination(Node destination, BigDecimal price) {
        associatedNodes.put(destination, price);
    }
}