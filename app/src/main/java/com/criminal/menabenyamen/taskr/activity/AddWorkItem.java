package com.criminal.menabenyamen.taskr.activity;


import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.criminal.menabenyamen.taskr.R;
import com.criminal.menabenyamen.taskr.api.ApiItem;
import com.criminal.menabenyamen.taskr.model.WorkItem;
import com.criminal.menabenyamen.taskr.repository.WorkItemRepository;
import com.criminal.menabenyamen.taskr.sql.SqlWorkItemRepository;

public class AddWorkItem extends AppCompatActivity {

    private EditText title, description, state, assignee;
    private Button saveButton;
    private WorkItemRepository itemRepository;
    private String titleEdit;
    private String descriptionEdit;
    private String stateEdit;
    private String assigneeEdit;
    private Button viewButton;
    private RequestQueue mQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_work_item);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        itemRepository = new ApiItem();


        title = (EditText) findViewById(R.id.edit_title);
        description = (EditText) findViewById(R.id.edit_description);
        state = (EditText) findViewById(R.id.edit_status);
        assignee = (EditText) findViewById(R.id.edit_assignee);

        saveButton = (Button) findViewById(R.id.btn_save);
        viewButton = (Button) findViewById(R.id.btn_view_save_data);


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleEdit = title.getText().toString();
                descriptionEdit = description.getText().toString();
                stateEdit = state.getText().toString();
                assigneeEdit = assignee.getText().toString();
  itemRepository.addWorkItem(new WorkItem(titleEdit, descriptionEdit, stateEdit, assigneeEdit));
                    sendRequest(titleEdit, descriptionEdit, stateEdit, assigneeEdit);


           }
        });


    }



    public boolean caheckInternet(){
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }


    private void sendRequest(final String itemTitle, final String itemDescription, final String itemStatus, final String itemAssignee) {

        mQueue = CustomVolleyRequestQueue.getInstance(this.getApplicationContext())
                .getRequestQueue();
        final String url = "http://10.0.2.2:8080/items";

        final String inputValue = "{\"title\":\"" + itemTitle +"\","
                +"\"description\":\"" +itemDescription+"\","
                +"\"status\":\"" + itemStatus+"\","
                +"\"assignee\":\""+itemAssignee+"\"}";

        JSONObject body = null;
        try {
            body = new JSONObject(inputValue);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        itemRepository.addWorkItem(new WorkItem(itemTitle, itemDescription, itemStatus, itemAssignee));
                        Toast.makeText(getApplicationContext(), "Registering Ok", Toast.LENGTH_LONG).show();
                    }

                } , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(),Toast.LENGTH_LONG).show();
            }
        });

        jsonRequest.setTag(REQUEST_TAG);
        mQueue.add(jsonRequest);
    }

}
