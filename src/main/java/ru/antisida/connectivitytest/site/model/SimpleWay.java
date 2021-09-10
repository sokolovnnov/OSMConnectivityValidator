package ru.antisida.connectivitytest.site.model;

import java.util.Arrays;

public class SimpleWay {

    private int id;
    private long osmId;
    private SimpleNode[] nodes;

    public SimpleWay() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getOsmId() {
        return osmId;
    }

    public void setOsmId(long osmId) {
        this.osmId = osmId;
    }

    public SimpleNode[] getNodes() {
        return nodes;
    }

    public void setNodes(SimpleNode[] nodes) {
        this.nodes = nodes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimpleWay simpleWay = (SimpleWay) o;

        if (id != simpleWay.id) return false;
        if (osmId != simpleWay.osmId) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(nodes, simpleWay.nodes);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (int) (osmId ^ (osmId >>> 32));
        result = 31 * result + Arrays.hashCode(nodes);
        return result;
    }
}
