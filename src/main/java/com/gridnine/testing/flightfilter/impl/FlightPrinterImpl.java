package com.gridnine.testing.flightfilter.impl;

import com.gridnine.testing.flight.Flight;
import com.gridnine.testing.flightfilter.FlightPrinter;

import java.util.List;

public class FlightPrinterImpl implements FlightPrinter {
    @Override
    public void printFlights(String description, List<Flight> flights) {
        String DELIMITER = "================================================================================";
        System.out.println(DELIMITER);
        System.out.println("FLIGHTS with the segments");
        System.out.println(description);
        System.out.println(DELIMITER);
        flights.forEach(System.out::println);
        System.out.println(DELIMITER);
        System.out.println();
    }
}
