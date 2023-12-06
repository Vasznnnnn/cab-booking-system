package com.myapps.cabs.repository;

import com.myapps.cabs.model.TripRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRepository extends JpaRepository<TripRequest, String> {
}
