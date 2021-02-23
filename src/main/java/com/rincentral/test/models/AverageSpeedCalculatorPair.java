package com.rincentral.test.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AverageSpeedCalculatorPair {
    int count = 0;
    double speed = 0;

    public void add(double value) {
        count++;
        speed += value;
    }

    public double getAverageSpeed() {
        return speed / count;
    }
}
