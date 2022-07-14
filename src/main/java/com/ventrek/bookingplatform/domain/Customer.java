package com.ventrek.bookingplatform.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Customer extends Trace{
    @Column(name = "name")
    private String customerName;

    @Column(name = "phone")
    private String customerPhone;

    @Column(name = "email")
    private String customerEmail;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<PackageDeliveryRequest> packageDeliveryRequests;
}
