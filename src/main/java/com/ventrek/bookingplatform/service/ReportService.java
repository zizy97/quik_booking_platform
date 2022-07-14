package com.ventrek.bookingplatform.service;

import com.ventrek.bookingplatform.domain.Allocation;
import com.ventrek.bookingplatform.repository.AllocationRepository;
import com.ventrek.bookingplatform.repository.PackageDeliveryRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    @Autowired
    private PackageDeliveryRequestRepository packageDeliveryRequestRepository;

    @Autowired
    private AllocationRepository allocationRepository;

    public void getDeliveryCashReport(Pageable pageable) {
        Page<Allocation> allAllocations = allocationRepository.findAll(pageable);
//        allAllocations.
    }
}

