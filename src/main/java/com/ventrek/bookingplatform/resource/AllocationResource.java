package com.ventrek.bookingplatform.resource;

import com.ventrek.bookingplatform.domain.Allocation;
import com.ventrek.bookingplatform.domain.PackageDeliveryRequest;
import com.ventrek.bookingplatform.domain.PackageDeliveryRequestStatus;
import com.ventrek.bookingplatform.domain.Report;
import com.ventrek.bookingplatform.service.AllocationService;
import com.ventrek.bookingplatform.service.PackageService;
import com.ventrek.bookingplatform.utils.ReportPDFExporter;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
public class AllocationResource {

    @Autowired
    private AllocationService allocationService;

    @Autowired
    private PackageService packageService;

    @PostMapping("allocations")
    public ResponseEntity<Allocation> createAllocation(@RequestBody Allocation allocation) {
            PackageDeliveryRequest packageDeliveryRequestFromDB = packageService.getPackageDeliveryRequestById(allocation.getPackageDeliveryRequest().getId());
            packageDeliveryRequestFromDB.setAllocation(allocation);
            packageDeliveryRequestFromDB.setStatus(PackageDeliveryRequestStatus.DELIVERY_IN_PROGRESS);
            packageDeliveryRequestFromDB.setAllocated(true);
            Allocation createdAllocation = allocationService.createAllocation(allocation);
            packageService.updatePackageDeliveryRequest(packageDeliveryRequestFromDB);
            return ResponseEntity.status(HttpStatus.OK).body(allocationService.getAllocation(createdAllocation.getId()));
    }

    @PutMapping("allocations")
    public ResponseEntity<Allocation> updateAllocation(@RequestBody PackageDeliveryRequest packageDeliveryRequest, @RequestParam("duration") double duration) {
        Allocation allocation = allocationService.getAllocationByPackageDeliveryId(packageDeliveryRequest.getId());
        packageDeliveryRequest.setAllocation(allocation);
        packageDeliveryRequest.setStatus(PackageDeliveryRequestStatus.DELIVERY_COMPLETED);
        allocation.setDeliveryDuration(duration);
        allocationService.updateAllocation(allocation);
        packageService.updatePackageDeliveryRequest(packageDeliveryRequest);
        return ResponseEntity.status(HttpStatus.OK).body(allocationService.getAllocation(allocation.getId()));
    }

