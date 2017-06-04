package com.criminal.menabenyamen.taskr.sql;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.criminal.menabenyamen.taskr.model.Team;
import com.criminal.menabenyamen.taskr.repository.TeamRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by menabenyamen on 2017-05-26.
 */

public final class SqlTeamRepository implements TeamRepository{

    private static SqlTeamRepository instance;

    public static synchronized SqlTeamRepository getInstance(Context context) {
        if(instance == null) {
            instance = new SqlTeamRepository(context);
        }

        return instance;
    }

    private final SQLiteDatabase database;

    private SqlTeamRepository(Context context) {
        database = ItemsDbHelper.getInstance(context).getWritableDatabase();
    }

    @Override
    public List<Team> getTeams() {
        TeamCursorWarpper cursor = queryTeams(null, null);

        List<Team> teams = new ArrayList<>();
        if(cursor.getCount() > 0) {
            while(cursor.moveToNext()) {
                teams.add(cursor.getTeam());
            }
        }

        cursor.close();

        return teams;
    }

    @Override
    public Team getTeam(long id) {
        TeamCursorWarpper cursor = queryTeams(ItemsDbContract.TeamsEntry._ID + " = ?", new String[] { String.valueOf(id) });

        if(cursor.getCount() > 0) {
            Team team = cursor.getFirstTeam();
            cursor.close();

            return team;
        }

        cursor.close();

        return null;
    }

    @Override
    public long addOrUpdateTeam(Team team) {
        ContentValues cv = getContentValues(team);

        if(team.hasBeenPersisted()) {
            cv.put(ItemsDbContract.TeamsEntry._ID, team.getId());
            database.update(ItemsDbContract.TeamsEntry.TABLE_NAME, cv, ItemsDbContract.TeamsEntry._ID + " = ?", new String[] { String.valueOf(team.getId()) });

            return team.getId();
        } else {
            return database.insert(ItemsDbContract.TeamsEntry.TABLE_NAME, null, cv);
        }
    }

    @Override
    public Team removeTeam(long id) {
        Team team = getTeam(id);
        database.delete(ItemsDbContract.TeamsEntry.TABLE_NAME, ItemsDbContract.TeamsEntry._ID + " = ?", new String[] { String.valueOf(id) });

        return team;
    }

    private ContentValues getContentValues(Team team) {
        ContentValues cv = new ContentValues();
        cv.put(ItemsDbContract.TeamsEntry.COLUMN_TEAM_NAME, team.getTeamName());
        cv.put(ItemsDbContract.TeamsEntry.COLUMN_TEAM_STATUS, team.getTeamStatus());

        return cv;
    }

    private TeamCursorWarpper queryTeams(String where, String[] whereArg) {
        @SuppressLint("Recycle")
        Cursor cursor = database.query(
                ItemsDbContract.TeamsEntry.TABLE_NAME,
                null,
                where,
                whereArg,
                null,
                null,
                null
        );

        return new TeamCursorWarpper(cursor);
    }
}

