package com.example.mychild2.Domain.child;

public class Child {

    private String CH_class;
    private String CH_name;
    private String CH_age;
    private String CH_number;
    private String CH_addr;
    private String CH_phone;
    private String CH_comment;

    public Child(){

    }
    public Child(String CH_class, String CH_name,String CH_age, String CH_number, String CH_addr, String CH_phone, String CH_comment) {
        this.CH_class = CH_class;
        this.CH_name = CH_name;
        this.CH_age = CH_age;
        this.CH_number = CH_number;
        this.CH_addr = CH_addr;
        this.CH_phone = CH_phone;
        this.CH_comment = CH_comment;
    }

    public String getCH_class() {
        return CH_class;
    }

    public void setCH_class(String CH_class) {
        this.CH_class = CH_class;
    }

    public String getCH_name() {
        return CH_name;
    }

    public void setCH_name(String CH_name) {
        this.CH_name = CH_name;
    }

    public String getCH_age() {
        return CH_age;
    }

    public void setCH_age(String CH_age) {
        this.CH_age = CH_age;
    }

    public String getCH_number() {
        return CH_number;
    }

    public void setCH_number(String CH_number) {
        this.CH_number = CH_number;
    }

    public String getCH_addr() {
        return CH_addr;
    }

    public void setCH_addr(String CH_addr) {
        this.CH_addr = CH_addr;
    }

    public String getCH_phone() {
        return CH_phone;
    }

    public void setCH_phone(String CH_phone) {
        this.CH_phone = CH_phone;
    }

    public String getCH_comment() {
        return CH_comment;
    }

    public void setCH_comment(String CH_comment) {
        this.CH_comment = CH_comment;
    }
}
