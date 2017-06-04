package com.criminal.menabenyamen.taskr.sql;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.criminal.menabenyamen.taskr.model.WorkItem;

/**
 * Created by menabenyamen on 2017-05-26.
 */

public final class WorkItemCursorWrapper extends CursorWrapper{


    public WorkItemCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public WorkItem getWorkItem() {
        long id = getLong(getColumnIndexOrThrow(ItemsDbContract.WorkItemsEntry._ID));
        String title = getString(getColumnIndexOrThrow(ItemsDbContract.WorkItemsEntry.COLUMN_NAME_TITLE));
        String descritpion = getString(getColumnIndexOrThrow(ItemsDbContract.WorkItemsEntry.COLUMN_NAME_DESCRIPTION));
        String state = getString(getColumnIndexOrThrow(ItemsDbContract.WorkItemsEntry.COLUMN_NAME_STATUS));
        String assignee = getString(getColumnIndexOrThrow(ItemsDbContract.WorkItemsEntry.COLUMN_NAME_ASSIGNEE));
        long teamId = getLong(getColumnIndexOrThrow(ItemsDbContract.WorkItemsEntry.COLUMN_NAME_TEAM_ID));
        long userId = getLong(getColumnIndexOrThrow(ItemsDbContract.WorkItemsEntry.COLUMN_NAME_USER_ID));



        return new WorkItem(id, title, descritpion, state, assignee, teamId, userId);
    }

    public WorkItem getFirstWorkItem() {
        moveToFirst();
        return getWorkItem();
    }
}
