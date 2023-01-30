package com.example.visitsapp.ui;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.visitsapp.R;
import com.example.visitsapp.business.PostFeedBackResponce;
import com.example.visitsapp.business.impl.Business;
import com.example.visitsapp.db.DBHelper;
import com.example.visitsapp.db.myDbAdapter;
import com.example.visitsapp.delegate.ResponseCallBack;
import com.example.visitsapp.model.configuration.ConfigurationResponse;
import com.example.visitsapp.model.configuration.FeedbackQuestionnaire;
import com.example.visitsapp.model.request.ChangedPlan;
import com.example.visitsapp.model.request.CreateEventFeedback;
import com.example.visitsapp.model.request.CreatePlanRequest;
import com.example.visitsapp.model.request.PostFeedBackRequest;
import com.example.visitsapp.model.request.ReplaceEventRequest;
import com.example.visitsapp.model.responce.ApproveEventRequest;
import com.example.visitsapp.model.responce.GetLeavesResponce;
import com.example.visitsapp.model.responce.GetReportingTeamResponce;
import com.example.visitsapp.model.responce.LoginResponce;
import com.example.visitsapp.ui.activities.Login;
import com.example.visitsapp.ui.adapter.HistoryAdapter;
import com.example.visitsapp.ui.dialoguefragmens.CreatePlanDialogue;
import com.example.visitsapp.ui.fragments.approvefrag.ApprovalListingFrag;
import com.example.visitsapp.ui.fragments.BaseFragment;
import com.example.visitsapp.ui.fragments.approvefrag.ApprovalPendigEvents;
import com.example.visitsapp.ui.fragments.forms.DisbursementFrag;
import com.example.visitsapp.ui.fragments.forms.LACFeedbackForm;
import com.example.visitsapp.ui.fragments.myteam.MyTeamFragment;
import com.example.visitsapp.ui.fragments.myteam.TeamMemberAllEvent;
import com.example.visitsapp.ui.fragments.pending.CalenderViewFrag;
import com.example.visitsapp.ui.fragments.drawerfrag.UnApprovedEvent;
import com.example.visitsapp.ui.fragments.drawerfrag.UnExecutedEvent;
import com.example.visitsapp.ui.fragments.executionfrags.ExecutionListingFrag;
import com.example.visitsapp.ui.fragments.HomeFragment;
import com.example.visitsapp.ui.fragments.executionfrags.QuestionairePostFeedFrag;
import com.example.visitsapp.ui.fragments.executionfrags.QuestionaireReplaceEventFragment;
import com.example.visitsapp.ui.fragments.approvefrag.ReportingTeamFragment;
import com.example.visitsapp.ui.fragments.executedevent.ExecutedEventsFragment;
import com.example.visitsapp.ui.fragments.executedevent.ExecutionCompletedEvents;
import com.example.visitsapp.ui.fragments.leaves.LeavesFrag;
import com.example.visitsapp.ui.fragments.rejected.RejectedEvents;
import com.example.visitsapp.ui.fragments.viewhistory.FeedbackEventResponce;
import com.example.visitsapp.ui.fragments.viewhistory.ViewHistoryFragment;
import com.example.visitsapp.utils.SharedPrefrences;
import com.example.visitsapp.utils.alert.AlertUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    public NavigationView navigationView;
    public DrawerLayout drawer;
    public BottomNavigationView bottomNavigationView;
    public LinearLayout llcplan;
    public CircleImageView profileImage;


    private String imageUrl= null;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        syncOfflineData();


        if(SharedPrefrences.getInstance().getConfig() == null){
            getConfig();
        } else {
            ConfigurationResponse config = SharedPrefrences.getInstance().getConfig();
            if(config.events == null){
                getConfig();
            }
        }



        llcplan = findViewById(R.id.cplan);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        homeFrag();
                        bottomNavigationView.setVisibility(View.VISIBLE);
                        llcplan.setVisibility(View.VISIBLE);
                        break;

                    case R.id.plan:
                        calenderViewFrag();
                        bottomNavigationView.setVisibility(View.GONE);
                        llcplan.setVisibility(View.GONE);
                        break;

                    case R.id.execution:
                        executionFrag();
                        bottomNavigationView.setVisibility(View.GONE);
                        llcplan.setVisibility(View.GONE);
                        break;

                    case R.id.approval:
                        getRepotingTeam();
                        bottomNavigationView.setVisibility(View.VISIBLE);
                        llcplan.setVisibility(View.VISIBLE);
                        break;

                }

                return true;
            }
        });


        llcplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CreatePlanDialogue dialog = new CreatePlanDialogue(MainActivity.this );

                dialog.show(MainActivity.this.getSupportFragmentManager(), "MyDialogFragment");
            }
        });

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        drawer.closeDrawer(Gravity.LEFT);
                        homeFrag();
                        bottomNavigationView.setVisibility(View.VISIBLE);
                        llcplan.setVisibility(View.VISIBLE);
                        break;

                    case R.id.plan:
                        drawer.closeDrawer(Gravity.LEFT);
                        calenderViewFrag();
                        bottomNavigationView.setVisibility(View.GONE);
                        llcplan.setVisibility(View.GONE);
                        break;

                    case R.id.execution:
                        drawer.closeDrawer(Gravity.LEFT);
                        executionFrag();
                        bottomNavigationView.setVisibility(View.GONE);
                        llcplan.setVisibility(View.GONE);
                        break;

                    case R.id.approval:
                        drawer.closeDrawer(Gravity.LEFT);
                        getRepotingTeam();
                        bottomNavigationView.setVisibility(View.VISIBLE);
                        llcplan.setVisibility(View.VISIBLE);
                        break;

                    case R.id.leave:
                        drawer.closeDrawer(Gravity.LEFT);
                        getLeaves();
                        bottomNavigationView.setVisibility(View.VISIBLE);
                        llcplan.setVisibility(View.VISIBLE);
                        break;

                    case R.id.unapprove:
                        drawer.closeDrawer(Gravity.LEFT);
                        getUnApprovedEvents();
                        bottomNavigationView.setVisibility(View.VISIBLE);
                        llcplan.setVisibility(View.VISIBLE);
                        break;

                    case R.id.unExecuted:
                        drawer.closeDrawer(Gravity.LEFT);
                        getUnExecutedEvents();
                        bottomNavigationView.setVisibility(View.VISIBLE);
                        llcplan.setVisibility(View.VISIBLE);
                        break;

                    case R.id.myteam:
                        drawer.closeDrawer(Gravity.LEFT);
                        myTeam();
                        bottomNavigationView.setVisibility(View.GONE);
                        llcplan.setVisibility(View.GONE);
                        break;

                    case R.id.rejected:
                        drawer.closeDrawer(Gravity.LEFT);
                        rejectedEvent();
                        bottomNavigationView.setVisibility(View.VISIBLE);
                        llcplan.setVisibility(View.VISIBLE);
                        break;


                    case R.id.logout:
                        drawer.closeDrawer(Gravity.LEFT);
                        SharedPrefrences.getInstance().setConfig(null);
                        SharedPrefrences.getInstance().clearPreference();
                        startActivity(new Intent(MainActivity.this,
                                Login.class));
                        finishAffinity();
                        SharedPrefrences.getInstance().setIsLogin(false);
                        break;

                }

                return true;

            }
        });


        setProfileImage();
        homeFrag();

    }



    public void homeFrag() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof HomeFragment)) {

            HomeFragment recfrag = new HomeFragment(this, llcplan, navigationView);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_container, recfrag)
                    .commit();
        }
    }

    public void leavesFrag(ArrayList<GetLeavesResponce> pendingLeaves, ArrayList<GetLeavesResponce> approveLeaves) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof LeavesFrag)) {

            LeavesFrag recfrag = new LeavesFrag(this, pendingLeaves, approveLeaves);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, recfrag)
                    .addToBackStack("")
                    .commit();
        }
    }

    public void myTeam() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof MyTeamFragment)) {

            MyTeamFragment recfrag = new MyTeamFragment(this);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, recfrag)
                    .addToBackStack("")
                    .commit();
        }
    }

    public void getRepotingTeam() {

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof ApprovalPendigEvents)) {


            ApprovalPendigEvents recfrag = new ApprovalPendigEvents(this);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, recfrag)
                    .addToBackStack("")
                    .commit();
        }
