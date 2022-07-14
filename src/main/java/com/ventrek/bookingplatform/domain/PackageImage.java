package com.ventrek.bookingplatform.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PackageImage extends Trace {
    private String imagePath;
    private String imageFileName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "package_delivery_request_id", nullable = false)
    @JsonBackReference
    private PackageDeliveryRequest packageDeliveryRequest;
}
