package com.example.visitsapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResponceObject <T> implements Serializable {
    @SerializedName("message")
    private String message;
    @SerializedName("success")
    private boolean success;
    @SerializedName("responsecode")
    private int responsecode;

    @SerializedName("data")
    private T data = null;

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getResponsecode() {
        return responsecode;
    }

    public T getData() {

        return data;
    }


}
