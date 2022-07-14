package com.ventrek.bookingplatform.service;

import com.ventrek.bookingplatform.domain.Driver;
import com.ventrek.bookingplatform.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DriverService {

    @Autowired
    private DriverRepository driverRepository;

    public List<Driver> getDrivers(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Driver> pagedResult = driverRepository.findAll(paging);
        if (pagedResult.hasContent()){
            return pagedResult.getContent();
        }else {
            return new ArrayList<>();
        }
    }

    public Driver addDriver(Driver driver) {
        return driverRepository.save(driver);
    }

    public Driver updateDriver(Driver driver) {
        return driverRepository.save(driver);
    }

    public Driver findDriverById(Long id) {
        return driverRepository.findById(id).orElseThrow();
    }

    public void deleteDriverById(Long id) {
        Driver byId = driverRepository.findById(id).orElseThrow();
        driverRepository.delete(byId);
    }

}
