package com.example.unitconverter;

import java.util.HashMap;


public class Conversions {

    HashMap<String, Double> distanceMap = new HashMap<>();

    public Conversions() {
        distanceMap.put("micrometres", 1.e-6d);
        distanceMap.put("millimetres", 1.E-3d);
        distanceMap.put("centimetres", 1.E-2d);
        distanceMap.put("metres", 1.d);
        distanceMap.put("kilometres", 1.E3d);
        distanceMap.put("inch", 0.0254d);
        distanceMap.put("foot", 0.3048d);
        distanceMap.put("yard", 0.9144d);
        distanceMap.put("mile", 1609.344d);
        distanceMap.put("nautical mile", 1852d);
    }

    public HashMap<String, Double> getDistanceMap() {
        return distanceMap;
    }
}
