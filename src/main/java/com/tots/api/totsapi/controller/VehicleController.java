package com.tots.api.totsapi.controller;

import java.util.List;

import javax.transaction.Transactional;

import com.tots.api.totsapi.classes.GasConsumption;
import com.tots.api.totsapi.model.Vehicle;
import com.tots.api.totsapi.service.VehicleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/vehicles")
    public List<Vehicle> list() {
        return vehicleService.list();
    }

    @GetMapping("/vehicle/{id}")
    public ResponseEntity<Vehicle> getVehicle(@PathVariable("id") Long id){
        return ResponseEntity.ok(vehicleService.getVehicle(id));
    }
 
    @PostMapping("/vehicle")
    public ResponseEntity<Vehicle> add(@Validated @RequestBody Vehicle vehicle) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicleService.saveVehicle(vehicle));
    }

    @Transactional
    @PutMapping("/vehicle/{id}")
    public ResponseEntity<Vehicle> updateVehicle(
        @PathVariable("id") Long id,
        @Validated @RequestBody Vehicle vehicle
    ) {
        return ResponseEntity.ok(vehicleService.updateVehicle(id, vehicle));
    }

    @DeleteMapping("/vehicle/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVehicle(@PathVariable("id") Long id){
        vehicleService.deleteVehicle(id);
    }

    @GetMapping("/calculateGasConsuption")
    public List<GasConsumption> calculateGasConsuption(
        @RequestParam("gasPrice")    Long gasPrice,
        @RequestParam("totalKmCity") Long totalKmCity,
        @RequestParam("totalKmRoad") Long totalKmRoad
    ) {
        return vehicleService.calculateGasConspumption(gasPrice, totalKmCity, totalKmRoad);
    }
}
