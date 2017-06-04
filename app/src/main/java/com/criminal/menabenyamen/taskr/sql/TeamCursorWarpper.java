package com.criminal.menabenyamen.taskr.sql;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.criminal.menabenyamen.taskr.model.Team;
import com.criminal.menabenyamen.taskr.model.WorkItem;

/**
 * Created by menabenyamen on 2017-05-26.
 */

public final class TeamCursorWarpper extends CursorWrapper {

    public TeamCursorWarpper(Cursor cursor) {
        super(cursor);
    }

    public Team getTeam() {
        long id = getLong(getColumnIndexOrThrow(ItemsDbContract.TeamsEntry._ID));
        String teamName = getString(getColumnIndexOrThrow(ItemsDbContract.TeamsEntry.COLUMN_TEAM_NAME));
        String teamState = getString(getColumnIndexOrThrow(ItemsDbContract.TeamsEntry.COLUMN_TEAM_STATUS));

        return new Team(id, teamName, teamState);
    }

    public Team getFirstTeam() {
        moveToFirst();
        return getTeam();
    }
}
