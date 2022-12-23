package com.example.visitsapp.business;

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
import com.example.visitsapp.utils.AppConstantsUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BusinessService {

    @POST(AppConstantsUtils.LOGIN)
    Call<ResponceObject<LoginResponce>> login(@Body LoginRequest loginRequestInfo);

    @POST(AppConstantsUtils.FORGOT_PASSWORD)
    Call<ResponceObject<ForgotPasswordResponce>> forgotPassword(@Body ForgotPasswordRequest forgotPasswordRequest);

    @GET(AppConstantsUtils.DASHBOARD)
    Call<ResponceObject<DashboardResponce>> getDashboard();

    @GET(AppConstantsUtils.CONFIGURATION)
    Call<ResponceObject<ConfigurationResponse>> getConfig();

    @POST(AppConstantsUtils.CREATE_EVENT)
    Call<ResponceObject<CreatePlanResponce>> createPlan(@Body CreatePlanRequest createPlanRequest);

    @POST(AppConstantsUtils.UPDATE_EVENT)
    Call<ResponceObject<UpdateEventResponce>> updateevent(@Body  UpdateEventRequest updateEventRequest);

    @GET(AppConstantsUtils.DAY_PLANNER)
    Call<ResponceObject<List<PlansData>>> dayPlanner();

    @GET(AppConstantsUtils.GET_REPORTING_TEAM)
    Call<ResponceObject<ArrayList<GetReportingTeamResponce>>> getReportingTeam();

    @GET(AppConstantsUtils.EXECUTED_PLAN)
    Call<ResponceObject<ArrayList<PlansData>>> getExecutedPlan();


    @GET("getPendingApproval/{user_id}")
    Call<ResponceObject<ArrayList<GetPendingApproval>>> getPendingApproval(@Path("user_id") int userId);

    @POST(AppConstantsUtils.APPROVE_EVENT)
    Call<ResponceObject<ApproveEventResponce>> approveEvent(@Body ApproveEventRequest approveEventRequest);

    @POST(AppConstantsUtils.POST_FEEDBACK)
    Call<ResponceObject<PostFeedBackResponce>> postFeedback(@Body PostFeedBackRequest postFeedBackRequest);


    @POST(AppConstantsUtils.REPLACEEVENTANDPOST)
    Call<ResponceObject<String>> replaceEvent(@Body ReplaceEventRequest approveEventRequest);



    @GET(AppConstantsUtils.LEAVES)
    Call<ResponceObject<ArrayList<GetLeavesResponce>>> getLeavesResponse();

    @POST(AppConstantsUtils.UPLOAD_IMAGE)
    Call<ResponceObject<String>> uploadImage(@Body UploadImageRequest uploadImageRequest);

    @GET(AppConstantsUtils.COMPLETED_PLAN)
    Call<ResponceObject<ArrayList<PlansData>>> completedPlan(@Query("period") String filter);

    @GET(AppConstantsUtils.UNAPPROVED_EVENT)
    Call<ResponceObject<ArrayList<PlansData>>> unApprovedEvent();


    @POST(AppConstantsUtils.CREATE_EVENT_FEEDBACK)
    Call<ResponceObject<String>> createEventFeedback(@Body CreateEventFeedback createEventFeedback);


    @GET(AppConstantsUtils.GET_UNEXECUTED_EVENTS)
    Call<ResponceObject<ArrayList<PlansData>>> unExecutedEvent();


}
