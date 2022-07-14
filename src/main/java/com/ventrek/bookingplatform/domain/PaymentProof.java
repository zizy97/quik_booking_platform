package com.ventrek.bookingplatform.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentProof extends Trace{
    private String note;
    private String imagePath;
    private String imageFileName;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "package_delivery_request_id", nullable = false)
    @JsonBackReference
    private PackageDeliveryRequest packageDeliveryRequest;
}
