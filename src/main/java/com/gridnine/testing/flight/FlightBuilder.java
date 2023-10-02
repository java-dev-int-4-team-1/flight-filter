
package com.gridnine.testing.flight;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Factory class to get sample list of flights.
 */
public class FlightBuilder {
    private static List<Flight> createdFlights;
    final private static List<Flight> flightsWithDepartureInThePast = new ArrayList<>();
    final private static List<Flight> flightsWithArrivalBeforeDeparture = new ArrayList<>();
    final private static List<Flight> flightsWithMoreThanTwoHoursGroundTime = new ArrayList<>();

    private static List<Flight> checkAndReturn(List<Flight> someFlights) {
        if (someFlights.isEmpty()) {
            createFlights();
        }
        return someFlights;
    }

    public static List<Flight> getCreatedFlights() {
        return (createdFlights == null)? createFlights() : createdFlights;
    }
    public static List<Flight> getFlightsWithDepartureInThePast() {
        return checkAndReturn(flightsWithDepartureInThePast);
    }

    public static List<Flight> getFlightsWithArrivalBeforeDeparture() {
        return checkAndReturn(flightsWithArrivalBeforeDeparture);
    }
    public static List<Flight> getFlightsWithMoreThatTwoHoursGroundTime() {
        return checkAndReturn(flightsWithMoreThanTwoHoursGroundTime);
    }
    public static List<Flight> createFlights() {
        LocalDateTime threeDaysFromNow = LocalDateTime.now().plusDays(3);

        flightsWithDepartureInThePast.clear();
        flightsWithArrivalBeforeDeparture.clear();
        flightsWithMoreThanTwoHoursGroundTime.clear();

        return createdFlights = Arrays.asList(
                //A normal flight with two hour duration
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2)),
                //A normal multi segment flight
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                        threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(5)),
                //A flight departing in the past
                addToListOfFlightsWithDepartureInThePast(
                        createFlight(threeDaysFromNow.minusDays(6), threeDaysFromNow)
                ),
                //A flight that departs before it arrives
                addToListOfFlightsWithArrivalBeforeDeparture(
                        createFlight(threeDaysFromNow, threeDaysFromNow.minusHours(6))
                ),
                //A flight with more than two hours ground time
                addToListOfFlightsWithMoreThanTwoHoursGroundTIme(
                    createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                            threeDaysFromNow.plusHours(5), threeDaysFromNow.plusHours(6))
                ),
                //Another flight with more than two hours ground time
                addToListOfFlightsWithMoreThanTwoHoursGroundTIme(
                        createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                            threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(4),
                            threeDaysFromNow.plusHours(6), threeDaysFromNow.plusHours(7)))
                );
    }

    private static Flight addToListOfFlightsWithDepartureInThePast(Flight flight) {
        flightsWithDepartureInThePast.add(flight);
        return flight;
    }

    private static Flight addToListOfFlightsWithArrivalBeforeDeparture(Flight flight) {
        flightsWithArrivalBeforeDeparture.add(flight);
        return flight;
    }
    private static Flight addToListOfFlightsWithMoreThanTwoHoursGroundTIme(Flight flight) {
        flightsWithMoreThanTwoHoursGroundTime.add(flight);
        return flight;
    }

    private static Flight createFlight(final LocalDateTime... dates) {
        if ((dates.length % 2) != 0) {
            throw new IllegalArgumentException(
                    "you must pass an even number of dates");
        }
        List<Segment> segments = new ArrayList<>(dates.length / 2);
        for (int i = 0; i < (dates.length - 1); i += 2) {
            segments.add(new Segment(dates[i], dates[i + 1]));
        }
        return new Flight(segments);
    }
}

