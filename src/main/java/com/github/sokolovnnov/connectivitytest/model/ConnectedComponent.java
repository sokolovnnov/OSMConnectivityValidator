package com.github.sokolovnnov.connectivitytest.model;

import java.io.Serializable;

public class ConnectedComponent implements Serializable {

    private final int id;
    private boolean isolated;

    public ConnectedComponent(int id, int size) {
        this.id = id;
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

}
