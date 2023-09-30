package com.gridnine.testing.flightfilter.impl;

import com.gridnine.testing.flight.Flight;
import com.gridnine.testing.flight.FlightBuilder;
import com.gridnine.testing.flightfilter.FlightFilter;
import org.assertj.core.api.Condition;
import org.assertj.core.api.ListAssert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class FlightFilterImplTest {

    FlightFilter flightFilter = new FlightFilterImpl();

    @Test
    public void getFlightsWithAnySegmentWhereDepartureAfterNow() {
        List<Flight> flights = FlightBuilder.createFlights();
        List<Flight> result = flightFilter.getFlightsWithAnySegmentWhereDepartureAfterNow(flights);


        Condition<Flight> departureInThePastCondition = new Condition<>(
                flight -> flight
                        .getSegments()
                        .stream()
                        .anyMatch(
                                segment -> segment.getDepartureDate()
                                        .isBefore(LocalDateTime.now()
                                        )
                        ), "There is a departure in the past"
        );

        ListAssert<Flight> assertThatData   = assertThat(flights);
        ListAssert<Flight> assertThatResult = assertThat(result);
        int initialDataSize = flights.size();
        int expectedSizeDiff = initialDataSize - result.size();

        assertThatData
                .filteredOn(departureInThePastCondition)
                .isNotEmpty()
                .size().isEqualTo(expectedSizeDiff);

        assertThatResult
                .filteredOn(departureInThePastCondition
                )
                .isEmpty();
        assertThatResult
                .isNotEmpty()
                .size().isEqualTo(initialDataSize - expectedSizeDiff);
    }

    @Test
    public void getFlightsWithSegmentsWhereArrivalIsNotBeforeDeparture() {
        List<Flight> flights = FlightBuilder.createFlights();
        List<Flight> result = flightFilter.getFlightsWithSegmentsWhereArrivalIsNotBeforeDeparture(flights);

        ListAssert<Flight> assertList = assertThat(result);
        assertList
                .filteredOn(flight -> flight
                        .getSegments()
                        .stream()
                        .anyMatch(
                                segment -> segment.getArrivalDate()
                                        .isBefore(segment.getDepartureDate())
                        )
                )
                .isEmpty();
        assertList.isNotEmpty();
    }

    @Test
    public void getFlightsWhereTotalTimeBetweenAllTheSegmentsOnLandIsNotMoreThan2Hours() {
    }
}