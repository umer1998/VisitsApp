package com.example.visitsapp.delegate;

public interface ResponseCallBack<T> {
    public void onSuccess(T body);

    void onFailure(String message);
}
