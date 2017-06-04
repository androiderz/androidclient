package com.criminal.menabenyamen.taskr.model;

/**
 * Created by menabenyamen on 2017-05-26.
 */

public final class User {

    private static final long DEFAULT_ID = -1;

    private final long id;
    private final String userNumber;
    private final String firstName;
    private final String lastName;
    private final String assignee;
    private final String userState;
    private long teamId;

    public User(long id, String userNumber, String firstName, String lastName, String assignee, String userState, long teamId) {
        this.id = id;
        this.userNumber = userNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.assignee = assignee;
        this.userState = userState;

    }

    public User(String userNumber, String firstName, String lastName, String assignee, String userState, long teamId) {
        this(DEFAULT_ID, userNumber, firstName, lastName, assignee, userState, teamId);
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

    public String getUserNumber() {
        return userNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAssignee() {
        return assignee;
    }

    public String getUserState() {
        return userState;
    }

    public long getTeamId() {
        return teamId;
    }

    public void setTeamId(long teamId) {
        this.teamId = teamId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (teamId != user.teamId) return false;
        if (userNumber != null ? !userNumber.equals(user.userNumber) : user.userNumber != null)
            return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null)
            return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null)
            return false;
        if (assignee != null ? !assignee.equals(user.assignee) : user.assignee != null)
            return false;
        return userState != null ? userState.equals(user.userState) : user.userState == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (userNumber != null ? userNumber.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (assignee != null ? assignee.hashCode() : 0);
        result = 31 * result + (userState != null ? userState.hashCode() : 0);
        result = 31 * result + (int) (teamId ^ (teamId >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userNumber='" + userNumber + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", assignee='" + assignee + '\'' +
                ", userState='" + userState + '\'' +
                ", teamId=" + teamId +
                '}';
    }
}
