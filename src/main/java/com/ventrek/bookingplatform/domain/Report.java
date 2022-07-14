package com.ventrek.bookingplatform.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Report extends Trace {
    private List<Allocation> allocations;
    private double total;
}
