package ru.connectivitytest.site.model;

public class SimpleNode {

    private Integer id;
    private long osmId;
    private long wayOsmId;
    private int orderInWay;
    private float lat;
    private float lon;

    public SimpleNode() {
    }

    public SimpleNode(Integer id, long osmId, long wayOsmId, int orderInWay, float lat, float lon) {
        this.id = id;
        this.osmId = osmId;
        this.wayOsmId = wayOsmId;
        this.orderInWay = orderInWay;
        this.lat = lat;
        this.lon = lon;
    }

    public int getId() {
        return id;
    }

    public long getOsmId() {
        return osmId;
    }

    public long getWayOsmId() {
        return wayOsmId;
    }

    public int getOrderInWay() {
        return orderInWay;
    }

    public float getLat() {
        return lat;
    }

    public float getLon() {
        return lon;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOsmId(long osmId) {
        this.osmId = osmId;
    }

    public void setWayOsmId(long wayOsmId) {
        this.wayOsmId = wayOsmId;
    }

    public void setOrderInWay(int orderInWay) {
        this.orderInWay = orderInWay;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimpleNode that = (SimpleNode) o;

        if (osmId != that.osmId) return false;
        if (wayOsmId != that.wayOsmId) return false;
        if (orderInWay != that.orderInWay) return false;
        if (lat != that.lat) return false;
        if (lon != that.lon) return false;
        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (int) (osmId ^ (osmId >>> 32));
        result = 31 * result + (int) (wayOsmId ^ (wayOsmId >>> 32));
        result = 31 * result + orderInWay;
        result = (int) (31 * result + lat);
        result = (int) (31 * result + lon);
        return result;
    }
}
