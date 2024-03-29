package com.github.sokolovnnov.connectivitytest.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class MarkedNode implements Serializable {

    private long id;
    protected long[] wayIds; //веи в которые входит точка
    private boolean visited;
    private int connectedComponentId; //компонент связности в который входит точка
    protected long[] neighborNodeIds; // id соседних точек

    public MarkedNode(long id, long wayId, long[] neighborNodeIds) {
        this.id = id;
        this.wayIds = new long[]{wayId};
        this.neighborNodeIds = neighborNodeIds;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long[] getWayIds(){ return  wayIds; }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public int getConnectedComponentId() {
        return connectedComponentId;
    }

    public void setConnectedComponentId(int connectedComponentId) {
        this.connectedComponentId = connectedComponentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MarkedNode that = (MarkedNode) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, connectedComponentId);
        result = 31 * result + Arrays.hashCode(wayIds);
        result = 31 * result + Arrays.hashCode(neighborNodeIds);
        return result;
    }
}
