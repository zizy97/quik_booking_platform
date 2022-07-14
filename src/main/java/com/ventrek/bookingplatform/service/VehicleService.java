package com.ventrek.bookingplatform.service;

import com.ventrek.bookingplatform.domain.Driver;
import com.ventrek.bookingplatform.domain.Job;
import com.ventrek.bookingplatform.domain.Vehicle;
import com.ventrek.bookingplatform.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    public List<Vehicle> getVehicles(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Vehicle> pagedResult = vehicleRepository.findAll(paging);
        if (pagedResult.hasContent()){
            return pagedResult.getContent();
        }else {
            return new ArrayList<>();
        }
    }

    public Vehicle addVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public Vehicle updateVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public void deleteVehicle(Long id) {
        Vehicle vehicleById = findVehicleById(id);
        vehicleRepository.delete(vehicleById);
    }

    public Vehicle findVehicleById(Long id) {
        return vehicleRepository.findById(id).orElseThrow();
    }

}
