package com.example.visitsapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.visitsapp.ui.MainActivity;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "OfflineHandling.db";
    public static final String QUES_POST_FEED = "quesPostFeedback";
    public static final String QUES_REPLACE_POST_FEED = "quesReplacePostFeedback";
    public static final String QUESTIONAIRE = "questionaire";

    public static final String PLANNER_EVENT_ID = "planner_event_id";
    public static final String RESULT = "result";
    public static final String QUESTION_ID = "question_id";

    public static final String PLANNED_ON = "planned_on";
    public static final String EVENT_ID = "event_id";
    public static final String PURPOSE_ID = "purpose_id";
    public static final String PURPOSE_CHILD_ID = "purpose_child_id";



    public DBHelper(MainActivity context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public boolean insertQuesPostFeedback(int planner_id){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("planner_event_id", planner_id);
        db.insert("quesPostFeedback", null, contentValues);
        return true;

    }

    public boolean insertQuesReplaceFeedback(int planner_id, String planned_on, String event_id,
                                             String purpose_id, String purpose_child_id){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("planner_event_id", planner_id);
        contentValues.put("planned_on", planned_on);
        contentValues.put("event_id", event_id);
        contentValues.put("purpose_id", purpose_id);
        contentValues.put("purpose_child_id", purpose_child_id);
        db.insert("quesReplacePostFeedback", null, contentValues);
        return true;

    }

    public boolean insertQuestionaire(int planner_id, String result, String question_id){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("planner_event_id", planner_id);
        contentValues.put("result", result);
        contentValues.put("question_id", question_id);

        db.insert("quesReplacePostFeedback", null, contentValues);
        return true;

    }
}

