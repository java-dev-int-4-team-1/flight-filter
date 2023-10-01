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

    List<Flight> flights = FlightBuilder.createFlights();

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

    @Test
    public void getFlightsWithAnySegmentWhereDepartureAfterNow() {
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

        makeAssertions(flights, result, departureInThePastCondition);

    }

    @Test
    public void getFlightsWithSegmentsWhereArrivalIsNotBeforeDeparture() {
        List<Flight> result = flightFilter.getFlightsWithSegmentsWhereArrivalIsNotBeforeDeparture(flights);

        Condition<Flight> arrivalIsBeforeDepartureCondition = new Condition<>(
                flight -> flight
                        .getSegments()
                        .stream()
                        .anyMatch(
                                segment -> segment.getArrivalDate()
                                        .isBefore(segment.getDepartureDate())
                        )
                , "There is an arrival before the departure"
        );

        makeAssertions(flights, result, arrivalIsBeforeDepartureCondition);

    }

    @Test
    public void getFlightsWhereTotalTimeBetweenAllTheSegmentsOnLandIsNotMoreThan2Hours() {
        //List<Flight> result = flightFilter.getFlightsWhereTotalTimeBetweenAllTheSegmentsOnLandIsNotMoreThan2Hours(flights);



/*        Condition<Flight> totalTimeOnLandIsMoreThan2HoursCondition = new Condition<>(
                flight -> flight
                        .getSegments()
                        .stream()
                        .sorted()
                        .reduce(0, (subtotal, segment) -> subtotal + segment.);)flatMapToLong().anyMatch(
                                segment -> segment.getArrivalDate()
                                        .isBefore(segment.getDepartureDate())
                        )
                , "There is an arrival before the departure"
        );

        makeAssertions(flights, result, totalTimeOnLandIsMoreThan2HoursCondition);
*/
    }
}