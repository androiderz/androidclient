package com.criminal.menabenyamen.taskr.model;

/**
 * Created by menabenyamen on 2017-05-26.
 */

public final class WorkItem {

    private static final long DEFAULT_ID = -1;

    private long id;
    private String title;
    private  String description;
    private  String status;
    private  String assignee;
    private  long userId;
    private  long teamId;

    public WorkItem(long id, String title, String description, String status, String assignee, long teamId, long userId) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.assignee = assignee;
        this.id = id;

    }

    public WorkItem( String title, String description, String status, String assignee) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.assignee = assignee;


    }

    public WorkItem( long id, String title, String description, String status, String assignee) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.assignee = assignee;


    }


    public WorkItem(String title, String description, String status, String assignee, long userId, long teamId) {
        this(DEFAULT_ID, title, description, status, assignee, teamId, userId);
    }

    public boolean hasBeenPersisted() {
        return id != DEFAULT_ID;
    }

    public static long getDefaultId() {
        return DEFAULT_ID;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public String getAssignee() {
        return assignee;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getTeamId() {
        return teamId;
    }

    public void setTeamId(long teamId) {
        this.teamId = teamId;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static void setStatus(String status) {status = status;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorkItem workItem = (WorkItem) o;

        if (id != workItem.id) return false;
        if (userId != workItem.userId) return false;
        if (teamId != workItem.teamId) return false;
        if (title != null ? !title.equals(workItem.title) : workItem.title != null) return false;
        if (description != null ? !description.equals(workItem.description) : workItem.description != null)
            return false;
        if (status != null ? !status.equals(workItem.status) : workItem.status != null)
            return false;
        return assignee != null ? assignee.equals(workItem.assignee) : workItem.assignee == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (assignee != null ? assignee.hashCode() : 0);
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + (int) (teamId ^ (teamId >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return title + "\n" + description + "\n" + status + "\n" + assignee;


    }
}



