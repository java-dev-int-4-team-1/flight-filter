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

    private void makeAssertions(List<Flight> flights, List<Flight> result, Condition<Flight> condition) {

        ListAssert<Flight> assertThatData   = assertThat(flights);
        ListAssert<Flight> assertThatResult = assertThat(result);
        int initialDataSize = flights.size();
        int expectedSizeDiff = initialDataSize - result.size();

        assertThatData
                .filteredOn(condition)
                .isNotEmpty()
                .size().isEqualTo(expectedSizeDiff);

        assertThatResult
                .filteredOn(condition
                )
                .isEmpty();
        assertThatResult
                .isNotEmpty()
                .size().isEqualTo(initialDataSize - expectedSizeDiff);
    }

    private void makeAssertions(List<Flight> flights, List<Flight> result, List<Flight> expectedBeingRemoved) {

        ListAssert<Flight> assertThatResult = assertThat(result);
        int resultSize = flights.size() - expectedBeingRemoved.size();

        assertThatResult
                .hasSize(resultSize)
                .doesNotContainAnyElementsOf(expectedBeingRemoved);
    }

    @Test
    public void getFlightsWithAnySegmentWithDepartureNotInThePast() {
        List<Flight> flights = FlightBuilder.createFlights();
        List<Flight> expectedBeingRemoved = FlightBuilder.getFlightsWithDepartureInThePast();
        List<Flight> result = flightFilter.getFlightsWithAnySegmentWithDepartureNotInThePast(flights);

        Condition<Flight> departureInThePastCondition = new Condition<>(
                flight -> flight
                        .getSegments()
                        .stream()
                        .anyMatch(
                                segment -> segment.getDepartureDate()
                                        .isBefore(LocalDateTime.now()
                                        )
                        ), "There is a departure in the past for some segments"
        );

        makeAssertions(flights, result, departureInThePastCondition);
        makeAssertions(flights, result, expectedBeingRemoved);

    }

    @Test
    public void getFlightsWithSegmentsWithArrivalIsNotBeforeDeparture() {
        List<Flight> flights = FlightBuilder.createFlights();
        List<Flight> expectedBeingRemoved = FlightBuilder.getFlightsWithArrivalBeforeDeparture();
        List<Flight> result = flightFilter.getFlightsWithSegmentsWithArrivalIsNotBeforeDeparture(flights);

        Condition<Flight> arrivalIsBeforeDepartureCondition = new Condition<>(
                flight -> flight
                        .getSegments()
                        .stream()
                        .anyMatch(
                                segment -> segment.getArrivalDate()
                                        .isBefore(segment.getDepartureDate())
                        )
                , "There is an arrival before the departure in some segments"
        );

        makeAssertions(flights, result, arrivalIsBeforeDepartureCondition);
        makeAssertions(flights, result, expectedBeingRemoved);

    }

    @Test
    public void getFlightsWithGroundTimeIsNotMoreThan2Hours() {
        List<Flight> expectedBeingRemoved = FlightBuilder.getFlightsWithMoreThatTwoHoursGroundTime();
        List<Flight> flights = FlightBuilder.createFlights();
        List<Flight> result = flightFilter.getFlightsWithGroundTimeIsNotMoreThan2Hours(flights);

        makeAssertions(flights, result, expectedBeingRemoved);
    }
}