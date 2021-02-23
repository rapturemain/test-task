package com.rincentral.test.controllers;

import com.rincentral.test.models.AverageSpeedCalculatorPair;
import com.rincentral.test.models.CarInfoI;
import com.rincentral.test.models.external.enums.EngineType;
import com.rincentral.test.models.external.enums.FuelType;
import com.rincentral.test.models.external.enums.GearboxType;
import com.rincentral.test.models.external.enums.WheelDriveType;
import com.rincentral.test.models.params.CarRequestParameters;
import com.rincentral.test.models.params.MaxSpeedRequestParameters;
import com.rincentral.test.services.CarInfoProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

    @Autowired
    CarInfoProviderService carInfoProviderService;

    @GetMapping("/cars")
    public ResponseEntity<List<? extends CarInfoI>> getCars(CarRequestParameters requestParameters) {
        List<? extends CarInfoI> cars = requestParameters.getIsFull() != null && requestParameters.getIsFull() ?
                carInfoProviderService.getCarsFullInfo() : carInfoProviderService.getCarsInfo();
        Stream<? extends CarInfoI> carsStream = cars.stream();
        if (requestParameters.getCountry() != null) {
            carsStream = carsStream.filter(it -> it.getCountry().matches(".*" + requestParameters.getCountry() + ".*"));
        }
        if (requestParameters.getSegment() != null) {
            carsStream = carsStream.filter(it -> it.getSegment().matches(".*" + requestParameters.getSegment() + ".*"));
        }
        if (requestParameters.getMinEngineDisplacement() != null) {
            carsStream = carsStream.filter(it -> it.getEngineDisplacement() >= requestParameters.getMinEngineDisplacement());
        }
        if (requestParameters.getMinEngineHorsepower() != null) {
            carsStream = carsStream.filter(it -> it.getEngineDisplacement() >= requestParameters.getMinEngineHorsepower());
        }
        if (requestParameters.getMinMaxSpeed() != null) {
            carsStream = carsStream.filter(it -> it.getEngineDisplacement() >= requestParameters.getMinMaxSpeed());
        }
        if (requestParameters.getSearch() != null) {
            carsStream = carsStream.filter(it -> it.getModel().matches(".*" + requestParameters.getSearch() + ".*") ||
                    it.getGeneration().matches(".*" + requestParameters.getSearch() + ".*") ||
                    it.getModification().matches(".*" + requestParameters.getSearch() + ".*"));
        }
        if (requestParameters.getYear() != null) {
            carsStream = carsStream.filter(it -> it.getStartYear() <= requestParameters.getYear() &&
                    it.getEndYear() >= requestParameters.getYear());
        }
        if (requestParameters.getBodyStyle() != null) {
            carsStream = carsStream.filter(it -> it.getBodyStyle().matches(".*" + requestParameters.getBodyStyle() + ".*"));
        }
        return ResponseEntity.ok(carsStream.collect(Collectors.toList()));
    }

    private List<String> existingFuelTypes = null;

    @GetMapping("/fuel-types")
    public ResponseEntity<List<String>> getFuelTypes() {
        if (existingFuelTypes == null) {
            HashSet<FuelType> types = new HashSet<>();
            carInfoProviderService.getCarsFullInfo().forEach(it -> {
                types.add(it.getEngineCharacteristics().getFuelType());
            });
            existingFuelTypes = types.stream().map(Enum::toString).collect(Collectors.toList());
        }
        return ResponseEntity.ok(existingFuelTypes);
    }

    private List<String> existingBodyStyles = null;

    @GetMapping("/body-styles")
    public ResponseEntity<List<String>> getBodyStyles() {
        if (existingBodyStyles == null) {
            HashSet<String> styles = new HashSet<>();
            carInfoProviderService.getCarsFullInfo().forEach(it -> {
                styles.add(it.getBodyStyle());
            });
            existingBodyStyles = new ArrayList<>(styles);
        }
        return ResponseEntity.ok(existingBodyStyles);
    }

    private List<String> existingEngineTypes = null;

    @GetMapping("/engine-types")
    public ResponseEntity<List<String>> getEngineTypes() {
        if (existingEngineTypes == null) {
            HashSet<EngineType> types = new HashSet<>();
            carInfoProviderService.getCarsFullInfo().forEach(it -> {
                types.add(it.getEngineCharacteristics().getEngineType());
            });
            existingEngineTypes = types.stream().map(Enum::toString).collect(Collectors.toList());
        }
        return ResponseEntity.ok(existingEngineTypes);
    }

    private List<String> existingWheelDrives = null;

    @GetMapping("/wheel-drives")
    public ResponseEntity<List<String>> getWheelDrives() {
        if (existingWheelDrives == null) {
            HashSet<WheelDriveType> types = new HashSet<>();
            carInfoProviderService.getCarsFullInfo().forEach(it -> {
                types.add(it.getWheelDriveType());
            });
            existingWheelDrives = types.stream().map(Enum::toString).collect(Collectors.toList());
        }
        return ResponseEntity.ok(existingWheelDrives);
    }

    private List<String> existingGearBoxes = null;

    @GetMapping("/gearboxes")
    public ResponseEntity<List<String>> getGearboxTypes() {
        if (existingGearBoxes == null) {
            HashSet<GearboxType> types = new HashSet<>();
            carInfoProviderService.getCarsFullInfo().forEach(it -> {
                types.add(it.getGearboxType());
            });
            existingGearBoxes = types.stream().map(Enum::toString).collect(Collectors.toList());
        }
        return ResponseEntity.ok(existingGearBoxes);
    }

    private Map<String, Double> averageSpeedBrand = null;
    private Map<String, Double> averageSpeedModel = null;

    @GetMapping("/max-speed")
    public ResponseEntity<Double> getMaxSpeed(MaxSpeedRequestParameters requestParameters) {
        if (requestParameters.getBrand() == null && requestParameters.getModel() == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No parameters provided"
            );
        }
        if (requestParameters.getBrand() != null && requestParameters.getModel() != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Both parameters provided"
            );
        }
        Double res = null;
        if (requestParameters.getModel() != null) {
            if (averageSpeedModel == null) {
                Map<String, AverageSpeedCalculatorPair> map = new HashMap<>();
                carInfoProviderService.getCarsFullInfo().forEach(it -> {
                    if (!map.containsKey(it.getModel())) {
                        map.put(it.getModel(), new AverageSpeedCalculatorPair());
                    }
                    map.get(it.getModel()).add(it.getMaxSpeed());
                });
                averageSpeedModel = new HashMap<>();
                map.forEach((key, value) -> averageSpeedModel.put(key, value.getAverageSpeed()));
            }
            res = averageSpeedModel.get(requestParameters.getModel());
        }
        if (requestParameters.getBrand() != null) {
            if (averageSpeedModel == null) {
                Map<String, AverageSpeedCalculatorPair> map = new HashMap<>();
                carInfoProviderService.getCarsFullInfo().forEach(it -> {
                    if (!map.containsKey(it.getBrand().getTitle())) {
                        map.put(it.getBrand().getTitle(), new AverageSpeedCalculatorPair());
                    }
                    map.get(it.getBrand().getTitle()).add(it.getMaxSpeed());
                });
                averageSpeedBrand = new HashMap<>();
                map.forEach((key, value) -> averageSpeedBrand.put(key, value.getAverageSpeed()));
            }
            res = averageSpeedBrand.get(requestParameters.getBrand());
        }
        if (res == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No such brand/model found"
            );
        }
        return ResponseEntity.ok(res);
    }
}
