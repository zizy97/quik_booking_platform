package com.ventrek.bookingplatform.domain;

import lombok.Data;

@Data
public class DistanceMatrix {
    private String[] destination_addresses;
    private String[] origin_addresses;
    private DistanceMatrixRow[] rows;
    private String status;

}
