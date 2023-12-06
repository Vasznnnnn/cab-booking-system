package com.myapps.cabs.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.lang.NonNull;


import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@Table(name="USER_TRIP_REQUEST")
public class TripRequest {

    @Id
    @Column(name = "TRIP_ID")
    private String tripId;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "KERBEROS_ID")
    @NonNull
    private String kerberosId;

    @Column(name = "TYPE")
    @NonNull
    private String type;

    @Column(name = "TRIP_DATE")
    @NonNull
    private List<Date> tripDate;

    @Column(name = "PICKUP_TIME")
    @NonNull
    private Time pickUpTime;

    @Column(name = "STATUS")
    private String status;

    @Column(name ="REQUEST_CREATED_TIME")
    private Timestamp requestCreatedTime;

    @Column(name ="PICKUP_TYPE")
    private String pickUpType;

    @Column(name="DROPOFF_TYPE")
    private String dropOffType;

}
