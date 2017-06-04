package com.criminal.menabenyamen.taskr.repository;

import com.criminal.menabenyamen.taskr.model.Team;

import java.util.List;

/**
 * Created by menabenyamen on 2017-05-26.
 */

public interface TeamRepository {

    List<Team> getTeams();
    Team getTeam(long id);
    long addOrUpdateTeam(Team team);
    Team removeTeam(long id);

}
