package com.pgu.springboot.entity;

public enum AdsLimit {
    ONEMONTH("One month"),TWOMONTH("Two month"),THREEMONTH("Three month"),SIXMONTH("Six month"),ONEYEAR("One year");

    private String name;
    AdsLimit(String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }
}
