package com.ventrek.bookingplatform.repository;

import com.ventrek.bookingplatform.domain.PackageDeliveryRequest;
import com.ventrek.bookingplatform.domain.PackageDeliveryRequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;

@Repository
public interface PackageDeliveryRequestRepository extends PagingAndSortingRepository<PackageDeliveryRequest, Long> {
    Page<PackageDeliveryRequest> findAllByStatusIsLikeOrStatusIsLike(PackageDeliveryRequestStatus status1, PackageDeliveryRequestStatus status2, Pageable pageable);
    Page<PackageDeliveryRequest> findAllByStatusIsLikeOrStatusIsLikeAndPickupDateIsAfter(PackageDeliveryRequestStatus status1, PackageDeliveryRequestStatus status2, LocalDate pickupDate, Pageable pageable);
    Page<PackageDeliveryRequest> findAllByStatusIsLikeAndPickupDateIsAfter(PackageDeliveryRequestStatus status, LocalDate pickupDate, Pageable pageable);
    Page<PackageDeliveryRequest> findAllByStatusIsLikeAndPickupDateIs(PackageDeliveryRequestStatus status, LocalDate pickupDate, Pageable pageable);
    Page<PackageDeliveryRequest> findAllByStatusIsLikeOrStatusIsLikeAndPickupDateIs(PackageDeliveryRequestStatus status1,  PackageDeliveryRequestStatus status2, LocalDate pickupDate, Pageable pageable);
    Page<PackageDeliveryRequest> findAllByStatusIs(PackageDeliveryRequestStatus status, Pageable pageable);
    Page<PackageDeliveryRequest> findAllByAllocated(boolean allocated, Pageable pageable);
    Page<PackageDeliveryRequest> findAllByStatusIsLikeOrStatusIsLikeOrStatusIsLikeAndPickupDateBefore(PackageDeliveryRequestStatus status1, PackageDeliveryRequestStatus status2, PackageDeliveryRequestStatus status3, LocalDate date, Pageable pageable);
}
