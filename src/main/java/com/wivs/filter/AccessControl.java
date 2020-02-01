package com.wivs.filter;

public class AccessControl {
    public String allow_Origin;
    public String allow_Methods;
    public String max_Age;
    public String allow_Headers;
    public String allow_Credentials;

    public void setAllow_Origin(String allow_Origin) {
        this.allow_Origin = allow_Origin;
    }

    public String getAllow_Origin() {
        return allow_Origin;
    }

    public void setAllow_Methods(String allow_Methods) {
        this.allow_Methods = allow_Methods;
    }

    public String getAllow_Methods() {
        return allow_Methods;
    }

    public void setMax_Age(String max_Age) {
        this.max_Age = max_Age;
    }

    public String getMax_Age() {
        return max_Age;
    }

    public void setAllow_Headers(String allow_Headers) {
        this.allow_Headers = allow_Headers;
    }

    public String getAllow_Headers() {
        return allow_Headers;
    }

    public void setAllow_Credentials(String allow_Credentials) {
        this.allow_Credentials = allow_Credentials;
    }

    public String getAllow_Credentials() {
        return allow_Credentials;
    }
}
