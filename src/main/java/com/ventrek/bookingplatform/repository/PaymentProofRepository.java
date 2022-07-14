package com.ventrek.bookingplatform.repository;

import com.ventrek.bookingplatform.domain.PaymentProof;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PaymentProofRepository extends PagingAndSortingRepository<PaymentProof, Long> {

}
