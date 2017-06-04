package com.criminal.menabenyamen.taskr.api;

import android.net.Uri;
import android.util.Log;

import com.criminal.menabenyamen.taskr.model.WorkItem;
import com.criminal.menabenyamen.taskr.repository.WorkItemRepository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by Mikael on 2017-06-01.
 */

    public class ApiItem implements WorkItemRepository{

    private static final String BASE_URL = "http://10.0.2.2:8080";
    private static final String TAG = ApiUser.class.getSimpleName();

    private final HttpClient httpClient;

    public ApiItem() {
        this.httpClient = new HttpClient();
    }

    @Override
    public WorkItem getItem(long id) {
        String url = getUrl("items/" + id);
        HttpResponse response = httpClient.get(url, null);

        if(response.getStatusCode() == 200) {
            JSONObject json = response.getResponseAsJsonObject();
            return parseItem(json);
        }

        return null;
    }

        @Override
        public List<WorkItem> getAllWorkItems() {
        String url = getUrl("items/all/items");
        HttpResponse response = httpClient.get(url, null);

        if(response.getStatusCode() == 200) {
            JSONArray jsonArray = response.getResponseAsJsonArray();
            if (jsonArray == null) {
                return Collections.emptyList();
            }

            return parseItems(jsonArray);
        }

        return Collections.emptyList();
    }

    @Override
    public long addWorkItem(WorkItem workItem) {
        String url = getUrl("items");

        RequestConfiguration requestConfiguration = new RequestConfiguration
                .Builder()
                .setBody(getItemAsJsonString(workItem))
                .setHeader("Content-type", "application/json")
                .build();

        HttpResponse response = httpClient.post(url, requestConfiguration);
        String location = response.getHeaders().get("location").get(0);
        Log.d(TAG, "addWorkItem: " + location);

        if(response.getStatusCode() == 200) {
            WorkItem addedItem = parseItem(response.getResponseAsJsonObject());
            if(addedItem != null) {
                return addedItem.getId();
            }
        }

        return -1;
    }

    @Override
    public long updateWorkItem(WorkItem workItem, String state) {
        String url = getUrl("items/" +  workItem.getId());
        RequestConfiguration configuration = new RequestConfiguration
                .Builder()
                .setBody(getItemAsJsonString( workItem))
                .setHeader("Content-type", "application/json")
                .build();

        HttpResponse response = httpClient.put(url, configuration);

        if(response.getStatusCode() == 200) {
            return  workItem.getId();
        }

        return -1;
    }



    @Override
    public void removeWorkItem(long id) {
        String url = getUrl("itmes/remove/" + id);
        HttpResponse response = httpClient.delete(url, null);

        if(response.getStatusCode() == 200) {
            Log.d(TAG, "removeWorkItem: SUCCESS");
        } else {
            Log.d(TAG, "removeWorkItem: ERROR, " + response.getResponseMessage());
        }
    }

    @Override
    public List<WorkItem> getWorkItemsWhihUnstarted(){
        String url = getUrl("items/state/UNSTARTED");
        HttpResponse response = httpClient.get(url, null);

        if(response.getStatusCode() == 200) {
            JSONArray jsonArray = response.getResponseAsJsonArray();
            if (jsonArray == null) {
                return Collections.emptyList();
            }

            return parseItems(jsonArray);
        }

        return Collections.emptyList();
    }

    @Override
    public List<WorkItem> getWorkItemsWhihStarted(){
        String url = getUrl("items/state/STARTED");
        HttpResponse response = httpClient.get(url, null);

        if(response.getStatusCode() == 200) {
            JSONArray jsonArray = response.getResponseAsJsonArray();
            if (jsonArray == null) {
                return Collections.emptyList();
            }

            return parseItems(jsonArray);
        }

        return Collections.emptyList();
    }

    @Override
    public List<WorkItem> getWorkItemsByAssignee(String assignee){
        String url = getUrl("items/view/" + assignee);

        HttpResponse response = httpClient.get(url, null);

        if( response.getStatusCode() == 200) {
            JSONArray jsonArray = response.getResponseAsJsonArray();
            if (jsonArray == null) {
                return Collections.emptyList();
            }

            return parseItems(jsonArray);
        }

        return Collections.emptyList();
    }

    public List<WorkItem> getWorkItemsWhihDone(){
        String url = getUrl("items/state/DONE");
        HttpResponse response = httpClient.get(url, null);

        if(response.getStatusCode() == 200) {
            JSONArray jsonArray = response.getResponseAsJsonArray();
            if (jsonArray == null) {
                return Collections.emptyList();
            }

            return parseItems(jsonArray);
        }

        return Collections.emptyList();
    }



    private String getItemAsJsonString(WorkItem workItem) {
        JSONObject jsonItem = new JSONObject();
        try {
            jsonItem.put("title", workItem.getTitle());
            jsonItem.put("description", workItem.getDescription());
            jsonItem.put("status", workItem.getStatus());
            jsonItem.put("assignee", workItem.getAssignee());
            jsonItem.put("userId", workItem.getUserId());
            jsonItem.put("teamId", workItem.getTeamId());

            return jsonItem.toString(6);
        } catch (JSONException e) {
            Log.e(TAG, "getItemAsJsonString: Error", e);
        }

        return null;
    }

    private List<WorkItem> parseItems(JSONArray jsonArray) {
        List<WorkItem> items = new ArrayList<>();

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                items.add(parseItem(jsonArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            Log.e(TAG, "parseItems: Error", e);
        }

        return items;
    }

    private WorkItem parseItem(JSONObject object) {
        try {
            long id = object.getLong("id");
            String title = object.getString("title");
            String description = object.getString("description");
            String state = object.getString("status");
            String assignee = object.getString("assignee");

            return new WorkItem(id, title, description, state, assignee);
        } catch (JSONException e) {
            Log.e(TAG, "parseWorkItem: Error", e);
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
