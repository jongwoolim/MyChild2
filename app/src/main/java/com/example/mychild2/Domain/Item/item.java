package com.example.mychild2.Domain.Item;

public class item {

    private String stcode; //어린이집 코드
    private String crname; //어린이집 명
    private String crtel; //전화번호
    private String crfax; //팩스번호
    private String craddr; //주소
    private String crcapat; // 정원

    public String getStcode() {
        return stcode;
    }

    public void setStcode(String stcode) {
        this.stcode = stcode;
    }

    public String getCrname() {
        return crname;
    }

    public void setCrname(String crname) {
        this.crname = crname;
    }

    public String getCrtel() {
        return crtel;
    }

    public void setCrtel(String crtel) {
        this.crtel = crtel;
    }

    public String getCrfax() {
        return crfax;
    }

    public void setCrfax(String crfax) {
        this.crfax = crfax;
    }

    public String getCraddr() {
        return craddr;
    }

    public void setCraddr(String craddr) {
        this.craddr = craddr;
    }

    public String getCrcapat() {
        return crcapat;
    }

    public void setCrcapat(String crcapat) {
        this.crcapat = crcapat;
    }
}
