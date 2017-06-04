package com.criminal.menabenyamen.taskr.swipe;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.criminal.menabenyamen.taskr.R;
import com.criminal.menabenyamen.taskr.adapter.WorkItemListAdapter;
import com.criminal.menabenyamen.taskr.api.ApiItem;
import com.criminal.menabenyamen.taskr.model.WorkItem;
import com.criminal.menabenyamen.taskr.repository.WorkItemRepository;

import java.util.List;

/**
 * Created by menabenyamen on 2017-06-03.
 */

public class UnstartedFragment extends Fragment {

    private RecyclerView recyclerView;
    private WorkItemListAdapter adapter;
    private WorkItemRepository workItemRepository;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        workItemRepository = new ApiItem();
        return inflater.inflate(R.layout.fragment_swipe, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        recyclerView = (RecyclerView) getView().findViewById(R.id.list_unstarted);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);


        adapter = new WorkItemListAdapter(workItemRepository.getWorkItemsWhihUnstarted());
        recyclerView.setAdapter(adapter);
    }
}



