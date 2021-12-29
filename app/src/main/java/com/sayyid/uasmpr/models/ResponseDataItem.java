package com.sayyid.uasmpr.models;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseDataItem{
    @SerializedName("country_code")
    private String countryCode;

    @SerializedName("capital")
    private String capital;

    @SerializedName("timezones")
    private List<String> timezones;

    @SerializedName("name")
    private String name;

    @SerializedName("latlng")
    private List<Double> latlng;

    public String getCountryCode(){
        return countryCode;
    }

    public String getCapital(){
        return capital;
    }

    public List<String> getTimezones(){
        return timezones;
    }

    public String getName(){
        return name;
    }

    public List<Double> getLatlng(){
        return latlng;
    }
}