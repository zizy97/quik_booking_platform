package com.ventrek.bookingplatform.service;

import com.ventrek.bookingplatform.domain.Allocation;
import com.ventrek.bookingplatform.domain.AllocationFilter;
import com.ventrek.bookingplatform.domain.PackageDeliveryRequest;
import com.ventrek.bookingplatform.domain.PackageDeliveryRequestStatus;
import com.ventrek.bookingplatform.repository.AllocationRepository;
import com.ventrek.bookingplatform.repository.PackageDeliveryRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AllocationService {

    @Autowired
    private AllocationRepository allocationRepository;

    @Autowired
    private PackageDeliveryRequestRepository packageDeliveryRequestRepository;

    public Allocation createAllocation(Allocation allocation) {
        return allocationRepository.save(allocation);
    }

    public List<Allocation> getAllAllocations(Integer pageNo, Integer pageSize, String sortBy, LocalDate fromDate, LocalDate toDate, String driverName) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Allocation> pagedResult;
        if (fromDate!=null && toDate !=null && driverName!=null) {
            pagedResult = allocationRepository.findAllByDeliveryDateBetweenAndDriver_Name(fromDate, toDate, driverName, paging);
        }else if (fromDate!=null && toDate !=null) {
            pagedResult = allocationRepository.findAllByDeliveryDateBetween(fromDate, toDate, paging);
        } else if (driverName!=null) {
            pagedResult = allocationRepository.getAllByDriver_Name(driverName, paging);
        }else {
            pagedResult = allocationRepository.findAll(paging);
        }
        if (pagedResult.hasContent()){
            return pagedResult.getContent();
        }else {
            return new ArrayList<>();
        }
    }

    public List<Allocation> getAllAllocationsWithoutPagination(LocalDate fromDate, LocalDate toDate, String driverName) {
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
        Page<Allocation> allocations;
        if (fromDate!=null && toDate !=null && driverName!=null) {
            allocations = allocationRepository.findAllByDeliveryDateBetweenAndDriver_Name(fromDate, toDate, driverName, pageable);
        }else if (fromDate!=null && toDate !=null) {
            allocations = allocationRepository.findAllByDeliveryDateBetween(fromDate, toDate, pageable);
        } else if (driverName!=null) {
            allocations = allocationRepository.getAllByDriver_Name(driverName, pageable);
        }else {
            List<Allocation> allocationList = new ArrayList<>();
            allocationRepository.findAll().iterator().forEachRemaining(allocationList::add);
            return allocationList;
        }

        return allocations.getContent();

    }

    public List<Allocation> getAllocationsWithStatusAndNotDate(Integer pageNo, Integer pageSize, String sortBy, PackageDeliveryRequestStatus status, String date) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Allocation> pagedResult = allocationRepository.getAllocationByPackageDeliveryRequestStatusAndPackageDeliveryRequestPickupDateNot(status, date, paging);
        if (pagedResult.hasContent()){
            return pagedResult.getContent();
        }else {
            return new ArrayList<>();
        }
    }

    public List<Allocation> getAllocationsWithStatusAndDate(Integer pageNo, Integer pageSize, String sortBy, PackageDeliveryRequestStatus status, String date) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Allocation> pagedResult = allocationRepository.getAllocationByPackageDeliveryRequestStatusAndPackageDeliveryRequestPickupDate(status, date, paging);
        if (pagedResult.hasContent()){
            return pagedResult.getContent();
        }else {
            return new ArrayList<>();
        }
    }

    public List<Allocation> getAllocationsWithStatus(Integer pageNo, Integer pageSize, String sortBy, PackageDeliveryRequestStatus status) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Allocation> pagedResult = allocationRepository.getAllocationByPackageDeliveryRequestStatus(status, paging);
        if (pagedResult.hasContent()){
            return pagedResult.getContent();
        }else {
            return new ArrayList<>();
        }
    }

    public Allocation getAllocation(Long id) {
        return allocationRepository.findById(id).orElse(null);
    }

    public Allocation getAllocationByPackageDeliveryId(Long id){
        return allocationRepository.getAllocationByPackageDeliveryRequestId(id);
    }

    public Allocation updateAllocation(Allocation allocation) {
        return allocationRepository.save(allocation);
    }
}
