package com.myapps.cabs.service;

import com.myapps.cabs.model.TripRequest;
import com.myapps.cabs.model.User;
import com.myapps.cabs.repository.TripRepository;
import com.myapps.cabs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static com.myapps.cabs.constants.Constants.*;

@Repository
public class TripServiceImpl implements TripService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TripRepository tripRepository;


    @Override
    public boolean addTripRequestForUser(TripRequest tripRequest) {
        try {
            long ticker = 0;
            User userObj = userRepository.findByKerberosId(tripRequest.getKerberosId());
            checkIsValidDate(tripRequest.getTripDate());
            LocalTime limit = Time.valueOf("12:00:00").toLocalTime();
            LocalTime pickupTime = tripRequest.getPickUpTime().toLocalTime();
            ticker = ChronoUnit.HOURS.between(pickupTime, limit);
            if (ticker > 6) {
                validateUserPickup(tripRequest.getPickUpType(), HOME, tripRequest);
            } else {
                validateUserPickup(tripRequest.getPickUpType(), STANDARD_PICKUP, tripRequest);
            }
            tripRequest.setTripId(UUID.randomUUID().toString());
            tripRequest.setUserId(userObj.getId());
            tripRequest.setStatus(IN_PROGRESS);

            tripRepository.save(tripRequest);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private boolean checkIsValidDate(List<Date> tripDate) {
        long span = 0;
        if (tripDate.size() > 1) {
            Date start = tripDate.get(0);
            Date end = tripDate.get(tripDate.size() - 1);

            LocalDate startDate = start.toLocalDate();
            LocalDate endDate = end.toLocalDate();
            span = ChronoUnit.DAYS.between(startDate, endDate);

            return span < 31;
        }
        return true;
    }

    private void validateUserPickup(String type, String pickup, TripRequest tripRequest) {
        if (type.equalsIgnoreCase(PICKUP)) {
            tripRequest.setPickUpType(pickup);
            tripRequest.setDropOffType(com.myapps.cabs.constants.Constants.OFFICE);
        } else {
            tripRequest.setPickUpType(pickup);
            tripRequest.setDropOffType(com.myapps.cabs.constants.Constants.OFFICE);
        }
    }
}