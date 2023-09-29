package com.gridnine.testing;

import javafx.util.Pair;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    static List<Flight> flightList = FlightBuilder.createFlights();

    public static void main(String[] args) {
        showFlights();
        showFlights(getFlightsWithSegmentsWhereDepartureAfterNow());
        showFlights(getFlightsWithSegmentsWhereArrivalDateIsBeforeDepartureDate());
        showFlights(getFlightsWithSegmentsWhereTimeBetweenSegmentsIsMoreThan2Hours());
    }

    private static void showFlights() {
        showFlights(new Pair<>("(all the flights)", flightList));
    }
    private static void showFlights(Pair<String, List<Flight>> flightsDisplay) {
        final String DELIMITER = "====================================================================================";
        System.out.println(DELIMITER);
        System.out.println("FLIGHTS with the segments");
        System.out.println(flightsDisplay.getKey());
        System.out.println(DELIMITER);
        flightsDisplay.getValue().forEach(System.out::println);
        System.out.println(DELIMITER);
        System.out.println();
    }

    private static Pair<String, List<Flight>> getFlightsWithSegmentsWhereDepartureAfterNow() {
        return  new Pair<>(
               "where departure is after now",
               flightList
                       .stream()
                       .filter(
                               flight->flight.getSegments()
                                       .stream()
                                       .anyMatch(segment -> segment.getDepartureDate()
                                               .isAfter(LocalDateTime.now())
                                       )
                       ).
                       collect(Collectors.toList())
        );
    }

    private static Pair<String, List<Flight>> getFlightsWithSegmentsWhereArrivalDateIsBeforeDepartureDate() {
        return  new Pair<>(
               "where segments have the arrival date before departure date date",
               flightList
                       .stream()
                       .filter(
                               flight->flight.getSegments()
                                       .stream()
                                       .anyMatch(segment -> segment.getArrivalDate()
                                               .isBefore(segment.getDepartureDate())
                                       )
                       ).
                       collect(Collectors.toList())
        );
    }

    private static Pair<String, List<Flight>> getFlightsWithSegmentsWhereTimeBetweenSegmentsIsMoreThan2Hours() {
        return  new Pair<>(
               "where the time on land between any two segments is more than 2 hours",

               flightList
                       .stream()
                       .filter(Main::hasTimeOnLandBetweenAny2SegmentsThatIsMoreThat2Hours
                       ).
                       collect(Collectors.toList())
        );
    }

    private static boolean hasTimeOnLandBetweenAny2SegmentsThatIsMoreThat2Hours(Flight flight) {
        Iterator<Segment> segmentIterator = flight.getSegments().iterator();
        if(!segmentIterator.hasNext()) {
            return false;
        }

        Segment segment = segmentIterator.next();
        while (segmentIterator.hasNext()) {
            Segment nextSegment = segmentIterator.next();
            if(nextSegment.getDepartureDate().toEpochSecond(ZoneOffset.UTC)
                    - segment.getArrivalDate().toEpochSecond(ZoneOffset.UTC)
                    > 2 * 3_600) {
                return true;
            }
            segment = nextSegment;
        }
        return false;
    }

}
