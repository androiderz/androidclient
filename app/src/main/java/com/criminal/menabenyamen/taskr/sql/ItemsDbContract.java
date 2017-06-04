package com.criminal.menabenyamen.taskr.sql;

import android.provider.BaseColumns;

/**
 * Created by menabenyamen on 2017-05-26.
 */

public final class ItemsDbContract {

    private ItemsDbContract() {
    }

    public static class WorkItemsEntry implements BaseColumns {
        public static final String TABLE_NAME = "Workitems";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_STATUS = "status";
        public static final String COLUMN_NAME_ASSIGNEE = "assignee";
        public static final String COLUMN_NAME_TEAM_ID = "teamId";
        public static final String COLUMN_NAME_USER_ID = "userId";
    }

    public static class UsersEntry implements BaseColumns {
        public static final String TABLE_NAME = "User";
        public static final String COLUMN_NAME_USER_NUMBER = "userNumber";
        public static final String COLUMN_NAME_FIRSTNAME = "firstname";
        public static final String COLUMN_NAME_LASTNAME = "lastname";
        public static final String COLUMN_NAME_ASSIGNEE = "assignee";
        public static final String COLUMN_NAME_USER_STATE = "status";
        public static final String COLUMN_NAME_TEAMID = "teamId";

    }

    public static class TeamsEntry implements BaseColumns {
        public static final String TABLE_NAME = "TEAM";
        public static final String COLUMN_TEAM_NAME = "name";
        public static final String COLUMN_TEAM_STATUS = "description";
    }

}
