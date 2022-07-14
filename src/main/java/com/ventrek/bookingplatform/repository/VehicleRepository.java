package com.ventrek.bookingplatform.repository;

import com.ventrek.bookingplatform.domain.Job;
import com.ventrek.bookingplatform.domain.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends PagingAndSortingRepository<Vehicle, Long> {
}
