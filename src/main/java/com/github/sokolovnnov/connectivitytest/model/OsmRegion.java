package com.github.sokolovnnov.connectivitytest.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

public class OsmRegion {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private String name;
    private int id;
    private boolean isRussian;
    private Integer[] neighborIds;
    private List<OsmRegion> neighbors;
    private String path;

    public OsmRegion() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isRussian() {
        return isRussian;
    }

    public void setRussian(boolean russian) {
        isRussian = russian;
    }

    public Integer[] getNeighborIds() {
        return neighborIds;
    }

    public void setNeighborIds(Integer[] neighborIds) {
        this.neighborIds = neighborIds;
    }

    public List<OsmRegion> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(List<OsmRegion> neighbors) {
        this.neighbors = neighbors;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
