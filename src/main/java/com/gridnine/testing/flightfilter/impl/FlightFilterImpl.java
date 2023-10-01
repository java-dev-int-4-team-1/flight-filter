package com.gridnine.testing.flightfilter.impl;

import com.gridnine.testing.flight.Flight;
import com.gridnine.testing.flight.Segment;
import com.gridnine.testing.flightfilter.FlightFilter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FlightFilterImpl implements FlightFilter {

    @Override
    public List<Flight> getFlightsWithAnySegmentWhereDepartureAfterNow(List<Flight> flights) {
        return flights
                .stream()
                .filter(
                        flight -> flight.getSegments()
                                .stream()
                                .anyMatch(segment -> segment.getDepartureDate()
                                        .isAfter(LocalDateTime.now())
                                )
                ).
                collect(Collectors.toList()
                );
    }

    @Override
    public List<Flight> getFlightsWithSegmentsWhereArrivalIsNotBeforeDeparture(List<Flight> flights) {
        return flights
                .stream()
                .filter(
                        flight -> flight.getSegments()
                                .stream()
                                .noneMatch(segment -> segment.getArrivalDate()
                                        .isBefore(segment.getDepartureDate())
                                )
                ).collect(Collectors.toList());
    }

    @Override
    public List<Flight> getFlightsWhereTotalTimeBetweenAllTheSegmentsOnLandIsNotMoreThan2Hours(List<Flight> flights) {
        return flights
            .stream()
            .filter(FlightFilterImpl::doesNotHaveTotalTimeBetweenAllTheSegmentsOnLandMoreThan2Hours)
            .collect(Collectors.toList()
        );
    }

    private static boolean doesNotHaveTotalTimeBetweenAllTheSegmentsOnLandMoreThan2Hours(Flight flight) {
        //Sort the segments
        Stream<Segment> segmentsSorted = flight.getSegments()
                .stream()
                .sorted();

        //Iterate through the sorted segments
        Iterator<Segment> segmentIterator = segmentsSorted.iterator();
        if (!segmentIterator.hasNext()) {
            return true;
        }

        long totalTimeOnLandSeconds = 0L;
        Segment segment = segmentIterator.next();
        while (segmentIterator.hasNext()) {
            Segment nextSegment = segmentIterator.next();
            totalTimeOnLandSeconds +=
                nextSegment.getDepartureDate().toEpochSecond(ZoneOffset.UTC)
                    - segment.getArrivalDate().toEpochSecond(ZoneOffset.UTC);
                if(totalTimeOnLandSeconds > 2 * 3_600) {
                return false;
            }
            segment = nextSegment;
        }
        return true;
    }
}
