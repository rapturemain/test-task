package com.rincentral.test.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarInfo implements CarInfoI {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("segment")
    private String segment;

    @JsonProperty("brand")
    private String brand;

    @JsonProperty("model")
    private String model;

    @JsonProperty("country")
    private String country;

    @JsonProperty("generation")
    private String generation;

    @JsonProperty("modification")
    private String modification;

    @JsonIgnore
    private CarFullInfo fullInfo;

    @JsonIgnore
    @Override
    public Integer getEngineDisplacement() {
        return fullInfo.getEngineDisplacement();
    }

    @JsonIgnore
    @Override
    public Integer getHP() {
        return fullInfo.getHP();
    }

    @JsonIgnore
    @Override
    public Integer getStartYear() {
        return fullInfo.getStartYear();
    }

    @JsonIgnore
    @Override
    public Integer getEndYear() {
        return fullInfo.getEndYear();
    }

    @JsonIgnore
    @Override
    public String getBodyStyle() {
        return fullInfo.getBodyStyle();
    }
}
