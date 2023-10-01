package com.gridnine.testing;

import com.gridnine.testing.flight.Flight;
import com.gridnine.testing.flight.FlightBuilder;
import com.gridnine.testing.flightfilter.FlightFilter;
import com.gridnine.testing.flightfilter.FlightPrinter;
import com.gridnine.testing.flightfilter.impl.FlightFilterImpl;
import com.gridnine.testing.flightfilter.impl.FlightPrinterImpl;

import java.util.List;

public class FlightFilterRunner {
    public static void runFilters () {
        List<Flight> flights = FlightBuilder.createFlights();

        FlightFilter flightFilter = new FlightFilterImpl();
        FlightPrinter flightPrinter = new FlightPrinterImpl();

        flightPrinter.printFlights("(all the flights)", flights);
        flightPrinter.printFlights(
                 "having no segment with the departure is not in the past",
                flightFilter.getFlightsWithAnySegmentWithDepartureNotInThePast(flights));
        flightPrinter.printFlights(
                "having all the segments with the arrival after the departure",
                flightFilter.getFlightsWithSegmentsWithArrivalIsNotBeforeDeparture(flights));
       flightPrinter.printFlights(
               "having the total ground time not more than 2 hours",
               flightFilter.getFlightsWithGroundTimeIsNotMoreThan2Hours(flights)
       );
    }
}
