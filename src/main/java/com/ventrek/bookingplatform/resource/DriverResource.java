package com.ventrek.bookingplatform.resource;

import com.ventrek.bookingplatform.domain.Driver;
import com.ventrek.bookingplatform.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
public class DriverResource {

    @Autowired
    private DriverService driverService;

    @GetMapping("drivers")
    public ResponseEntity<List<Driver>> getDrivers(@RequestParam(defaultValue = "0") Integer pageNo,
                                                   @RequestParam(defaultValue = "10") Integer pageSize,
                                                   @RequestParam(defaultValue = "id") String sortBy){

        return ResponseEntity.status(HttpStatus.OK).body(driverService.getDrivers(pageNo, pageSize, sortBy));
    }

    @PostMapping("drivers")
    public Driver addDriver(@RequestBody Driver driver) {
        return driverService.addDriver(driver);
    }

    @PutMapping("drivers")
    public Driver updateDriver(@RequestBody Driver driver) {
        return driverService.updateDriver(driver);
    }

    @GetMapping("drivers/{id}")
    public Driver getDriverById(@PathVariable Long id) {
        return driverService.findDriverById(id);
    }

    @DeleteMapping("drivers/{id}")
    public ResponseEntity<HttpStatus> deleteDriverById(@PathVariable Long id) {
         driverService.deleteDriverById(id);
         return ResponseEntity.status(HttpStatus.OK).build();
    }


}
