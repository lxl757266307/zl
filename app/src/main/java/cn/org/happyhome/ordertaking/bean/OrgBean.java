package cn.org.happyhome.ordertaking.bean;

public class OrgBean {
    private String organizationID;

    private String addressid;

    private String organizationname;

    private String phone;

    private String salesman;

    private String note1;

    private String note2;

    public String getOrganizationID() {
        return organizationID;
    }

    public void setOrganizationID(String organizationID) {
        this.organizationID = organizationID;
    }

    public String getAddressid() {
        return addressid;
    }

    public void setAddressid(String addressid) {
        this.addressid = addressid;
    }

    public String getOrganizationname() {
        return organizationname;
    }

    public void setOrganizationname(String organizationname) {
        this.organizationname = organizationname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSalesman() {
        return salesman;
    }

    public void setSalesman(String salesman) {
        this.salesman = salesman;
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

    @Override
    public String toString() {
        return "OrgBean{" +
                "organizationID='" + organizationID + '\'' +
                ", addressid='" + addressid + '\'' +
                ", organizationname='" + organizationname + '\'' +
                ", phone='" + phone + '\'' +
                ", salesman='" + salesman + '\'' +
                ", note1='" + note1 + '\'' +
                ", note2='" + note2 + '\'' +
                '}';
    }
}
