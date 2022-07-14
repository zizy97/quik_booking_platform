package com.ventrek.bookingplatform.service;

import com.ventrek.bookingplatform.domain.*;
import com.ventrek.bookingplatform.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PackageService {
    @Autowired
    private PackageDeliveryRequestRepository packageDeliveryRequestRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PaymentProofRepository paymentProofRepository;

    @Autowired
    private PackageImageRepository packageImageRepository;




    public List<PackageDeliveryRequest> getPackageDeliveryRequests(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<PackageDeliveryRequest> pagedResult = packageDeliveryRequestRepository.findAll(paging);
        if (pagedResult.hasContent()){
            return pagedResult.getContent();
        }else {
            return new ArrayList<>();
        }
    }

    public List<PackageDeliveryRequest> getDeliveryRequests(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<PackageDeliveryRequest> pagedResult = packageDeliveryRequestRepository.findAllByStatusIsLikeOrStatusIsLike(PackageDeliveryRequestStatus.PAYMENT_PENDING, PackageDeliveryRequestStatus.ADMIN_APPROVAL_PENDING ,paging);
        if (pagedResult.hasContent()){
            return pagedResult.getContent();
        }else {
            return new ArrayList<>();
        }
    }

    public List<PackageDeliveryRequest> getUpcomingDeliveries(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<PackageDeliveryRequest> pagedResult = packageDeliveryRequestRepository.findAllByStatusIsLikeOrStatusIsLikeAndPickupDateIsAfter(PackageDeliveryRequestStatus.ADMIN_APPROVED, PackageDeliveryRequestStatus.DELIVERY_IN_PROGRESS, LocalDate.now(), paging);
        if (pagedResult.hasContent()){
            return pagedResult.getContent();
        }else {
            return new ArrayList<>();
        }
    }

    public List<PackageDeliveryRequest> getOngoingDeliveries(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<PackageDeliveryRequest> pagedResult = packageDeliveryRequestRepository.findAllByStatusIsLikeOrStatusIsLikeAndPickupDateIs(PackageDeliveryRequestStatus.ADMIN_APPROVED, PackageDeliveryRequestStatus.DELIVERY_IN_PROGRESS, LocalDate.now(), paging);
        if (pagedResult.hasContent()){
            return pagedResult.getContent();
        }else {
            return new ArrayList<>();
        }
    }

    public List<PackageDeliveryRequest> getCompletedDeliveries(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<PackageDeliveryRequest> pagedResult = packageDeliveryRequestRepository.findAllByStatusIs(PackageDeliveryRequestStatus.DELIVERY_COMPLETED, paging);
        if (pagedResult.hasContent()){
            return pagedResult.getContent();
        }else {
            return new ArrayList<>();
        }
    }

    public List<PackageDeliveryRequest> getRejectedDeliveries(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<PackageDeliveryRequest> pagedResult = packageDeliveryRequestRepository.findAllByStatusIs(PackageDeliveryRequestStatus.ADMIN_REJECTED, paging);
        if (pagedResult.hasContent()){
            return pagedResult.getContent();
        }else {
            return new ArrayList<>();
        }
    }

    public List<PackageDeliveryRequest> getNotAllocatedDeliveries(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<PackageDeliveryRequest> pagedResult = packageDeliveryRequestRepository.findAllByAllocated(false, paging);
        if (pagedResult.hasContent()){
            return pagedResult.getContent();
        }else {
            return new ArrayList<>();
        }
    }

    public List<PackageDeliveryRequest> getExpiredDeliveries(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<PackageDeliveryRequest> pagedResult = packageDeliveryRequestRepository.findAllByStatusIsLikeOrStatusIsLikeOrStatusIsLikeAndPickupDateBefore(PackageDeliveryRequestStatus.PAYMENT_PENDING, PackageDeliveryRequestStatus.ADMIN_APPROVAL_PENDING, PackageDeliveryRequestStatus.DELIVERY_IN_PROGRESS, LocalDate.now(),  paging);
        if (pagedResult.hasContent()){
            return pagedResult.getContent();
        }else {
            return new ArrayList<>();
        }
    }

    public PackageDeliveryRequest getPackageDeliveryRequestById(Long id){
        return packageDeliveryRequestRepository.findById(id).get();
    }

    public PackageDeliveryRequest updatePackageStatus(Long id, PackageDeliveryRequestStatus status) {
        PackageDeliveryRequest packageDeliveryRequest = packageDeliveryRequestRepository.findById(id).orElseThrow();
        packageDeliveryRequest.setStatus(status);
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("kanishka.aws.02@gmail.com");
        simpleMailMessage.setTo(packageDeliveryRequest.getCustomer().getCustomerEmail());
        simpleMailMessage.setSubject("Delivery Request Accepted");
        simpleMailMessage.setText("Hello "+packageDeliveryRequest.getCustomer().getCustomerName()+",\nYour package have been accepted and delivery will began soon.\nThank you, Best regards.");

        return packageDeliveryRequestRepository.save(packageDeliveryRequest);
    }

    public PackageDeliveryRequest createPackageDeliveryRequest(PackageDeliveryRequest deliveryRequest) {
        Customer customer = deliveryRequest.getCustomer();
        customerRepository.save(customer);
        return packageDeliveryRequestRepository.save(deliveryRequest);
    }

    public PackageImage createPackageImageUpload(PackageImage packageImage) {
        return packageImageRepository.save(packageImage);
    }

    public void createPaymentProof(PaymentProof paymentProof) {
        paymentProofRepository.save(paymentProof);
    }

    public PaymentProof getPaymentProof(Long id) {
        return paymentProofRepository.findById(id).orElseThrow();
    }

    public PackageDeliveryRequest updatePackageDeliveryRequest(PackageDeliveryRequest packageDeliveryRequest) {
        return packageDeliveryRequestRepository.save(packageDeliveryRequest);
    }

    public PackageDeliveryRequest updatePackagePaidAmount(Long id, double paidAmount) {
        PackageDeliveryRequest packageDeliveryRequest = packageDeliveryRequestRepository.findById(id).orElseThrow();
        packageDeliveryRequest.setPaidAmount(paidAmount);
        return packageDeliveryRequestRepository.save(packageDeliveryRequest);
    }
}
