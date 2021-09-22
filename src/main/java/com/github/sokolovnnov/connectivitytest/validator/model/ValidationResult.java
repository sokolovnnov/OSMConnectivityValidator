package com.github.sokolovnnov.connectivitytest.validator.model;

import org.alex73.osmemory.OsmWay;

import java.util.Collection;

public class ValidationResult {

    private final int regionId;
    private final String path;
    private final Collection<OsmWay> isolatedWays;

    public ValidationResult(int regionId, String path, Collection<OsmWay> isolatedWays) {
        this.regionId = regionId;
        this.path = path;
        this.isolatedWays = isolatedWays;
    }

    public int getRegionId() {
        return regionId;
    }

    public String getPath() {
        return path;
    }

    public Collection<OsmWay> getIsolatedWays() {
        return isolatedWays;
    }

}
