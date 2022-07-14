package com.ventrek.bookingplatform.repository;

import com.ventrek.bookingplatform.domain.Allocation;
import com.ventrek.bookingplatform.domain.PackageDeliveryRequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;
import java.util.List;

public interface AllocationRepository extends PagingAndSortingRepository<Allocation, Long> {
    Page<Allocation> getAllocationByPackageDeliveryRequestStatusAndPackageDeliveryRequestPickupDate(PackageDeliveryRequestStatus packageDeliveryRequestStatus, String packageDeliveryRequestPickupDate, Pageable pageable);

    Page<Allocation> getAllocationByPackageDeliveryRequestStatusAndPackageDeliveryRequestPickupDateNot(PackageDeliveryRequestStatus packageDeliveryRequestStatus, String packageDeliveryRequestPickupDate, Pageable pageable);

    Page<Allocation> getAllocationByPackageDeliveryRequestStatus(PackageDeliveryRequestStatus packageDeliveryRequestStatus, Pageable pageable);

    Allocation getAllocationByPackageDeliveryRequestId(Long id);

    Page<Allocation> getAllByDriver_Name(String driverName, Pageable pageable);

    Page<Allocation> findAllByDeliveryDateBetween(LocalDate deliveryDate1, LocalDate deliveryDate2, Pageable pageable);

    Page<Allocation> findAllByDeliveryDateBetweenAndDriver_Name(LocalDate deliveryDate1, LocalDate deliveryDate2, String driverName, Pageable pageable);

//    Page<Allocation> getAllByDriver_Name(String driverName);
//
//    Page<Allocation> findAllByDeliveryDateBetween(LocalDate deliveryDate1, LocalDate deliveryDate2);
//
//    Page<Allocation> findAllByDeliveryDateBetweenAndDriver_Name(LocalDate deliveryDate1, LocalDate deliveryDate2, String driverName);
}
