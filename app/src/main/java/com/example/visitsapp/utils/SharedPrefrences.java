package com.example.visitsapp.utils;

import static com.example.visitsapp.utils.AppConstantsUtils.PreferenceUtils.ACCESS_TOKEN;
import static com.example.visitsapp.utils.AppConstantsUtils.PreferenceUtils.EXECUTED_EVENT;
import static com.example.visitsapp.utils.AppConstantsUtils.PreferenceUtils.GET_CONFIG;
import static com.example.visitsapp.utils.AppConstantsUtils.PreferenceUtils.IMAGE;
import static com.example.visitsapp.utils.AppConstantsUtils.PreferenceUtils.LOGIN_CHECK;
import static com.example.visitsapp.utils.AppConstantsUtils.PreferenceUtils.LOGIN_INFO;
import static com.example.visitsapp.utils.AppConstantsUtils.PreferenceUtils.POST_FEEDBACK;
import static com.example.visitsapp.utils.AppConstantsUtils.PreferenceUtils.PREFERENCE_NAME;
import static com.example.visitsapp.utils.AppConstantsUtils.PreferenceUtils.REPLACE_FEEDBACK;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.visitsapp.application.MainApplication;
import com.example.visitsapp.model.configuration.ConfigurationResponse;
import com.example.visitsapp.model.request.PostFeedBackRequest;
import com.example.visitsapp.model.request.ReplaceEventRequest;
import com.example.visitsapp.model.responce.LoginResponce;
import com.example.visitsapp.model.responce.PlansData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

public class SharedPrefrences {

    private static SharedPrefrences instance = null;
    private static SharedPreferences preferences = null;

    private SharedPrefrences() {

    }

    public static SharedPrefrences getInstance() {

        if (instance == null) {
            instance = new SharedPrefrences();
            preferences = MainApplication.getAppContext()
                    .getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        }

        return instance;
    }


    public void clearPreference() {
        try {

            preferences.edit().clear().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setAccessToken(String token) {
        SharedPreferences.Editor prefsEditor = preferences.edit();
        prefsEditor.putString(ACCESS_TOKEN, token);
        prefsEditor.apply();
    }

    public String getAccessToken() {
        return preferences.getString(ACCESS_TOKEN, null);
    }


    public void setProfileImage(String image) {
        SharedPreferences.Editor prefsEditor = preferences.edit();
        prefsEditor.putString(IMAGE, image);
        prefsEditor.apply();
    }

    public String getProfileImage() {
        return preferences.getString(IMAGE, null);
    }


    public void setIsLogin(boolean token) {
        SharedPreferences.Editor prefsEditor = preferences.edit();
        prefsEditor.putBoolean(LOGIN_CHECK, token);
        prefsEditor.apply();
    }

    public boolean getIsLogin() {
        return preferences.getBoolean(LOGIN_CHECK, false);
    }

    public void setloginResponse(LoginResponce loginInfo) {
        String user = new Gson().toJson(loginInfo);
        try {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(LOGIN_INFO, user);
            editor.apply();
        } catch (Exception e) {
            Log.e("umer", Objects.requireNonNull(e.getMessage()));
        }
    }

    public LoginResponce getloginResponse() {
        Gson gson = new Gson();
        Type type = new TypeToken<LoginResponce>() {
        }.getType();
        String json = preferences.getString(LOGIN_INFO, "");
        if (json == null || json.equals("")) {
            return null;
        }
        return gson.fromJson(json, type);
    }


    public void setConfig(ConfigurationResponse configurationResponse) {
        String user = new Gson().toJson(configurationResponse);
        try {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(GET_CONFIG, user);
            editor.apply();
        } catch (Exception e) {
            Log.e("umer", Objects.requireNonNull(e.getMessage()));
        }
    }

    public ConfigurationResponse getConfig() {
        Gson gson = new Gson();
        Type type = new TypeToken<ConfigurationResponse>() {
        }.getType();
        String json = preferences.getString(GET_CONFIG, "");
        if (json == null || json.equals("")) {
            return null;
        }
        return gson.fromJson(json, type);
    }


    public void setPostFeedBack(PostFeedBackRequest loginInfo) {
        String user = new Gson().toJson(loginInfo);
        try {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(POST_FEEDBACK, user);
            editor.apply();
        } catch (Exception e) {
            Log.e("umer", Objects.requireNonNull(e.getMessage()));
        }
    }

    public PostFeedBackRequest getPostFeedBack() {
        Gson gson = new Gson();
        Type type = new TypeToken<PostFeedBackRequest>() {
        }.getType();
        String json = preferences.getString(POST_FEEDBACK, "");
        if (json == null || json.equals("")) {
            return null;
        }
        return gson.fromJson(json, type);
    }

    public void setReplaceEventFeedBack(ReplaceEventRequest loginInfo) {
        String user = new Gson().toJson(loginInfo);
        try {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(REPLACE_FEEDBACK, user);
            editor.apply();
        } catch (Exception e) {
            Log.e("umer", Objects.requireNonNull(e.getMessage()));
        }
    }

    public ReplaceEventRequest getReplaceEventFeedBack() {
        Gson gson = new Gson();
        Type type = new TypeToken<ReplaceEventRequest>() {
        }.getType();
        String json = preferences.getString(REPLACE_FEEDBACK, "");
        if (json == null || json.equals("")) {
            return null;
        }
        return gson.fromJson(json, type);
    }

    public void setExecutedEvent(ArrayList<PlansData> loginInfo) {
        String user = new Gson().toJson(loginInfo);
        try {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(EXECUTED_EVENT, user);
            editor.apply();
        } catch (Exception e) {
            Log.e("umer", Objects.requireNonNull(e.getMessage()));
        }
    }

    public ArrayList<PlansData> getExecutedEvent() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<PlansData>>() {
        }.getType();
        String json = preferences.getString(EXECUTED_EVENT, "");
        if (json == null || json.equals("")) {
            return null;
        }
        return gson.fromJson(json, type);
    }




}
