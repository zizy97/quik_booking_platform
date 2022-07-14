package com.ventrek.bookingplatform.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Allocation extends Trace {
    @ManyToOne()
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @ManyToOne()
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    private PackageDeliveryRequestStatus status;

    @Column(name = "delivery_date")
    private LocalDate deliveryDate;

    @Column(name = "delivery_duration")
    private double deliveryDuration = 0.0;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "package_delivery_id", nullable = false)
    @JsonManagedReference
    private PackageDeliveryRequest packageDeliveryRequest;

}
