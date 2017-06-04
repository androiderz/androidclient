package com.criminal.menabenyamen.taskr.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by menabenyamen on 2017-05-26.
 */

public final class ItemsDbHelper extends SQLiteOpenHelper{

    private static final String DB_NAME = "ITEMSDATABASE";
    private static final int DB_VERSION = 2;
    private static ItemsDbHelper instance;

    private static final String CREATE_TABLE_WORKITEMS =
            "CREATE TABLE " + ItemsDbContract.WorkItemsEntry.TABLE_NAME + " (" +
                    ItemsDbContract.WorkItemsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    ItemsDbContract.WorkItemsEntry.COLUMN_NAME_TITLE + " TEXT," +
                    ItemsDbContract.WorkItemsEntry.COLUMN_NAME_DESCRIPTION + " TEXT NOT NULL," +
                    ItemsDbContract.WorkItemsEntry.COLUMN_NAME_STATUS + " TEXT NOT NULL," +
                    ItemsDbContract.WorkItemsEntry.COLUMN_NAME_ASSIGNEE + " TEXT NOT NULL," +
                    ItemsDbContract.WorkItemsEntry.COLUMN_NAME_TEAM_ID + " INTEGER NOT NULL,"+
                    ItemsDbContract.WorkItemsEntry.COLUMN_NAME_USER_ID + " INTEGER NOT NULL,"+
                    "FOREIGN KEY(" + ItemsDbContract.UsersEntry._ID + ") REFERENCES " + ItemsDbContract.WorkItemsEntry.TABLE_NAME + "(" + ItemsDbContract.WorkItemsEntry.COLUMN_NAME_TEAM_ID + "), " +
                    "FOREIGN KEY(" + ItemsDbContract.TeamsEntry._ID + ") REFERENCES " + ItemsDbContract.WorkItemsEntry.TABLE_NAME + "(" + ItemsDbContract.WorkItemsEntry.COLUMN_NAME_USER_ID + "), " +
                    "FOREIGN KEY(" + ItemsDbContract.UsersEntry.COLUMN_NAME_ASSIGNEE + ") REFERENCES " + ItemsDbContract.WorkItemsEntry.TABLE_NAME + "(" + ItemsDbContract.WorkItemsEntry.COLUMN_NAME_ASSIGNEE + "));";

    private static final String CREATE_TABLE_USERS =
            "CREATE TABLE " + ItemsDbContract.UsersEntry.TABLE_NAME + " (" +
                    ItemsDbContract.UsersEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ItemsDbContract.UsersEntry.COLUMN_NAME_USER_NUMBER + " TEXT, " +
                    ItemsDbContract.UsersEntry.COLUMN_NAME_FIRSTNAME + " TEXT NOT NULL, " +
                    ItemsDbContract.UsersEntry.COLUMN_NAME_LASTNAME + " TEXT NOT NULL, " +
                    ItemsDbContract.UsersEntry.COLUMN_NAME_USER_STATE + " TEXT NOT NULL,"+
                    ItemsDbContract.UsersEntry.COLUMN_NAME_ASSIGNEE + "TEXT NOT NULL," +
                    ItemsDbContract.UsersEntry.COLUMN_NAME_TEAMID + "INTEGER NOT NULL," +
                    "FOREIGN KEY(" + ItemsDbContract.TeamsEntry._ID + ") REFERENCES " + ItemsDbContract.UsersEntry.TABLE_NAME + "(" + ItemsDbContract.UsersEntry.COLUMN_NAME_TEAMID + "));";

    private static final String CREATE_TABLE_TEAMS =
            "CREATE TABLE " + ItemsDbContract.TeamsEntry.TABLE_NAME + " (" +
                    ItemsDbContract.TeamsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    ItemsDbContract.TeamsEntry.COLUMN_TEAM_NAME + " TEXT, " +
                    ItemsDbContract.TeamsEntry.COLUMN_TEAM_STATUS + " TEXT NOT NULL);";


    private static final String DROP_TABLE_WORKITEMS =
            "DROP TABLE IF EXISTS " + ItemsDbContract.WorkItemsEntry.TABLE_NAME;

    private static final String DROP_TABLE_USERS =
            "DROP TABLE IF EXISTS " + ItemsDbContract.UsersEntry.TABLE_NAME;

    private static final String DROP_TABLE_TEAMS =
            "DROP TABLE IF EXISTS " + ItemsDbContract.TeamsEntry.TABLE_NAME;



    public static synchronized ItemsDbHelper getInstance(Context context) {
        if(instance == null) {
            instance = new ItemsDbHelper(context);
        }
        return instance;
    }

    private ItemsDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_WORKITEMS);
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_TEAMS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_WORKITEMS);
        db.execSQL(DROP_TABLE_USERS);
        db.execSQL(DROP_TABLE_TEAMS);

        onCreate(db);
    }
}
