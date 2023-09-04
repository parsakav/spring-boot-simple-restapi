package com.pgu.springboot.model.request;

public enum RoleType {
    RENTER("ROLE_RENTER"),LESSOR("ROLE_LESSOR");
    private String name;
    RoleType(String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }

}
