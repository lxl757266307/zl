package cn.org.happyhome.ordertaking.bean;

import java.io.Serializable;

public class Address implements Serializable {

    private String addressid;

    private String address;

    private String longitude;

    private String latitude;

    private String note1;

    private String note2;

    @Override
    public String toString() {
        return "Address{" +
                "addressid='" + addressid + '\'' +
                ", address='" + address + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", note1='" + note1 + '\'' +
                ", note2='" + note2 + '\'' +
                '}';
    }

    public String getAddressid() {
        return addressid;
    }

    public void setAddressid(String addressid) {
        this.addressid = addressid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getNote1() {
        return note1;
    }

    public void setNote1(String note1) {
        this.note1 = note1;
    }

    public String getNote2() {
        return note2;
    }

    public void setNote2(String note2) {
        this.note2 = note2;
    }
}
