package cn.org.happyhome.nursing.bean;

import java.io.Serializable;

public class AddAddressBean  implements Serializable{



    /**
     * hospitalid : 001
     * hospitalname : 西京医院
     * note1 : 610113
     * addressid : 1045934897829060611
     */

    private String hospitalid;
    private String hospitalname;
    private String note1;
    private String addressid;
    private transient boolean isCheck;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getHospitalid() {
        return hospitalid;
    }

    public void setHospitalid(String hospitalid) {
        this.hospitalid = hospitalid;
    }

    public String getHospitalname() {
        return hospitalname;
    }

    public void setHospitalname(String hospitalname) {
        this.hospitalname = hospitalname;
    }

    public String getNote1() {
        return note1;
    }

    public void setNote1(String note1) {
        this.note1 = note1;
    }

    public String getAddressid() {
        return addressid;
    }

    public void setAddressid(String addressid) {
        this.addressid = addressid;
    }


    @Override
    public String toString() {
        return "Address{" +
                "hospitalid='" + hospitalid + '\'' +
                ", hospitalname='" + hospitalname + '\'' +
                ", note1='" + note1 + '\'' +
                ", addressid='" + addressid + '\'' +
                '}';
    }

}
