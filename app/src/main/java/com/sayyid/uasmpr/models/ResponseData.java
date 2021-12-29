package com.sayyid.uasmpr.models;


import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseData{
    @SerializedName("ResponseData")
    private List<ResponseDataItem> responseData;

    public List<ResponseDataItem> getResponseData(){
        return responseData;
    }
}