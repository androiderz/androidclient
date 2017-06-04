package com.criminal.menabenyamen.taskr.sql;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.criminal.menabenyamen.taskr.model.User;
import com.criminal.menabenyamen.taskr.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by menabenyamen on 2017-05-26.
 */

public final class SqlUserRepository implements UserRepository{

    private static SqlUserRepository instance;

    public static synchronized SqlUserRepository getInstance(Context context) {
        if(instance == null) {
            instance = new SqlUserRepository(context);
        }

        return instance;
    }

    private final SQLiteDatabase database;

    private SqlUserRepository(Context context) {
        database = ItemsDbHelper.getInstance(context).getWritableDatabase();
    }

    @Override
    public List<User> getUsers() {
        UserCursorWarrper cursor = queryUsers(null, null);

        List<User> users = new ArrayList<>();
        if(cursor.getCount() > 0) {
            while(cursor.moveToNext()) {
                users.add(cursor.getUser());
            }
        }

        cursor.close();

        return users;
    }

    @Override
    public User getUser(long id) {
        UserCursorWarrper cursor = queryUsers(ItemsDbContract.UsersEntry._ID + " = ?", new String[] { String.valueOf(id) });

        if(cursor.getCount() > 0) {
            User user = cursor.getFirstUser();
            cursor.close();

            return user;
        }

        cursor.close();

        return null;
    }

    @Override
    public long addOrUpdateUser(User user) {
        ContentValues cv = getContentValues(user);

        if(user.hasBeenPersisted()) {
            cv.put(ItemsDbContract.UsersEntry._ID, user.getId());
            database.update(ItemsDbContract.UsersEntry.TABLE_NAME, cv, ItemsDbContract.UsersEntry._ID + " = ?", new String[] { String.valueOf(user.getId()) });

            return user.getId();
        } else {
            return database.insert(ItemsDbContract.UsersEntry.TABLE_NAME, null, cv);
        }
    }

    @Override
    public User removeUser(long id) {
        User user = getUser(id);
        database.delete(ItemsDbContract.UsersEntry.TABLE_NAME, ItemsDbContract.UsersEntry._ID + " = ?", new String[] { String.valueOf(id) });

        return user;
    }

    private ContentValues getContentValues(User user) {
        ContentValues cv = new ContentValues();
        cv.put(ItemsDbContract.UsersEntry.COLUMN_NAME_USER_NUMBER, user.getUserNumber());
        cv.put(ItemsDbContract.UsersEntry.COLUMN_NAME_FIRSTNAME, user.getFirstName());
        cv.put(ItemsDbContract.UsersEntry.COLUMN_NAME_LASTNAME, user.getLastName());
        cv.put(ItemsDbContract.UsersEntry.COLUMN_NAME_USER_STATE, user.getUserState());
        cv.put(ItemsDbContract.UsersEntry.COLUMN_NAME_ASSIGNEE, user.getAssignee());
        cv.put(ItemsDbContract.UsersEntry.COLUMN_NAME_TEAMID, user.getTeamId());

        return cv;
    }


    private UserCursorWarrper queryUsers(String where, String[] whereArg) {
        @SuppressLint("Recycle")
        Cursor cursor = database.query(
                ItemsDbContract.UsersEntry.TABLE_NAME,
                null,
                where,
                whereArg,
                null,
                null,
                null
        );

        return new UserCursorWarrper(cursor);
    }
}




