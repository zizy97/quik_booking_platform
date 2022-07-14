package com.ventrek.bookingplatform.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
public class Vehicle extends Trace {

    @Column(name = "registration_number")
    private String registrationNumber;

    @Column(name = "model")
    private String model;

    @Column(name = "type")
    private VehicleType type;

    @Column(name = "current_allocation_status")
    private boolean currentAllocationStatus;

    @Column(name = "description")
    private String vehicleDescription;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Allocation> allocations;

}
