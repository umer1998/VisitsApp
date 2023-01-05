package com.example.visitsapp.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.visitsapp.model.request.ChangedPlan;
import com.example.visitsapp.model.request.CreateChangedPlan;
import com.example.visitsapp.model.request.CreateEventFeedback;
import com.example.visitsapp.model.request.CreatePlanRequest;
import com.example.visitsapp.model.request.Feedback;
import com.example.visitsapp.model.request.FeedbackReplace;
import com.example.visitsapp.model.request.PostFeedBackRequest;
import com.example.visitsapp.model.request.QuesAnswer;
import com.example.visitsapp.model.request.ReplaceEventRequest;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class myDbAdapter {
    myDbHelper myhelper;
    public myDbAdapter(Context context)
    {
        myhelper = new myDbHelper(context);
    }

    public boolean insertQuesPostFeedback(int planner_id){

        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("planner_event_id", planner_id);
        db.insert("quesPostFeedback", null, contentValues);
        return true;

    }

    public boolean insertQuesReplaceFeedback(int planner_id, String planned_on, String event_id,
                                             String purpose_id, String purpose_child_id){

        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("planner_event_id", planner_id);
        contentValues.put("planned_on", planned_on);
        contentValues.put("event_id", event_id);
        contentValues.put("purpose_id", purpose_id);
        contentValues.put("purpose_child_id", purpose_child_id);
        db.insert("quesReplacePostFeedback", null, contentValues);
        return true;

    }

    public boolean insertCreateFeedback(String planned_on, String event_id,
                                             String purpose_id, String purpose_child_id){

        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        int randomNum = ThreadLocalRandom.current().nextInt(123, 13475643 + 1);
        contentValues.put("id", randomNum);
        contentValues.put("planned_on", planned_on);
        contentValues.put("event_id", event_id);
        contentValues.put("purpose_id", purpose_id);
        contentValues.put("purpose_child_id", purpose_child_id);
        db.insert("createfeedback", null, contentValues);
        return true;

    }

    public boolean insertQuestionaire(int planner_id, String result, String question_id){

        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("planner_event_id", planner_id);
        contentValues.put("result", result);
        contentValues.put("question_id", question_id);

        db.insert("questionaire ", null, contentValues);
        return true;

    }

    public ArrayList<QuesAnswer> getQuestionaire(int id){

        SQLiteDatabase db  = myhelper.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + "questionaire";
        Cursor cursor      = db.rawQuery(selectQuery, null);
        ArrayList<QuesAnswer> quesAnswerArrayList = new ArrayList<>();
        Feedback feedback = new Feedback();


        if(cursor.moveToFirst()){
            do{
                QuesAnswer quesAnswer = new QuesAnswer();
                quesAnswer.setId(Integer.valueOf(cursor.getString(1)));
                quesAnswer.setAnswer(cursor.getString(2));
                quesAnswerArrayList.add(quesAnswer);
            } while (cursor.moveToNext());
        }
        feedback.questionaire = quesAnswerArrayList;

        return quesAnswerArrayList;

    }

    public PostFeedBackRequest getQuesPostFeedback(){

        SQLiteDatabase db  = myhelper.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + "quesPostFeedback";
        Cursor cursor      = db.rawQuery(selectQuery, null);

        PostFeedBackRequest postFeedBackRequest = new PostFeedBackRequest();

        ArrayList<Feedback> feedbackArrayList = new ArrayList<>();


        if(cursor.moveToFirst()){
            do{

//                createPlanRequest.setPlanned_on(cursor.getString(0));
//                ArrayList<QuesAnswer> quesAnswers = new ArrayList<>();
//                feedback.questionaire =quesAnswers;
//                feedbackArrayList.add(feedback);
                Feedback feedback = new Feedback();
                feedback.planner_event_id = Integer.valueOf(cursor.getString(0));
                feedback.questionaire = this.getQuestionaire(Integer.valueOf(cursor.getString(0)));
                feedbackArrayList.add(feedback);

            } while(cursor.moveToNext());
        }
        postFeedBackRequest.feedbacks = feedbackArrayList;

        cursor.close();

        return postFeedBackRequest;

    }

    public ReplaceEventRequest getQuesReplaceFeedback(){
        SQLiteDatabase db  = myhelper.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + "quesReplacePostFeedback";
        Cursor cursor      = db.rawQuery(selectQuery, null);
        ArrayList<ChangedPlan> changedPlanArrayList = new ArrayList<>();
        ReplaceEventRequest request = new ReplaceEventRequest();
        if(cursor.moveToFirst()){
            do{

                ChangedPlan changedPlan = new ChangedPlan();
                changedPlan.plannerEventId = Integer.valueOf(cursor.getString(0));

                CreatePlanRequest createPlanRequest = new CreatePlanRequest();
                ArrayList<FeedbackReplace> feedbackReplaces = new ArrayList<>();

                createPlanRequest.setPlanned_on(cursor.getString(1));
                createPlanRequest.setEvent_id(cursor.getString(2));
                createPlanRequest.setPurpose_id(cursor.getString(3));
                createPlanRequest.setPurpose_child_id(cursor.getString(4));

                changedPlan.new_event = createPlanRequest;
                changedPlan.feedbacks = feedbackReplaces;

                changedPlanArrayList.add(changedPlan);

            } while(cursor.moveToNext());
        }

        request.changedPlan = changedPlanArrayList;
        cursor.close();

        return request;

    }

    public ArrayList<CreateEventFeedback> getCreateFeedback(){


        SQLiteDatabase db  = myhelper.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + "createfeedback";
        Cursor cursor      = db.rawQuery(selectQuery, null);
        ArrayList<CreateEventFeedback> createEventFeedbackArrayList = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{

                CreateEventFeedback createEventFeedback = new CreateEventFeedback();
                ArrayList<CreateChangedPlan> createChangedPlanArrayList = new ArrayList<>();

                CreatePlanRequest createPlanRequest = new CreatePlanRequest();
                ArrayList<FeedbackReplace> feedbackReplaces = new ArrayList<>();


                FeedbackReplace feedbackReplace = new FeedbackReplace();
                feedbackReplace.questionaire = this.getQuestionaire(Integer.valueOf(cursor.getString(0)));
                feedbackReplaces.add(feedbackReplace);

                createPlanRequest.setPlanned_on(cursor.getString(1));
                createPlanRequest.setEvent_id(cursor.getString(2));
                createPlanRequest.setPurpose_id(cursor.getString(3));
                createPlanRequest.setPurpose_child_id(cursor.getString(4));

                CreateChangedPlan createChangedPlan = new CreateChangedPlan();
                createChangedPlan.new_event = createPlanRequest;
                createChangedPlan.feedbacks = feedbackReplaces;
                createChangedPlanArrayList.add(createChangedPlan);

                createEventFeedback.changedPlan = createChangedPlanArrayList;

                createEventFeedbackArrayList.add(createEventFeedback);


            } while(cursor.moveToNext());
        }

        cursor.close();
        return createEventFeedbackArrayList;

    }




