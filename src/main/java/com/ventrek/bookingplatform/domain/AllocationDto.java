package com.ventrek.bookingplatform.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AllocationDto {
    private Long id;
    private Vehicle vehicle;
    private Driver driver;
    private PackageDeliveryRequest packageDeliveryRequest;
}
