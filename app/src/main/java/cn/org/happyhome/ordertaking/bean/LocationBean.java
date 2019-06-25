package cn.org.happyhome.ordertaking.bean;

import com.amap.api.services.core.LatLonPoint;

import java.io.Serializable;

public class LocationBean implements Serializable {

    private String poiId;
    private String name;
    private String email;
    private LatLonPoint latLonPoint;


    @Override
    public String toString() {
        return "LocationBean{" +
                "name='" + name + '\'' +
                "email='" + email + '\'' +
                ", latLonPoint=" + latLonPoint +
                '}';
    }

    public String getPoiId() {
        return poiId;
    }

    public void setPoiId(String poiId) {
        this.poiId = poiId;
    }

    public LocationBean() {
    }

    public LocationBean(String name, LatLonPoint latLonPoint) {
        this.name = name;
        this.latLonPoint = latLonPoint;
    }

    public LocationBean(String poiId, String name, String email, LatLonPoint latLonPoint) {
        this.name = name;
        this.email = email;
        this.poiId = poiId;
        this.latLonPoint = latLonPoint;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LatLonPoint getLatLonPoint() {
        return latLonPoint;
    }

    public void setLatLonPoint(LatLonPoint latLonPoint) {
        this.latLonPoint = latLonPoint;
    }
}
