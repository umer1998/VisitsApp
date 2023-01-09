package com.example.visitsapp.business.impl;

import android.content.Context;
import android.util.Log;

import com.example.visitsapp.business.BusinessService;
import com.example.visitsapp.business.PostFeedBackResponce;
import com.example.visitsapp.delegate.ResponseCallBack;
import com.example.visitsapp.model.ResponceObject;
import com.example.visitsapp.model.configuration.ConfigurationResponse;
import com.example.visitsapp.model.request.CreateEventFeedback;
import com.example.visitsapp.model.request.CreatePlanRequest;
import com.example.visitsapp.model.request.ForgotPasswordRequest;
import com.example.visitsapp.model.request.LoginRequest;
import com.example.visitsapp.model.request.PostFeedBackRequest;
import com.example.visitsapp.model.request.ReplaceEventRequest;
import com.example.visitsapp.model.request.UpdateEventRequest;
import com.example.visitsapp.model.request.UploadImageRequest;
import com.example.visitsapp.model.responce.ApproveEventRequest;
import com.example.visitsapp.model.responce.ApproveEventResponce;
import com.example.visitsapp.model.responce.CreatePlanResponce;
import com.example.visitsapp.model.responce.DashboardResponce;
import com.example.visitsapp.model.responce.ForgotPasswordResponce;
import com.example.visitsapp.model.responce.GetLeavesResponce;
import com.example.visitsapp.model.responce.GetPendingApproval;
import com.example.visitsapp.model.responce.GetReportingTeamResponce;
import com.example.visitsapp.model.responce.LoginResponce;
import com.example.visitsapp.model.responce.PlannerResponse;
import com.example.visitsapp.model.responce.PlansData;
import com.example.visitsapp.model.responce.UpdateEventResponce;
import com.example.visitsapp.network.ApiClient;
import com.example.visitsapp.ui.MainActivity;
import com.example.visitsapp.ui.activities.ForgotPassword;
import com.example.visitsapp.utils.alert.AlertUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Business {

    private BusinessService businessService;

    public Business() {

        businessService = ApiClient.getApiClient().create(BusinessService.class);
    }

    public void login(final LoginRequest requestInfo,
                         final ResponseCallBack<LoginResponce> responseCallBack) {

        String json = new Gson().toJson(requestInfo);
        Log.d("myTag", json);
        Call<ResponceObject<LoginResponce>> call = businessService.login(requestInfo);

        call.enqueue(new Callback<ResponceObject<LoginResponce>>() {
            @Override
            public void onResponse(Call<ResponceObject<LoginResponce>> call,
                                   Response<ResponceObject<LoginResponce>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null
                            && response.body().getResponsecode() == 200) {
                        responseCallBack.onSuccess(response.body().getData());
                    } else if (response.body() != null
                            && response.body().getMessage() != null) {
                        responseCallBack.onFailure(response.body().getMessage());
                    }
                } else {
                    responseCallBack.onFailure(response.message());
                }
            }
            @Override
            public void onFailure(Call<ResponceObject<LoginResponce>> call, Throwable t) {
                responseCallBack.onFailure(t.getMessage());
            }
        });
    }


    public void forgotPassword(final ForgotPasswordRequest requestInfo,
                      final ResponseCallBack<ForgotPasswordResponce> responseCallBack) {

        String json = new Gson().toJson(requestInfo);
        Log.d("myTag", json);
        Call<ResponceObject<ForgotPasswordResponce>> call = businessService.forgotPassword(requestInfo);

        call.enqueue(new Callback<ResponceObject<ForgotPasswordResponce>>() {
            @Override
            public void onResponse(Call<ResponceObject<ForgotPasswordResponce>> call,
                                   Response<ResponceObject<ForgotPasswordResponce>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null
                            && response.body().getResponsecode() == 200) {
                        responseCallBack.onSuccess(response.body().getData());
                    } else if (response.body() != null
                            && response.body().getMessage() != null) {
                        responseCallBack.onFailure(response.body().getMessage());
                    }
                } else {
                    responseCallBack.onFailure(response.message());
                }
            }
            @Override
            public void onFailure(Call<ResponceObject<ForgotPasswordResponce>> call, Throwable t) {
                responseCallBack.onFailure(t.getMessage());
            }
        });
    }



    public void getDashboard(final ResponseCallBack<DashboardResponce> responseCallBack) {

        Call<ResponceObject<DashboardResponce>> call = businessService.getDashboard();
        call.enqueue(new Callback<ResponceObject<DashboardResponce>>() {
            @Override
            public void onResponse(Call<ResponceObject<DashboardResponce>> call,
                                   Response<ResponceObject<DashboardResponce>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null
                            && response.body().getResponsecode() == 200) {
                        responseCallBack.onSuccess(response.body().getData());
                    } else if (response.body() != null
                            && response.body().getMessage() != null) {
                        responseCallBack.onFailure(response.body().getMessage());
                    }
                } else {
                    responseCallBack.onFailure(response.message());
                }
            }
            @Override
            public void onFailure(Call<ResponceObject<DashboardResponce>> call, Throwable t) {
                responseCallBack.onFailure(t.getMessage());
            }
        });
    }


    public void getConfiguration(final ResponseCallBack<ConfigurationResponse> responseCallBack) {

        Call<ResponceObject<ConfigurationResponse>> call = businessService.getConfig();
        call.enqueue(new Callback<ResponceObject<ConfigurationResponse>>() {
            @Override
            public void onResponse(Call<ResponceObject<ConfigurationResponse>> call,
                                   Response<ResponceObject<ConfigurationResponse>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null
                            && response.body().getResponsecode() == 200) {
                        responseCallBack.onSuccess(response.body().getData());
                    } else if (response.body() != null
                            && response.body().getMessage() != null) {
                        responseCallBack.onFailure(response.body().getMessage());
                    }
                } else {
                    responseCallBack.onFailure(response.message());
                }
            }
            @Override
            public void onFailure(Call<ResponceObject<ConfigurationResponse>> call, Throwable t) {
                responseCallBack.onFailure(t.getMessage());
            }
        });
    }



    public void createPlan(MainActivity context, final CreatePlanRequest requestInfo,
                           final ResponseCallBack<CreatePlanResponce> responseCallBack) {

        String json = new Gson().toJson(requestInfo);
        Log.d("myTag", json);
        Call<ResponceObject<CreatePlanResponce>> call = businessService.createPlan(requestInfo);

        call.enqueue(new Callback<ResponceObject<CreatePlanResponce>>() {
            @Override
            public void onResponse(Call<ResponceObject<CreatePlanResponce>> call,
                                   Response<ResponceObject<CreatePlanResponce>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null
                            && response.body().getResponsecode() == 200) {
                        responseCallBack.onSuccess(response.body().getData());
                    }

                    else if (response.body() != null
                            && response.body().getMessage() != null) {
                        responseCallBack.onFailure(response.body().getMessage());
                    }
                } else {
                    if(response.code() == 404){
                        responseCallBack.onFailure("event exist for today");
                    } else {
                        responseCallBack.onFailure(response.message());
                    }

                }
            }
            @Override
            public void onFailure(Call<ResponceObject<CreatePlanResponce>> call, Throwable t) {
                responseCallBack.onFailure(t.getMessage());
            }
        });
    }


    public void plans(final ResponseCallBack<List<PlansData>> responseCallBack) {


        Call<ResponceObject<List<PlansData>>> call = businessService.dayPlanner();

        call.enqueue(new Callback<ResponceObject<List<PlansData>>>() {
            @Override
            public void onResponse(Call<ResponceObject<List<PlansData>>> call,
                                   Response<ResponceObject<List<PlansData>>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null
                            && response.body().getResponsecode() == 200) {
                        responseCallBack.onSuccess(response.body().getData());
                    } else if (response.body() != null
                            && response.body().getMessage() != null) {
                        responseCallBack.onFailure(response.body().getMessage());
                    }
                } else {
                    responseCallBack.onFailure(response.message());
                }
            }
            @Override
            public void onFailure(Call<ResponceObject<List<PlansData>>> call, Throwable t) {
                responseCallBack.onFailure(t.getMessage());
            }
        });
    }





    public void getReportingTeam(final ResponseCallBack<ArrayList<GetReportingTeamResponce>> responseCallBack) {


        Call<ResponceObject<ArrayList<GetReportingTeamResponce>>> call = businessService.getReportingTeam();

        call.enqueue(new Callback<ResponceObject<ArrayList<GetReportingTeamResponce>>>() {
            @Override
            public void onResponse(Call<ResponceObject<ArrayList<GetReportingTeamResponce>>> call,
                                   Response<ResponceObject<ArrayList<GetReportingTeamResponce>>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null
                            && response.body().getResponsecode() == 200) {
                        responseCallBack.onSuccess(response.body().getData());
                    } else if (response.body() != null
                            && response.body().getMessage() != null) {
                        responseCallBack.onFailure(response.body().getMessage());
                    }
                } else {
                    responseCallBack.onFailure(response.message());
                }
            }
            @Override
            public void onFailure(Call<ResponceObject<ArrayList<GetReportingTeamResponce>>> call, Throwable t) {
                responseCallBack.onFailure(t.getMessage());
            }
        });
    }





    public void getPendingApproval(int id, final ResponseCallBack<ArrayList<GetPendingApproval>> responseCallBack) {


        Call<ResponceObject<ArrayList<GetPendingApproval>>> call = businessService.getPendingApproval(id);

        call.enqueue(new Callback<ResponceObject<ArrayList<GetPendingApproval>>>() {
            @Override
            public void onResponse(Call<ResponceObject<ArrayList<GetPendingApproval>>> call,
                                   Response<ResponceObject<ArrayList<GetPendingApproval>>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null
                            && response.body().getResponsecode() == 200) {
                        responseCallBack.onSuccess(response.body().getData());
                    } else if (response.body() != null
                            && response.body().getMessage() != null) {
                        responseCallBack.onFailure(response.body().getMessage());
                    }
                } else {
                    responseCallBack.onFailure(response.message());
                }
            }
            @Override
            public void onFailure(Call<ResponceObject<ArrayList<GetPendingApproval>>> call, Throwable t) {
                responseCallBack.onFailure(t.getMessage());
            }
        });
    }

    public void getExecutionEvent(final ResponseCallBack<ArrayList<PlansData>> responseCallBack) {


        Call<ResponceObject<ArrayList<PlansData>>> call = businessService.getExecutedPlan();

        call.enqueue(new Callback<ResponceObject<ArrayList<PlansData>>>() {
            @Override
            public void onResponse(Call<ResponceObject<ArrayList<PlansData>>> call,
                                   Response<ResponceObject<ArrayList<PlansData>>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null
                            && response.body().getResponsecode() == 200) {
                        responseCallBack.onSuccess(response.body().getData());
                    } else if (response.body() != null
                            && response.body().getMessage() != null) {
                        responseCallBack.onFailure(response.body().getMessage());
                    }
                } else {
                    responseCallBack.onFailure(response.message());
                }
            }
            @Override
            public void onFailure(Call<ResponceObject<ArrayList<PlansData>>> call, Throwable t) {
                responseCallBack.onFailure(t.getMessage());
            }
        });
    }



    public void approveEvent(final ApproveEventRequest requestInfo,
                      final ResponseCallBack<ApproveEventResponce> responseCallBack) {

        String json = new Gson().toJson(requestInfo);
        Log.d("myTag", json);
        Call<ResponceObject<ApproveEventResponce>> call = businessService.approveEvent(requestInfo);

        call.enqueue(new Callback<ResponceObject<ApproveEventResponce>>() {
            @Override
            public void onResponse(Call<ResponceObject<ApproveEventResponce>> call,
                                   Response<ResponceObject<ApproveEventResponce>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null
                            && response.body().getResponsecode() == 200) {
                        responseCallBack.onSuccess(response.body().getData());
                    } else if (response.body() != null
                            && response.body().getMessage() != null) {
                        responseCallBack.onFailure(response.body().getMessage());
                    }
                } else {
                    responseCallBack.onFailure(response.message());
                }
            }
            @Override
            public void onFailure(Call<ResponceObject<ApproveEventResponce>> call, Throwable t) {
                responseCallBack.onFailure(t.getMessage());
            }
        });
    }


    public void postFeedBack(final PostFeedBackRequest requestInfo,
                             final ResponseCallBack<PostFeedBackResponce> responseCallBack) {

        String json = new Gson().toJson(requestInfo);
        Log.d("myTag", json);
        Call<ResponceObject<PostFeedBackResponce>> call = businessService.postFeedback(requestInfo);

        call.enqueue(new Callback<ResponceObject<PostFeedBackResponce>>() {
            @Override
            public void onResponse(Call<ResponceObject<PostFeedBackResponce>> call,
                                   Response<ResponceObject<PostFeedBackResponce>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null
                            && response.body().getResponsecode() == 200) {
                        responseCallBack.onSuccess(response.body().getData());
                    } else if (response.body() != null
                            && response.body().getMessage() != null) {
                        responseCallBack.onFailure(response.body().getMessage());
                    }
                } else {
                    responseCallBack.onFailure(response.message());
                }
            }
            @Override
            public void onFailure(Call<ResponceObject<PostFeedBackResponce>> call, Throwable t) {
                responseCallBack.onFailure(t.getMessage());
            }
        });
    }


    public void replaceevent(final ReplaceEventRequest requestInfo,
                             final ResponseCallBack<String> responseCallBack) {

        String json = new Gson().toJson(requestInfo);
        Log.d("myTag", json);
        Call<ResponceObject<String>> call = businessService.replaceEvent(requestInfo);

        call.enqueue(new Callback<ResponceObject<String>>() {
            @Override
            public void onResponse(Call<ResponceObject<String>> call,
                                   Response<ResponceObject<String>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null
                            && response.body().getResponsecode() == 200) {
                        responseCallBack.onSuccess(response.body().getData());
                    } else if (response.body() != null
                            && response.body().getMessage() != null) {
                        responseCallBack.onFailure(response.body().getMessage());
                    }
                } else {
                    responseCallBack.onFailure(response.message());
                }
            }
            @Override
            public void onFailure(Call<ResponceObject<String>> call, Throwable t) {
                responseCallBack.onFailure(t.getMessage());
            }
        });
    }



    public void getLeaves(final ResponseCallBack<ArrayList<GetLeavesResponce>> responseCallBack) {


        Call<ResponceObject<ArrayList<GetLeavesResponce>>> call = businessService.getLeavesResponse();

        call.enqueue(new Callback<ResponceObject<ArrayList<GetLeavesResponce>>>() {
            @Override
            public void onResponse(Call<ResponceObject<ArrayList<GetLeavesResponce>>> call,
                                   Response<ResponceObject<ArrayList<GetLeavesResponce>>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null
                            && response.body().getResponsecode() == 200) {
                        responseCallBack.onSuccess(response.body().getData());
                    } else if (response.body() != null
                            && response.body().getMessage() != null) {
                        responseCallBack.onFailure(response.body().getMessage());
                    }
                } else {
                    responseCallBack.onFailure(response.message());
                }
            }
            @Override
            public void onFailure(Call<ResponceObject<ArrayList<GetLeavesResponce>>> call, Throwable t) {
                responseCallBack.onFailure(t.getMessage());
            }
        });
    }


    public void updateEvent(final UpdateEventRequest requestInfo,
                           final ResponseCallBack<UpdateEventResponce> responseCallBack) {

        String json = new Gson().toJson(requestInfo);
        Log.d("myTag", json);
        Call<ResponceObject<UpdateEventResponce>> call = businessService.updateevent(requestInfo);

        call.enqueue(new Callback<ResponceObject<UpdateEventResponce>>() {
            @Override
            public void onResponse(Call<ResponceObject<UpdateEventResponce>> call,
                                   Response<ResponceObject<UpdateEventResponce>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null
                            && response.body().getResponsecode() == 200) {
                        responseCallBack.onSuccess(response.body().getData());
                    } else if (response.body() != null
                            && response.body().getMessage() != null) {
                        responseCallBack.onFailure(response.body().getMessage());
                    }
                } else {
                    responseCallBack.onFailure(response.message());
                }
            }
            @Override
            public void onFailure(Call<ResponceObject<UpdateEventResponce>> call, Throwable t) {
                responseCallBack.onFailure(t.getMessage());
            }
        });
    }


    public void uploadImage(final UploadImageRequest requestInfo,
                            final ResponseCallBack<String> responseCallBack) {

        String json = new Gson().toJson(requestInfo);
        Log.d("myTag", json);
        Call<ResponceObject<String>> call = businessService.uploadImage(requestInfo);

        call.enqueue(new Callback<ResponceObject<String>>() {
            @Override
            public void onResponse(Call<ResponceObject<String>> call,
                                   Response<ResponceObject<String>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null
                            && response.body().getResponsecode() == 200) {
                        responseCallBack.onSuccess(response.body().getData());
                    } else if (response.body() != null
                            && response.body().getMessage() != null) {
                        responseCallBack.onFailure(response.body().getMessage());
                    }
                } else {
                    responseCallBack.onFailure(response.message());
                }
            }
            @Override
            public void onFailure(Call<ResponceObject<String>> call, Throwable t) {
                responseCallBack.onFailure(t.getMessage());
            }
        });
    }


    public void getCompletedEvents(String filter,final ResponseCallBack<ArrayList<PlansData>> responseCallBack) {


        Call<ResponceObject<ArrayList<PlansData>>> call = businessService.completedPlan(filter);

        call.enqueue(new Callback<ResponceObject<ArrayList<PlansData>>>() {
            @Override
            public void onResponse(Call<ResponceObject<ArrayList<PlansData>>> call,
                                   Response<ResponceObject<ArrayList<PlansData>>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null
                            && response.body().getResponsecode() == 200) {
                        responseCallBack.onSuccess(response.body().getData());
                    } else if (response.body() != null
                            && response.body().getMessage() != null) {
                        responseCallBack.onFailure(response.body().getMessage());
                    }
                } else {
                    responseCallBack.onFailure(response.message());
                }
            }
            @Override
            public void onFailure(Call<ResponceObject<ArrayList<PlansData>>> call, Throwable t) {
                responseCallBack.onFailure(t.getMessage());
            }
        });
    }


    public void unExecutedEvent(final ResponseCallBack<ArrayList<PlansData>> responseCallBack) {


        Call<ResponceObject<ArrayList<PlansData>>> call = businessService.unExecutedEvent();

        call.enqueue(new Callback<ResponceObject<ArrayList<PlansData>>>() {
            @Override
            public void onResponse(Call<ResponceObject<ArrayList<PlansData>>> call,
                                   Response<ResponceObject<ArrayList<PlansData>>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null
                            && response.body().getResponsecode() == 200) {
                        responseCallBack.onSuccess(response.body().getData());
                    } else if (response.body() != null
                            && response.body().getMessage() != null) {
                        responseCallBack.onFailure(response.body().getMessage());
                    }
                } else {
                    responseCallBack.onFailure(response.message());
                }
            }
            @Override
            public void onFailure(Call<ResponceObject<ArrayList<PlansData>>> call, Throwable t) {
                responseCallBack.onFailure(t.getMessage());
            }
        });
    }

    public void getRejectedEvent(final ResponseCallBack<ArrayList<PlansData>> responseCallBack) {


        Call<ResponceObject<ArrayList<PlansData>>> call = businessService.getRejectedEvent();

        call.enqueue(new Callback<ResponceObject<ArrayList<PlansData>>>() {
            @Override
            public void onResponse(Call<ResponceObject<ArrayList<PlansData>>> call,
                                   Response<ResponceObject<ArrayList<PlansData>>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null
                            && response.body().getResponsecode() == 200) {
                        responseCallBack.onSuccess(response.body().getData());
                    } else if (response.body() != null
                            && response.body().getMessage() != null) {
                        responseCallBack.onFailure(response.body().getMessage());
                    }
                } else {
                    responseCallBack.onFailure(response.message());
                }
            }
            @Override
            public void onFailure(Call<ResponceObject<ArrayList<PlansData>>> call, Throwable t) {
                responseCallBack.onFailure(t.getMessage());
            }
        });
    }

    public void getPendingApproval(final ResponseCallBack<ArrayList<PlansData>> responseCallBack) {


        Call<ResponceObject<ArrayList<PlansData>>> call = businessService.getPendingApproval();

        call.enqueue(new Callback<ResponceObject<ArrayList<PlansData>>>() {
            @Override
            public void onResponse(Call<ResponceObject<ArrayList<PlansData>>> call,
                                   Response<ResponceObject<ArrayList<PlansData>>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null
                            && response.body().getResponsecode() == 200) {
                        responseCallBack.onSuccess(response.body().getData());
                    } else if (response.body() != null
                            && response.body().getMessage() != null) {
                        responseCallBack.onFailure(response.body().getMessage());
                    }
                } else {
                    responseCallBack.onFailure(response.message());
                }
            }
            @Override
            public void onFailure(Call<ResponceObject<ArrayList<PlansData>>> call, Throwable t) {
                responseCallBack.onFailure(t.getMessage());
            }
        });
    }




    public void unApproved(final ResponseCallBack<ArrayList<PlansData>> responseCallBack) {


        Call<ResponceObject<ArrayList<PlansData>>> call = businessService.unApprovedEvent();

        call.enqueue(new Callback<ResponceObject<ArrayList<PlansData>>>() {
            @Override
            public void onResponse(Call<ResponceObject<ArrayList<PlansData>>> call,
                                   Response<ResponceObject<ArrayList<PlansData>>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null
                            && response.body().getResponsecode() == 200) {
                        responseCallBack.onSuccess(response.body().getData());
                    } else if (response.body() != null
                            && response.body().getMessage() != null) {
                        responseCallBack.onFailure(response.body().getMessage());
                    }
                } else {
                    responseCallBack.onFailure(response.message());
                }
            }
            @Override
            public void onFailure(Call<ResponceObject<ArrayList<PlansData>>> call, Throwable t) {
                responseCallBack.onFailure(t.getMessage());
            }
        });
    }




    public void createFeedback(CreateEventFeedback createEventFeedback, final ResponseCallBack<String> responseCallBack) {

        String json = new Gson().toJson(createEventFeedback);
        Call<ResponceObject<String>> call = businessService.createEventFeedback(createEventFeedback);

        call.enqueue(new Callback<ResponceObject<String>>() {
            @Override
            public void onResponse(Call<ResponceObject<String>> call,
                                   Response<ResponceObject<String>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null
                            && response.body().getResponsecode() == 200) {
                        responseCallBack.onSuccess(response.body().getData());
                    } else if (response.body() != null
                            && response.body().getMessage() != null) {
                        responseCallBack.onFailure(response.body().getMessage());
                    }
                } else {
                    responseCallBack.onFailure(response.message());
                }
            }
            @Override
            public void onFailure(Call<ResponceObject<String>> call, Throwable t) {
                responseCallBack.onFailure(t.getMessage());
            }
        });
    }



}
