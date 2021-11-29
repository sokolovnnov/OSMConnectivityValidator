package com.github.sokolovnnov.connectivitytest.model;

import java.io.Serializable;

public class SimpleNode implements Serializable {

//    private Integer id;
    private long osmId;
    private long wayOsmId;
    private int regionId;
    private int orderInWay;
    private float lat;
    private float lon;

    public SimpleNode() {
    }

    public SimpleNode(/*Integer id, */long osmId, long wayOsmId, int regionId, int orderInWay, float lat, float lon) {
//        this.id = id;
        this.osmId = osmId;
        this.wayOsmId = wayOsmId;
        this.regionId = regionId;
        this.orderInWay = orderInWay;
        this.lat = lat;
        this.lon = lon;
    }

//    public int getId() {
//        return id;
//    }

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

//    public void setId(int id) {
//        this.id = id;
//    }

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

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimpleNode that = (SimpleNode) o;

        if (osmId != that.osmId) return false;
        if (wayOsmId != that.wayOsmId) return false;
        if (regionId != that.regionId) return false;
        if (orderInWay != that.orderInWay) return false;
        if (Float.compare(that.lat, lat) != 0) return false;
        return Float.compare(that.lon, lon) == 0;
    }

    @Override
    public int hashCode() {
        int result = (int) (osmId ^ (osmId >>> 32));
        result = 31 * result + (int) (wayOsmId ^ (wayOsmId >>> 32));
        result = 31 * result + regionId;
        result = 31 * result + orderInWay;
        result = 31 * result + (lat != +0.0f ? Float.floatToIntBits(lat) : 0);
        result = 31 * result + (lon != +0.0f ? Float.floatToIntBits(lon) : 0);
        return result;
    }
}