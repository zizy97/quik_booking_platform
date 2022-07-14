package com.ventrek.bookingplatform.repository;

import com.ventrek.bookingplatform.domain.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriverRepository extends PagingAndSortingRepository<Driver, Long> {
}
