package com.gridnine.testing.flightfilter.impl;

import com.gridnine.testing.flight.Flight;
import com.gridnine.testing.flightfilter.FlightPrinter;

import java.util.List;

import static java.lang.System.out;

public class FlightPrinterImpl implements FlightPrinter {
    @Override
    public void printFlights(String description, List<Flight> flights) {
        String delimiter = "================================================================================";
        out.println(delimiter);
        out.println("FLIGHTS with the segments");
        out.println(description);
        out.println(delimiter);
        flights.forEach(out::println);
        out.println(delimiter);
        out.println();
    }
}