//    public long insertData(String name, String pass)
//    {
//        SQLiteDatabase dbb = myhelper.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(myDbHelper.NAME, name);
//        contentValues.put(myDbHelper.MyPASSWORD, pass);
//        long id = dbb.insert(myDbHelper.TABLE_NAME, null , contentValues);
//        return id;
//    }
//
//    public String getData()
//    {
//        SQLiteDatabase db = myhelper.getWritableDatabase();
//        String[] columns = {myDbHelper.UID,myDbHelper.NAME,myDbHelper.MyPASSWORD};
//        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,null);
//        StringBuffer buffer= new StringBuffer();
//        while (cursor.moveToNext())
//        {
//            @SuppressLint("Range") int cid =cursor.getInt(cursor.getColumnIndex(myDbHelper.UID));
//            @SuppressLint("Range") String name =cursor.getString(cursor.getColumnIndex(myDbHelper.NAME));
//            @SuppressLint("Range") String  password =cursor.getString(cursor.getColumnIndex(myDbHelper.MyPASSWORD));
//            buffer.append(cid+ "   " + name + "   " + password +" \n");
//        }
//        return buffer.toString();
//    }
//
//    public  int delete(String uname)
//    {
//        SQLiteDatabase db = myhelper.getWritableDatabase();
//        String[] whereArgs ={uname};
//
//        int count =db.delete(myDbHelper.TABLE_NAME ,myDbHelper.NAME+" = ?",whereArgs);
//        return  count;
//    }
//
//    public int updateName(String oldName , String newName)
//    {
//        SQLiteDatabase db = myhelper.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(myDbHelper.NAME,newName);
//        String[] whereArgs= {oldName};
//        int count =db.update(myDbHelper.TABLE_NAME,contentValues, myDbHelper.NAME+" = ?",whereArgs );
//        return count;
//    }

    static class myDbHelper extends SQLiteOpenHelper
    {
        public static final String DATABASE_NAME = "OfflineHandling.db";
        public static final String QUES_POST_FEED = "quesPostFeedback";
        public static final String QUES_REPLACE_POST_FEED = "quesReplacePostFeedback";
        public static final String QUESTIONAIRE = "questionaire";
        public static final String CREATE_FEEDBACK = "createfeedback";

        public static final String PLANNER_EVENT_ID = "planner_event_id";
        public static final String RESULT = "result";
        public static final String QUESTION_ID = "question_id";

        public static final String PLANNED_ON = "planned_on";
        public static final String EVENT_ID = "event_id";
        public static final String PURPOSE_ID = "purpose_id";
        public static final String PURPOSE_CHILD_ID = "purpose_child_id";
        private Context context;

        public myDbHelper(Context context) {
            super(context, DATABASE_NAME, null, 1);
            SQLiteDatabase db = this.getWritableDatabase();
            this.context=context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            try {
//                db.execSQL(
//                        "create table quesPostFeedback " +
//                                "(planner_event_id integer primary key)"
//                );
//                db.execSQL(
//                        "create table quesReplacePostFeedback " +
//                                "(planner_event_id integer primary key, planned_on text,event_id text,purpose_id text," +
//                                " purpose_child_id text)"
//                );
                db.execSQL(
                        "create table createfeedback " +
                                "(planned_on text,event_id text,purpose_id text," +
                                " purpose_child_id text)"
                );
//                db.execSQL(
//                        "create table questionaire " +
//                                "(planner_event_id integer primary key,result text,question_id text)"
//                );
            } catch (Exception e) {
                String re= "e";
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
