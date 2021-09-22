package com.github.sokolovnnov.connectivitytest.validator.model;

import org.alex73.osmemory.OsmWay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.sokolovnnov.connectivitytest.validator.StorageUtil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class OsmRegion {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private String name;
    private int id;
    private boolean isRussian;
    private Integer[] neighbors;
    private String path;
    private AdjacencyList adjacencyList;

    public OsmRegion() {}

    public void calculateAdjList() throws IOException {
        log.info("{}: creating adjacencyList for region...", name);
        AdjacencyList adjacencyList = AdjacencyList.newBuilder().readFromO5M(this.path).markAdjacentComponents().build();
        log.info("{}: number of connected components is {}", this.name, adjacencyList.getConnectedComponents().size());
        this.adjacencyList = adjacencyList;
    }

    public Set<OsmWay> getIsolatedWaysFromO5M() throws FileNotFoundException {
        Set<OsmWay> osmWays = adjacencyList.getIsolatedWaysFromO5M(this.path);
        log.info("{}: number of isolated ways is {}", this.name, osmWays.size());
        return osmWays;
    }

    public AdjacencyList getAdjacencyList() {
        return adjacencyList;
    }

    public void setEmptyAdjacencyList(){
        this.adjacencyList = AdjacencyList.newBuilder().setAdjacencyListToNull().build();
    }

    public void serializeAdjList() throws IOException {
        StorageUtil.serializeAdjList(this.adjacencyList);
        log.info("{}: serialize...", this.name);
        setEmptyAdjacencyList();
    }

    public void deSerializeAdjList() throws IOException, ClassNotFoundException {
        log.info("{}: deserialize...", this.name);
        this.adjacencyList = StorageUtil.deSerializeAdjList(this.path);
    }

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

    public Integer[] getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(Integer[] neighbors) {
        this.neighbors = neighbors;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
