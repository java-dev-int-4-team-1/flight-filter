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
                "With there is a segment with the departure is after now",
                flightFilter.getFlightsWithAnySegmentWithDepartureNotInThePast(flights));
        flightPrinter.printFlights(
                "With all the segments have the arrival after the departure",
                flightFilter.getFlightsWithSegmentsWithArrivalIsNotBeforeDeparture(flights));
       flightPrinter.printFlights(
               "With the total time between all the segments on land is not more than 2 hours",
               flightFilter.getFlightsWithGroundTimeIsNotMoreThan2Hours(flights)
       );
    }
}
