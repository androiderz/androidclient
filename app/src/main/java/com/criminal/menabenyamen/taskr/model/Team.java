package com.criminal.menabenyamen.taskr.model;

/**
 * Created by menabenyamen on 2017-05-26.
 */

public final class Team {
    private static final long DEFAULT_ID = -1;

    private final long id;
    private final String teamName;
    private final String teamStatus;


    public Team(long id, String teamName, String teamStatus) {
        this.id = id;
        this.teamName = teamName;
        this.teamStatus = teamStatus;
    }

    public Team(String teamName, String teamStatus) {
        this(DEFAULT_ID, teamName, teamStatus);
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

    public String getTeamName() {
        return teamName;
    }

    public String getTeamStatus() {
        return teamStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Team team = (Team) o;

        if (id != team.id) return false;
        if (teamName != null ? !teamName.equals(team.teamName) : team.teamName != null)
            return false;
        return teamStatus != null ? teamStatus.equals(team.teamStatus) : team.teamStatus == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (teamName != null ? teamName.hashCode() : 0);
        result = 31 * result + (teamStatus != null ? teamStatus.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", teamName='" + teamName + '\'' +
                ", teamStatus='" + teamStatus + '\'' +
                '}';
    }
}