    @GetMapping("allocations")
    public ResponseEntity<List<Allocation>> getAllocations(@RequestParam(defaultValue = "0") Integer pageNo,
                                           @RequestParam(defaultValue = "10") Integer pageSize,
                                           @RequestParam(defaultValue = "id") String sortBy,
                                           @RequestParam(defaultValue = "1000-01-01") String from,
                                           @RequestParam(defaultValue = "1000-01-01") String to,
                                           @RequestParam(defaultValue = "") String driverName) {

        LocalDate fromDate = LocalDate.parse(from, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate toDate = LocalDate.parse(to, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        if(fromDate.getYear()!=1000 && toDate.getYear()!=1000 && !driverName.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(allocationService.getAllAllocations(pageNo, pageSize, sortBy, fromDate, toDate, driverName));
        }else if (fromDate.getYear()!=1000 && toDate.getYear()!=1000) {
            return ResponseEntity.status(HttpStatus.OK).body(allocationService.getAllAllocations(pageNo, pageSize, sortBy, fromDate, toDate, null));
        }else if (!driverName.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(allocationService.getAllAllocations(pageNo, pageSize, sortBy, null, null, driverName));
        }

        return ResponseEntity.status(HttpStatus.OK).body(allocationService.getAllAllocations(pageNo, pageSize, sortBy, null, null, null));
    }

    @GetMapping("report")
    public ResponseEntity<Report> getReportData(@RequestParam(defaultValue = "0") Integer pageNo,
                                                           @RequestParam(defaultValue = "10") Integer pageSize,
                                                           @RequestParam(defaultValue = "id") String sortBy,
                                                           @RequestParam(defaultValue = "1000-01-01") String from,
                                                           @RequestParam(defaultValue = "1000-01-01") String to,
                                                           @RequestParam(defaultValue = "") String driverName) {

        LocalDate fromDate = LocalDate.parse(from, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate toDate = LocalDate.parse(to, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        List<Allocation> allAllocations;
        if(fromDate.getYear()!=1000 && toDate.getYear()!=1000 && !driverName.isEmpty()) {
            allAllocations = allocationService.getAllAllocations(pageNo, pageSize, sortBy, fromDate, toDate, driverName);
        }else if (fromDate.getYear()!=1000 && toDate.getYear()!=1000) {
            allAllocations = allocationService.getAllAllocations(pageNo, pageSize, sortBy, fromDate, toDate, null);
        }else if (!driverName.isEmpty()) {
            allAllocations = allocationService.getAllAllocations(pageNo, pageSize, sortBy, null, null, driverName);
        }else{
            allAllocations = allocationService.getAllAllocations(pageNo, pageSize, sortBy, null, null, null);
        }

        double total = allAllocations.stream().mapToDouble(allocation -> allocation.getDeliveryDuration() * allocation.getDriver().getHourlyWage()).sum();

        Report report = new Report(allAllocations, total);

        return ResponseEntity.status(HttpStatus.OK).body(report);
    }

    @GetMapping("/report/export/pdf")
    public void exportToPDF(@RequestParam(defaultValue = "1000-01-01") String from,
                            @RequestParam(defaultValue = "1000-01-01") String to,
                            @RequestParam(defaultValue = "") String driverName, HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        LocalDate fromDate = LocalDate.parse(from, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate toDate = LocalDate.parse(to, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        List<Allocation> allAllocations;
        if(fromDate.getYear()!=1000 && toDate.getYear()!=1000 && !driverName.isEmpty()) {
            allAllocations = allocationService.getAllAllocationsWithoutPagination(fromDate, toDate, driverName);
        }else if (fromDate.getYear()!=1000 && toDate.getYear()!=1000) {
            allAllocations = allocationService.getAllAllocationsWithoutPagination(fromDate, toDate, null);
        }else if (!driverName.isEmpty()) {
            allAllocations = allocationService.getAllAllocationsWithoutPagination(null, null, driverName);
        }else{
            allAllocations = allocationService.getAllAllocationsWithoutPagination(null, null, null);
        }

        double total = allAllocations.stream().mapToDouble(allocation -> allocation.getDeliveryDuration() * allocation.getDriver().getHourlyWage()).sum();

        ReportPDFExporter exporter = new ReportPDFExporter(allAllocations, total);
        exporter.export(response);

    }


    @GetMapping("allocations/ongoing")
    public ResponseEntity<List<Allocation>> getOngoingAllocations(@RequestParam(defaultValue = "0") Integer pageNo,
                                                           @RequestParam(defaultValue = "10") Integer pageSize,
                                                           @RequestParam(defaultValue = "id") String sortBy,
                                                           @RequestParam() String date) {
        return ResponseEntity.status(HttpStatus.OK).body(allocationService.getAllocationsWithStatusAndDate(pageNo, pageSize, sortBy, PackageDeliveryRequestStatus.DELIVERY_IN_PROGRESS,date));
    }

    @GetMapping("allocations/upcoming")
    public ResponseEntity<List<Allocation>> getUpcomingAllocations(@RequestParam(defaultValue = "0") Integer pageNo,
                                                                  @RequestParam(defaultValue = "10") Integer pageSize,
                                                                  @RequestParam(defaultValue = "id") String sortBy,
                                                                  @RequestParam() String todayDate) {
        return ResponseEntity.status(HttpStatus.OK).body(allocationService.getAllocationsWithStatusAndNotDate(pageNo, pageSize, sortBy, PackageDeliveryRequestStatus.DELIVERY_IN_PROGRESS,todayDate));
    }

    @GetMapping("allocations/status")
    public ResponseEntity<List<Allocation>> getAllocationsByStatus(@RequestParam(defaultValue = "0") Integer pageNo,
                                                                  @RequestParam(defaultValue = "10") Integer pageSize,
                                                                  @RequestParam(defaultValue = "id") String sortBy,
                                                                   @RequestParam() PackageDeliveryRequestStatus status) {
        return ResponseEntity.status(HttpStatus.OK).body(allocationService.getAllocationsWithStatus(pageNo, pageSize, sortBy, status));
    }

    @GetMapping("allocations/{id}")
    public ResponseEntity<Allocation> getAllocation(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body((allocationService.getAllocation(id)));
    }
}
