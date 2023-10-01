package com.gridnine.testing.flightfilter;

import com.gridnine.testing.flight.Flight;

import java.util.List;

public interface FlightPrinter {
    void printFlights(String description, List<Flight> flights);
}