//        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);
//
//        if (!(fragment instanceof ReportingTeamFragment)) {
//
//
//            ReportingTeamFragment recfrag = new ReportingTeamFragment(this);
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.main_container, recfrag)
//                    .addToBackStack("")
//                    .commit();
//        }
    }

    public void getUnApprovedEvents() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof UnApprovedEvent)) {
            bottomNavigationView.setVisibility(View.GONE);
            llcplan.setVisibility(View.GONE);

            UnApprovedEvent recfrag = new UnApprovedEvent(this);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, recfrag)
                    .addToBackStack("")
                    .commit();
        }
    }


    public void getUnExecutedEvents() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof UnExecutedEvent)) {
            bottomNavigationView.setVisibility(View.GONE);
            llcplan.setVisibility(View.GONE);

            UnExecutedEvent recfrag = new UnExecutedEvent(this);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, recfrag)
                    .addToBackStack("")
                    .commit();
        }
    }

    public void getHistoryEvent(String location) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof ViewHistoryFragment)) {


            ViewHistoryFragment recfrag = new ViewHistoryFragment(this, location);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, recfrag)
                    .addToBackStack("")
                    .commit();
        }
    }


    public void getFeedbackResponce(String id) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof FeedbackEventResponce)) {


            FeedbackEventResponce recfrag = new FeedbackEventResponce(this, id);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_container, recfrag)
                    .addToBackStack("")
                    .commit();
        }
    }


    public void getExecutedEvent() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof ExecutedEventsFragment )) {


            ExecutedEventsFragment recfrag = new ExecutedEventsFragment(this);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, recfrag)
                    .addToBackStack("")
                    .commit();
        }
    }

    public void getQuestionaireForm(ArrayList<FeedbackQuestionnaire> feedbackQuestionnaires, MainActivity context, int id, CreatePlanRequest createPlanRequest) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof DisbursementFrag)) {

            DisbursementFrag recfrag = new DisbursementFrag(context, feedbackQuestionnaires, id, createPlanRequest);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, recfrag)
                    .addToBackStack("")
                    .commit();
        }
