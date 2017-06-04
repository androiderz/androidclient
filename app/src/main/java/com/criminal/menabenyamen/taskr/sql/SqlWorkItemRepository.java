package com.criminal.menabenyamen.taskr.sql;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.criminal.menabenyamen.taskr.model.WorkItem;
import com.criminal.menabenyamen.taskr.repository.WorkItemRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by menabenyamen on 2017-05-26.
 */

public final class SqlWorkItemRepository implements WorkItemRepository{

    public static List<WorkItem> workItems;


    private static SqlWorkItemRepository instance;

    public static synchronized SqlWorkItemRepository getInstance(Context context) {
        if(instance == null) {
            instance = new SqlWorkItemRepository(context);
        }

        return instance;
    }

    private final SQLiteDatabase database;

    public SqlWorkItemRepository(Context context) {
        database = ItemsDbHelper.getInstance(context).getWritableDatabase();

    }

    @Override
    public WorkItem getItem(long id) {
        WorkItemCursorWrapper cursor = queryUsers(ItemsDbContract.WorkItemsEntry._ID + " = ?", new String[]{String.valueOf(id)});

        if (cursor.getCount() > 0) {
            WorkItem workItem = cursor.getFirstWorkItem();
            cursor.close();

            return workItem;
        }

        cursor.close();

        return null;
    }

        @Override
        public long addWorkItem(WorkItem item) {

            ContentValues cv = getContentValues(item);


            long result = database.insert(ItemsDbContract.WorkItemsEntry.TABLE_NAME, null, cv);

            return result;
    }

    @Override
    public void removeWorkItem(final long id) {
        WorkItem workItem = getItem(id);
        database.delete(ItemsDbContract.WorkItemsEntry.TABLE_NAME, ItemsDbContract.WorkItemsEntry._ID + " = ?", new String[] { String.valueOf(id) });


    }

    private ContentValues getContentValues(WorkItem workItem) {
        ContentValues cv = new ContentValues();
        cv.put(ItemsDbContract.WorkItemsEntry.COLUMN_NAME_TITLE, workItem.getTitle());
        cv.put(ItemsDbContract.WorkItemsEntry.COLUMN_NAME_DESCRIPTION, workItem.getDescription());
        cv.put(ItemsDbContract.WorkItemsEntry.COLUMN_NAME_STATUS, workItem.getStatus());
        cv.put(ItemsDbContract.WorkItemsEntry.COLUMN_NAME_ASSIGNEE, workItem.getAssignee());
        cv.put(ItemsDbContract.WorkItemsEntry.COLUMN_NAME_TEAM_ID, workItem.getTeamId());
        cv.put(ItemsDbContract.WorkItemsEntry.COLUMN_NAME_USER_ID, workItem.getUserId());

        return cv;
    }

    private WorkItemCursorWrapper queryUsers(String where, String[] whereArg) {
        @SuppressLint("Recycle")
        Cursor cursor = database.query(
                ItemsDbContract.WorkItemsEntry.TABLE_NAME,
                null,
                where,
                whereArg,
                null,
                null,
                null
        );

        return new WorkItemCursorWrapper(cursor);
    }

    @Override
    public List<WorkItem> getAllWorkItems(){
        WorkItemCursorWrapper cursor = queryUsers(null, null);

        List<WorkItem> workItems = new ArrayList<>();
        if(cursor.getCount() > 0) {
            while(cursor.moveToNext()) {
                workItems.add(cursor.getWorkItem());
            }
        }

        cursor.close();

        return workItems;
    }

    @Override
    public List<WorkItem> getWorkItemsWhihUnstarted() {

            WorkItemCursorWrapper cursor = queryUsers(ItemsDbContract.WorkItemsEntry.COLUMN_NAME_STATUS + " = ?", new String[]{"UNSTARTED"});

        List<WorkItem> workItems = new ArrayList<>();
            if (cursor.getCount() > 0) {
               while(cursor.moveToNext()){
                   workItems.add(cursor.getWorkItem());
               }

            }

            cursor.close();

            return workItems;
        }

    @Override
    public List<WorkItem> getWorkItemsWhihStarted() {

        WorkItemCursorWrapper cursor = queryUsers(ItemsDbContract.WorkItemsEntry.COLUMN_NAME_STATUS + " = ?", new String[]{"STARTED"});

        List<WorkItem> workItems = new ArrayList<>();
        if (cursor.getCount() > 0) {
            while(cursor.moveToNext()){
                workItems.add(cursor.getWorkItem());
            }

        }

        cursor.close();

        return workItems;
    }

    @Override
    public List<WorkItem> getWorkItemsWhihDone() {

        WorkItemCursorWrapper cursor = queryUsers(ItemsDbContract.WorkItemsEntry.COLUMN_NAME_STATUS + " = ?", new String[]{"DONE"});

        List<WorkItem> workItems = new ArrayList<>();
        if (cursor.getCount() > 0) {
            while(cursor.moveToNext()){
                workItems.add(cursor.getWorkItem());
            }

        }

        cursor.close();

        return workItems;
    }

    @Override
    public long updateWorkItem(WorkItem workItem, String state) {
        return 0;
    }


    @Override
    public List<WorkItem> getWorkItemsByAssignee(String assignee) {

        WorkItemCursorWrapper cursor = queryUsers(ItemsDbContract.WorkItemsEntry.COLUMN_NAME_ASSIGNEE + " = ?", new String[]{assignee});

        List<WorkItem> workItems = new ArrayList<>();
        if (cursor.getCount() > 0) {
            while(cursor.moveToNext()){
                workItems.add(cursor.getWorkItem());
            }

        }

        cursor.close();

        return workItems;
    }


}


