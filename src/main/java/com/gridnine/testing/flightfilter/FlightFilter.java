package com.gridnine.testing.flightfilter;

import com.gridnine.testing.flight.Flight;

import java.util.List;

public interface FlightFilter {
    List<Flight> getFlightsWithAnySegmentWhereDepartureAfterNow(List<Flight> flights);
    List<Flight> getFlightsWithSegmentsWhereArrivalIsNotBeforeDeparture(List<Flight> flights);
    List<Flight> getFlightsWhereTotalTimeBetweenAllTheSegmentsOnLandIsNotMoreThan2Hours(List<Flight> flights);
}
