package com.ventrek.bookingplatform.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllocationFilter {
    private String date;
    private PackageDeliveryRequestStatus status;
    private String driverName;
    private String vehicleRegistrationNumber;
}
