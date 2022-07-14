package com.ventrek.bookingplatform.resource;

import com.ventrek.bookingplatform.domain.Driver;
import com.ventrek.bookingplatform.domain.Job;
import com.ventrek.bookingplatform.domain.Vehicle;
import com.ventrek.bookingplatform.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
public class VehicleResource {

    @Autowired
    private VehicleService vehicleService;

    @GetMapping("vehicles")
    public ResponseEntity<List<Vehicle>> getVehicles(@RequestParam(defaultValue = "0") Integer pageNo,
                                                     @RequestParam(defaultValue = "10") Integer pageSize,
                                                     @RequestParam(defaultValue = "id") String sortBy) {
        return ResponseEntity.status(HttpStatus.OK).body(vehicleService.getVehicles(pageNo, pageSize, sortBy));
    }

    @PostMapping("vehicles")
    public Vehicle addVehicle(@RequestBody Vehicle vehicle) {
        return vehicleService.addVehicle(vehicle);
    }

    @PutMapping("vehicles")
    public Vehicle updateVehicle(@RequestBody Vehicle vehicle) {
        return vehicleService.updateVehicle(vehicle);
    }

    @GetMapping("vehicles/{id}")
    public Vehicle getVehicleById(@PathVariable Long id) {
        return vehicleService.findVehicleById(id);
    }

    @DeleteMapping("vehicles/{id}")
    public ResponseEntity<HttpStatus> deleteVehicleById(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