//        if (!(fragment instanceof QuestionaireReplaceEventFragment)) {
//
//            QuestionaireReplaceEventFragment recfrag = new QuestionaireReplaceEventFragment(context, feedbackQuestionnaires, id, createPlanRequest);
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.main_container, recfrag)
//                    .addToBackStack("")
//                    .commit();
//        }
    }

    public void getQuestionairePostFeed(ArrayList<FeedbackQuestionnaire> feedbackQuestionnaires, MainActivity context, int id) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof LACFeedbackForm)) {

            LACFeedbackForm recfrag = new LACFeedbackForm(context, feedbackQuestionnaires, id);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, recfrag)
                    .addToBackStack("")
                    .commit();
        }

//        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);
//
//        if (!(fragment instanceof QuestionairePostFeedFrag)) {
//
//            QuestionairePostFeedFrag recfrag = new QuestionairePostFeedFrag(context, feedbackQuestionnaires, id);
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.main_container, recfrag)
//                    .addToBackStack("")
//                    .commit();
//        }
    }



    public void getPendingApproval(GetReportingTeamResponce getReportingTeamResponce) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof ApprovalListingFrag)) {

            ApprovalListingFrag recfrag = new ApprovalListingFrag(this, getReportingTeamResponce);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, recfrag)
                    .addToBackStack("")
                    .commit();
        }
    }

    public void calenderViewFrag() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof CalenderViewFrag)) {

            CalenderViewFrag recfrag = new CalenderViewFrag(this, llcplan, bottomNavigationView);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, recfrag)
                    .addToBackStack("")
                    .commit();
        }
    }

    public void teamMemberAllEvent(String id) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof TeamMemberAllEvent)) {

            TeamMemberAllEvent recfrag = new TeamMemberAllEvent(this, id, llcplan, bottomNavigationView);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, recfrag)
                    .addToBackStack("")
                    .commit();
        }
    }

    public void rejectedEvent() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof RejectedEvents)) {

            RejectedEvents recfrag = new RejectedEvents(this);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, recfrag)
                    .addToBackStack("")
                    .commit();
        }
    }


    public void executionFrag() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof ExecutionListingFrag)) {

            ExecutionListingFrag recfrag = new ExecutionListingFrag(this);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, recfrag)
                    .addToBackStack("")
                    .commit();
        }
    }

    public void executedCompletedEvent() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof ExecutionCompletedEvents)) {

            ExecutionCompletedEvents recfrag = new ExecutionCompletedEvents(this);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, recfrag)
                    .addToBackStack("")
                    .commit();
        }
    }

    public void clearBackStack() {
        FragmentManager fm = getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }



    public void onBackPressed() {
        int backStack = getSupportFragmentManager().getBackStackEntryCount();
        BaseFragment fragment = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.main_container);


        if(fragment instanceof HomeFragment){
            bottomNavigationView.setVisibility(View.VISIBLE);
            llcplan.setVisibility(View.VISIBLE);
        } else if(fragment instanceof CalenderViewFrag){
            bottomNavigationView.setVisibility(View.GONE);
            llcplan.setVisibility(View.GONE);
        } else if(fragment instanceof ExecutionListingFrag){
            bottomNavigationView.setVisibility(View.GONE);
            llcplan.setVisibility(View.GONE);
        } else if(fragment instanceof ApprovalListingFrag){
            bottomNavigationView.setVisibility(View.GONE);
            llcplan.setVisibility(View.GONE);
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (backStack > 0) {
            clearBackStack();
        } else {

        }
    }



    private void setProfileImage() {
        final AlertDialog dialog = AlertUtils.showLoader(this);

        dialog.show();
        if(SharedPrefrences.getInstance().getloginResponse() != null){
            LoginResponce loginResponce = SharedPrefrences.getInstance().getloginResponse();
            if(loginResponce.designation != null && !loginResponce.designation.isEmpty() &&
                    loginResponce.fullname != null && !loginResponce.fullname.isEmpty()){
                TextView tvname = navigationView.getHeaderView(0).findViewById(R.id.name);
                tvname.setText(loginResponce.fullname);
                TextView tvDesignation = navigationView.getHeaderView(0).findViewById(R.id.designation);
                tvDesignation.setText(loginResponce.designation);
            }
        }

        profileImage = navigationView.getHeaderView(0).findViewById(R.id.profileimage);
        if(SharedPrefrences.getInstance().getProfileImage() != null){
            Picasso.get()
                    .load(SharedPrefrences.getInstance().getProfileImage())
                    .into(profileImage);
        }
        dialog.dismiss();
    }

    private void syncOfflineData(){

        ArrayList<CreateEventFeedback> arrayList = new ArrayList<>();
        arrayList = DBHelper.getInstance().getCreateFeedback();
//
//        ReplaceEventRequest request = new ReplaceEventRequest();
//        request = dbHelper.getQuesReplaceFeedback();
//
//        PostFeedBackRequest postFeedBackRequest = new PostFeedBackRequest();
//        postFeedBackRequest = dbHelper.getQuesPostFeedback();

        String s = "d";



//        if(SharedPrefrences.getInstance().getReplaceEventFeedBack()!= null){
//            ReplaceEventRequest replaceEventRequest = SharedPrefrences.getInstance().getReplaceEventFeedBack();
//            if(replaceEventRequest.changedPlan.size() > 0 && isNetworkAvailable()){
//                replaceEventandFeedBack(replaceEventRequest);
//            }
//        }
//        if(SharedPrefrences.getInstance().getPostFeedBack() != null){
//            PostFeedBackRequest postFeedBackRequest = SharedPrefrences.getInstance().getPostFeedBack();
//            if(postFeedBackRequest.feedbacks.size() >0 && isNetworkAvailable()){
//                postFeedback(postFeedBackRequest);
//            }
//        }
    }

    private void postFeedback(PostFeedBackRequest postFeedBackRequest){
        final AlertDialog dialog = AlertUtils.showLoader(MainActivity.this);

        if (dialog != null) {
            dialog.show();
        }

        Business serviceImp = new Business() ;
        serviceImp.postFeedBack(postFeedBackRequest, new ResponseCallBack<PostFeedBackResponce>() {
            @Override
            public void onSuccess(PostFeedBackResponce body) {


                if (dialog != null) {
                    dialog.dismiss();
                }

                SharedPrefrences.getInstance().setPostFeedBack(null);
                AlertUtils.showAlert(MainActivity.this, "Feedback submitted successfully.");


            }

            @Override
            public void onFailure(String message) {
                if (dialog != null) {
                    dialog.dismiss();
                }

                AlertUtils.showAlert(MainActivity.this, message);


            }
        });
    }

    private void replaceEventandFeedBack(ReplaceEventRequest replaceEventRequest){
        final AlertDialog dialog = AlertUtils.showLoader(MainActivity.this);

        if (dialog != null) {
            dialog.show();
        }

        Business serviceImp = new Business() ;
        serviceImp.replaceevent(replaceEventRequest, new ResponseCallBack<String>() {
            @Override
            public void onSuccess(String body) {
                SharedPrefrences.getInstance().setReplaceEventFeedBack(null);

                if (dialog != null) {
                    dialog.dismiss();
                }

                AlertUtils.showAlert(MainActivity.this, "Feedback submitted successfully.");

            }

            @Override
            public void onFailure(String message) {
                if (dialog != null) {
                    dialog.dismiss();
                }

                AlertUtils.showAlert(MainActivity.this, message);


            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) MainActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void getLeaves() {
        final AlertDialog dialog = AlertUtils.showLoader(MainActivity.this);

        if (dialog != null) {
            dialog.show();
        }




        Business serviceImp = new Business() ;
        serviceImp.getLeaves( new ResponseCallBack<ArrayList<GetLeavesResponce>>() {
            @Override
            public void onSuccess(ArrayList<GetLeavesResponce> body) {


                if (dialog != null) {
                    dialog.dismiss();
                }

                ArrayList<GetLeavesResponce> pendingLeaves = new ArrayList<>();
                ArrayList<GetLeavesResponce> approveLeaves = new ArrayList<>();

                for(int i =0 ; i< body.size(); i++){
                    if(body.get(i).status.equalsIgnoreCase("pending")){
                        pendingLeaves.add(body.get(i));
                    } else {
                        approveLeaves.add(body.get(i));
                    }
                }
                leavesFrag(pendingLeaves, approveLeaves);

            }

            @Override
            public void onFailure(String message) {
                if (dialog != null) {
                    dialog.dismiss();
                }

                AlertUtils.showAlert(MainActivity.this, message);


            }
        });
    }


    public void getConfig() {

        final AlertDialog dialog = AlertUtils.showLoader(MainActivity.this);

        if (dialog != null) {
            dialog.show();
        }


        Business serviceImp = new Business() ;
        serviceImp.getConfiguration( new ResponseCallBack<ConfigurationResponse>() {
            @Override
            public void onSuccess(ConfigurationResponse body) {

                SharedPrefrences.getInstance().setConfig(body);
                if(body.user_image != null && !body.user_image.equals("")){

                    SharedPrefrences.getInstance().setProfileImage(body.user_image);

                }
                dialog.dismiss();


            }

            @Override
            public void onFailure(String message) {

                dialog.dismiss();
                AlertUtils.showAlert(MainActivity.this, message);


            }
        });
    }


}






//
//    private void setAdapter(ArrayList<String> dateList) {
//        LinearLayoutManager llm = new LinearLayoutManager(MainActivity.this);
//        llm.setOrientation(LinearLayoutManager.VERTICAL);
//        rlRecycle.setLayoutManager(llm);
//
//        cartDetail = new CartDetailAdapter(this, dateList);
//
//        rlRecycle.setAdapter(cartDetail);
//    }

//    private RecyclerView rlRecycle;
//    private CartDetailAdapter cartDetail;
//    ArrayList<String> dateList = new ArrayList<>();