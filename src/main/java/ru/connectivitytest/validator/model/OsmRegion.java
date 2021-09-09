package ru.connectivitytest.validator.model;

import org.alex73.osmemory.OsmWay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class OsmRegion {
    private final Logger log = LoggerFactory.getLogger(getClass());

    public String name;
    public int id;
    public boolean isRussian;
    public Integer[] neighbors;
    public String path;
    public AdjacencyList adjacencyList;

    public OsmRegion() {
    }

    public AdjacencyList calculateAdjList() throws IOException, ClassNotFoundException {
        return new AdjacencyList().calculateFromO5M(path);
    }

    public Set<OsmWay> getIsolatedWaysFromO5M() throws FileNotFoundException {
        return adjacencyList.getIsolatedWaysFromO5M(this.getPath());
    }

    public AdjacencyList getAdjacencyList() {
        return adjacencyList;
    }

    public void setAdjacencyList(AdjacencyList adjacencyList) {
        this.adjacencyList = adjacencyList;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public boolean isRussian() {
        return isRussian;
    }

    public Integer[] getNeighbors() {
        return neighbors;
    }

    public String getPath() {
        return path;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNeighbors(Integer[] neighbors) {
        this.neighbors = neighbors;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
