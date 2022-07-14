package com.ventrek.bookingplatform.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistanceMatrixElement {
    private DistanceMatrixData distance;
    private DistanceMatrixData duration;
    private String status;
}
