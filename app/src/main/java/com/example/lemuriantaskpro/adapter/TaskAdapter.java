package com.example.lemuriantaskpro.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lemuriantaskpro.R;
import com.example.lemuriantaskpro.model.Task;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private Context context;
    private List<Task> taskList;
    private OnTaskClickListener listener;
    private boolean isBinding = false; // Bandera

    public interface OnTaskClickListener {
        void onTaskClick(Task task);
        void onCheckBoxClick(Task task, int position);
    }

    public TaskAdapter(Context context, List<Task> taskList, OnTaskClickListener listener) {
        this.context = context;
        this.taskList = taskList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);

        isBinding = true;

        holder.tvTitle.setText(task.getName());
        holder.tvDescription.setText(task.getDescription());
        holder.rbPriority.setRating(task.getPriority());
        holder.cbCompleted.setChecked(task.getCompleted());

        isBinding = false;

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onTaskClick(task);
            }
        });

        holder.cbCompleted.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isBinding) {
                return;
            }

            if (listener != null) {
                task.setCompleted(isChecked);
                listener.onCheckBoxClick(task, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public void updateList(List<Task> newList) {
        this.taskList = newList;
        notifyDataSetChanged();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        CheckBox cbCompleted;
        TextView tvTitle, tvDescription;
        RatingBar rbPriority;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            cbCompleted = itemView.findViewById(R.id.cb_completed);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDescription = itemView.findViewById(R.id.tv_description);
            rbPriority = itemView.findViewById(R.id.rb_priority);
        }
    }
}