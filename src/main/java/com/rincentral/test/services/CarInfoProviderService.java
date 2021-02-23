package com.rincentral.test.services;

import com.rincentral.test.models.BodyCharacteristics;
import com.rincentral.test.models.CarFullInfo;
import com.rincentral.test.models.CarInfo;
import com.rincentral.test.models.EngineCharacteristics;
import com.rincentral.test.models.external.ExternalBrand;
import com.rincentral.test.models.external.ExternalCar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class CarInfoProviderService {

    @Autowired
    private ExternalCarsApiService externalCarApi;

    private final List<CarFullInfo> carFullInfo = new ArrayList<>();
    private final List<CarInfo> carInfo = new ArrayList<>();
    private List<ExternalBrand> externalBrands;
    private final Map<Integer, ExternalBrand> brands = new HashMap<>();

    @PostConstruct
    private void init() {
        List<ExternalCar> externalCars = externalCarApi.loadAllCars();
        externalBrands = externalCarApi.loadAllBrands();
        externalBrands.forEach(it -> brands.put(it.getId(), it));

        externalCars.stream().map(externalCar -> externalCarApi.loadCarInformationById(externalCar.getId()))
                .forEach(fi -> {
            carFullInfo.add(new CarFullInfo(fi.getId(), fi.getSegment(), brands.get(fi.getBrandId()), fi.getModel(),
                    fi.getGeneration(), fi.getModification(), fi.getYearsRange(),
                    new EngineCharacteristics(fi.getFuelType(), fi.getEngineType(), fi.getEngineDisplacement(),
                            fi.getHp()), fi.getGearboxType(), fi.getWheelDriveType(),
                    new BodyCharacteristics(fi.getBodyLength(), fi.getBodyWidth(), fi.getBodyHeight(),
                            fi.getBodyStyle()),
                    fi.getAcceleration(), fi.getMaxSpeed()));

            carInfo.add(new CarInfo(fi.getId(), fi.getSegment(), brands.get(fi.getBrandId()).getTitle(), fi.getModel(),
                    brands.get(fi.getBrandId()).getCountry(), fi.getGeneration(), fi.getModification(),
                    carFullInfo.get(carFullInfo.size() - 1)));
        });

    }

    public List<CarInfo> getCarsInfo() {
        return carInfo;
    }

    public List<CarFullInfo> getCarsFullInfo() {
        return carFullInfo;
    }

    public List<ExternalBrand> getBrandsInfo() {
        return externalBrands;
    }

    public Map<Integer, ExternalBrand> getBrandsMap() {
        return brands;
    }

}
