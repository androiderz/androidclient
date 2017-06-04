package com.criminal.menabenyamen.taskr.sql;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.criminal.menabenyamen.taskr.model.User;
import com.criminal.menabenyamen.taskr.model.WorkItem;

/**
 * Created by menabenyamen on 2017-05-26.
 */

public final class UserCursorWarrper extends CursorWrapper{

    public UserCursorWarrper(Cursor cursor) {
        super(cursor);
    }

    public User getUser() {
        long id = getLong(getColumnIndexOrThrow(ItemsDbContract.UsersEntry._ID));
        String userNumber = getString(getColumnIndexOrThrow(ItemsDbContract.UsersEntry.COLUMN_NAME_USER_NUMBER));
        String firstName = getString(getColumnIndexOrThrow(ItemsDbContract.UsersEntry.COLUMN_NAME_FIRSTNAME));
        String lastName = getString(getColumnIndexOrThrow(ItemsDbContract.UsersEntry.COLUMN_NAME_LASTNAME));
        String userState = getString(getColumnIndexOrThrow(ItemsDbContract.UsersEntry.COLUMN_NAME_USER_NUMBER));
        String assignee = getString(getColumnIndexOrThrow(ItemsDbContract.UsersEntry.COLUMN_NAME_ASSIGNEE));
        long teamId = getLong(getColumnIndexOrThrow(ItemsDbContract.UsersEntry.COLUMN_NAME_TEAMID));
        return new User(id, userNumber, firstName, lastName, userState, assignee, teamId);
    }

    public User getFirstUser() {
        moveToFirst();
        return getUser();
    }
}
