package com.tots.api.totsapi.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.tots.api.totsapi.classes.GasConsumption;
import com.tots.api.totsapi.exception.EntityNotFoundException;

import com.tots.api.totsapi.model.Vehicle;
import com.tots.api.totsapi.repository.VehicleRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VehicleService {
    
    @Autowired
    private VehicleRepository vehicleRepository;
    
    //GET
    public List<Vehicle> list() {
        return vehicleRepository.findAll();
    }

    //GET
    public Vehicle getVehicle(Long id) {
        return findOrFail(id);
    }

    //POST
    public Vehicle saveVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    //PUT
    @Transactional
    public Vehicle updateVehicle(
        long id,
        Vehicle vehicle
    ) {
        Vehicle vehicleSaved = findOrFail(id);

        BeanUtils.copyProperties(vehicle, vehicleSaved, "id");
        return vehicleRepository.save(vehicleSaved);
    }

    //DELETE
    public void deleteVehicle(Long id) {
        Vehicle vehicle = findOrFail(id);
        vehicleRepository.delete(vehicle);
    }

    public List<GasConsumption> calculateGasConspumption(long gasPrice, Long totalKmCity, Long totalKmRoad) {
        List <Vehicle> vehicles = vehicleRepository.findAll();
        ArrayList <GasConsumption> gasConsumptionList = new ArrayList<GasConsumption>();

        vehicles.forEach(vehicle -> {
            GasConsumption gasConsumption = new GasConsumption();
            Long totalGasConsumption = (vehicle.getKmCity() * totalKmCity) + (vehicle.getKmRoad() * totalKmRoad);
            Long totalGasSpent = totalGasConsumption * gasPrice;

            gasConsumption.setName(vehicle.getName());
            gasConsumption.setModel(vehicle.getModel());
            gasConsumption.setYear(vehicle.getDtFab().toString().split("-")[0]);
            gasConsumption.setGasConsumption(totalGasConsumption);
            gasConsumption.setGasSpent(totalGasSpent);

            gasConsumptionList.add(gasConsumption);
        });
        Collections.sort(gasConsumptionList, GasConsumption.GasConsumptionComparator);
        return gasConsumptionList;
    }

    private Vehicle findOrFail(Long id) {
        return vehicleRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("Vehicle not found."));
    }

}
