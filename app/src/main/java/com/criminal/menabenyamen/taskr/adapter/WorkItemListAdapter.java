package com.criminal.menabenyamen.taskr.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.criminal.menabenyamen.taskr.R;
import com.criminal.menabenyamen.taskr.model.WorkItem;
import java.util.List;


public class WorkItemListAdapter extends RecyclerView.Adapter<WorkItemListAdapter.RecyclerViewHolder>{

   private List<WorkItem> workItems;

    public WorkItemListAdapter(List<WorkItem> workItems) {
        this.workItems = workItems;
    }



    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_list, parent, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        holder.title.setText(workItems.get(position).getTitle());
        holder.description.setText(workItems.get(position).getDescription());
        holder.state.setText(workItems.get(position).getStatus());
        holder.assignee.setText(workItems.get(position).getAssignee());

    }

    @Override
    public int getItemCount() {
        return workItems.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{

        final private TextView title;
        final private TextView description;
        final private TextView state;
        final private TextView assignee;

        public RecyclerViewHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.workitem_title);
            description = (TextView) view.findViewById(R.id.workitem_description);
            state = (TextView) view.findViewById(R.id.workitem_statusbar);
            assignee = (TextView) view.findViewById(R.id.assigned_user);
        }
    }
}
