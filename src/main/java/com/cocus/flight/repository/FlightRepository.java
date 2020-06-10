package com.cocus.flight.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cocus.flight.model.Flight;

public interface FlightRepository extends JpaRepository<Flight, Long> {

}
