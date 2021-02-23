package com.rincentral.test.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rincentral.test.models.external.ExternalBrand;
import com.rincentral.test.models.external.enums.GearboxType;
import com.rincentral.test.models.external.enums.WheelDriveType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarFullInfo implements CarInfoI {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("segment")
    private String segment;

    @JsonProperty("brand")
    private ExternalBrand brand;

    @JsonProperty("model")
    private String model;

    @JsonProperty("generation")
    private String generation;

    @JsonProperty("modification")
    private String modification;

    @JsonProperty("year_range")
    private String yearsRange;

    @JsonProperty("engine_characteristics")
    private EngineCharacteristics engineCharacteristics;

    @JsonProperty("gearbox")
    private GearboxType gearboxType;

    @JsonProperty("wheel_drive")
    private WheelDriveType wheelDriveType;

    @JsonProperty("body_characteristics")
    private BodyCharacteristics bodyCharacteristics;

    @JsonProperty("acceleration")
    private Double acceleration;

    @JsonProperty("max_speed")
    private Integer maxSpeed;

    @JsonIgnore
    @Override
    public String getCountry() {
        return brand.getCountry();
    }

    @JsonIgnore
    @Override
    public Integer getEngineDisplacement() {
        return engineCharacteristics.getEngineDisplacement();
    }

    @JsonIgnore
    @Override
    public Integer getHP() {
        return engineCharacteristics.getHp();
    }

    @JsonIgnore
    @Override
    public Integer getStartYear() {
        return Integer.parseInt(yearsRange.split("-")[0]);
    }

    @JsonIgnore
    @Override
    public Integer getEndYear() {
        String sec = yearsRange.split("-")[1];
        if (sec.equals("present")) {
            return Integer.MAX_VALUE;
        } else {
            return Integer.parseInt(sec);
        }
    }

    @JsonIgnore
    @Override
    public String getBodyStyle() {
        return bodyCharacteristics.getBodyStyle();
    }
}
