package com.github.sokolovnnov.connectivitytest.validator.model;

import java.io.Serializable;

public class ConnectedComponent implements Serializable {

    private final int id;
    private final int size;
    private boolean isolated;

    public ConnectedComponent(int id, int size) {
        this.id = id;
        this.size = size;
        this.isolated = size < 1000;
    }

    public int getId() {
        return id;
    }

    public boolean isIsolated() {
        return isolated;
    }

    public void setIsolated(boolean isolated) {
        this.isolated = isolated;
    }

    @Override
    public String toString() {
        return "ConnectedComponent{" +
               "id=" + id +
               ", size=" + size +
               ", isolated=" + isolated +
               '}';
    }
}
