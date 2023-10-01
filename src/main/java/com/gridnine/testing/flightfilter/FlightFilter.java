package com.gridnine.testing.flightfilter;

import com.gridnine.testing.flight.Flight;

import java.util.List;

public interface FlightFilter {
    List<Flight> getFlightsWithAnySegmentWithDepartureNotInThePast(List<Flight> flights);
    List<Flight> getFlightsWithSegmentsWithArrivalIsNotBeforeDeparture(List<Flight> flights);
    List<Flight> getFlightsWithGroundTimeIsNotMoreThan2Hours(List<Flight> flights);
}
