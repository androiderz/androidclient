package com.criminal.menabenyamen.taskr.api;

import android.net.Uri;
import android.util.Log;

import com.criminal.menabenyamen.taskr.model.Team;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by Mikael on 2017-06-01.
 */

public class ApiTeam {
    private static final String BASE_URL = "http://10.0.2.2:8080";
    private static final String TAG = ApiUser.class.getSimpleName();

    private final HttpClient httpClient;

    public ApiTeam() {
        this.httpClient = new HttpClient();
    }

    public Team getTeam(long id) {
        String url = getUrl("/teams/" + id);
        HttpResponse response = httpClient.get(url, null);

        if(response.getStatusCode() == 200) {
            JSONObject json = response.getResponseAsJsonObject();
            return parseTeam(json);
        }

        return null;
    }

    public List<Team> getTeams() {
        String url = getUrl("/teams/all");
        HttpResponse response = httpClient.get(url, null);

        if(response.getStatusCode() == 200) {
            JSONArray jsonArray = response.getResponseAsJsonArray();
            if (jsonArray == null) {
                return Collections.emptyList();
            }

            return parseTeams(jsonArray);
        }

        return Collections.emptyList();
    }

    public long addTeam(Team team) {
        String url = getUrl("/teams");

        RequestConfiguration requestConfiguration = new RequestConfiguration
                .Builder()
                .setBody(getTeamAsJsonString(team))
                .setHeader("Content-type", "application/json")
                .build();

        HttpResponse response = httpClient.post(url, requestConfiguration);
        String location = response.getHeaders().get("location").get(0);
        Log.d(TAG, "addTeam: " + location);

        if(response.getStatusCode() == 200) {
            Team addedTeam = parseTeam(response.getResponseAsJsonObject());
            if(addedTeam != null) {
                return addedTeam.getId();
            }
        }

        return -1;
    }

    public long updateTeam(Team team) {
        String url = getUrl("/teams/" + team.getId());
        RequestConfiguration configuration = new RequestConfiguration
                .Builder()
                .setBody(getTeamAsJsonString(team))
                .setHeader("Content-type", "application/json")
                .build();

        HttpResponse response = httpClient.put(url, configuration);

        if(response.getStatusCode() == 200) {
            return team.getId();
        }

        return -1;
    }

    public void removeTeam(long id) {
        String url = getUrl("/teams/" + id);
        HttpResponse response = httpClient.delete(url, null);

        if(response.getStatusCode() == 200) {
            Log.d(TAG, "removeTeam: SUCCESS");
        } else {
            Log.d(TAG, "removeTeam: ERROR, " + response.getResponseMessage());
        }
    }

    private String getTeamAsJsonString(Team team) {
        JSONObject jsonTeam = new JSONObject();
        try {
            jsonTeam.put("teamName", team.getTeamName());
            jsonTeam.put("teamStatus", team.getTeamStatus());


            return jsonTeam.toString(4);
        } catch (JSONException e) {
            Log.e(TAG, "getTeamAsJsonString: Error", e);
        }

        return null;
    }

    private List<Team> parseTeams(JSONArray jsonArray) {
        List<Team> teams = new ArrayList<>();

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                teams.add(parseTeam(jsonArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            Log.e(TAG, "parseTeam: Error", e);
        }

        return teams;
    }

    private Team parseTeam(JSONObject object) {
        try {
            long id = object.getLong("id");
            String teamName = object.getString("teamName");
            String teamState = object.getString("teamstatus");


            return new Team(id, teamName,teamState);
        } catch (JSONException e) {
            Log.e(TAG, "parseTeam: Error", e);
        }

        return null;
    }

    private String getUrl(String path) {
        return Uri.parse(BASE_URL)
                .buildUpon()
                .appendEncodedPath(path)
                .build()
                .toString();
    }
}
