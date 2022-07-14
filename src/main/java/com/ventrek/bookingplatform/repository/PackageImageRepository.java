package com.ventrek.bookingplatform.repository;


import com.ventrek.bookingplatform.domain.PackageImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PackageImageRepository extends PagingAndSortingRepository<PackageImage, Long> {
}
