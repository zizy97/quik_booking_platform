package com.ventrek.bookingplatform.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Driver extends Trace {

    @Column(name = "driver_number")
    private String driverNumber;
    @Column(name = "name")
    private String name;
    @Column(name = "address")
    private String address;
    @Column(name = "email")
    private String email;
    @Column(name = "telephone")
    private String telephoneNumber;
    @Column(name = "license")
    private String license;
    @Column(name = "emergency_contact_number")
    private String emergencyContactNumber;
    @Column(name = "other_details")
    private String otherDetails;
    @Column(name = "hourly_wage")
    private double hourlyWage;

    @Column(name = "current_allocation_status")
    private boolean currentAllocationStatus;

    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Allocation> allocations;

}
