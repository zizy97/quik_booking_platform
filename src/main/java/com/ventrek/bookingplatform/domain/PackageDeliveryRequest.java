package com.ventrek.bookingplatform.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
public class PackageDeliveryRequest extends Trace{
    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "pickup_address")
    private String pickupAddress;

    @Column(name = "dropoff_address")
    private String dropOffAddress;

    @Column(name = "special_remarks")
    private String specialRemarks;

    @Column(name = "package_description")
    private String packageDescription;

    @Column(name = "weight")
    private double weight;

    @Column(name = "pickup_date")
    private LocalDate pickupDate;

    @Column(name = "pickup_time")
    private String pickupTime;

    @Column(name = "delivery_fee")
    private double deliveryFee;

    @Column(name = "paid_amount")
    private double paidAmount;

    @Column(name = "distance")
    private double distance;

    @Column(name = "allocated")
    private boolean allocated;

    @OneToOne(mappedBy = "packageDeliveryRequest", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private PaymentProof paymentProof;

    @Column(name="payment_proof_link_used")
    private boolean paymentProofLinkUsed = false;

    @OneToOne(mappedBy = "packageDeliveryRequest", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference
    private Allocation allocation;

    @Column(name = "status")
    private PackageDeliveryRequestStatus status;
}
