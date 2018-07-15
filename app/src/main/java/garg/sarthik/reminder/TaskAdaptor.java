package garg.sarthik.reminder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TaskAdaptor extends RecyclerView.Adapter<TaskAdaptor.ViewHolder> {


    List<Task> taskList;
    Context context;

    public TaskAdaptor(List<Task> taskList, Context context) {
        this.taskList = taskList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.task_layout,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final Task task = taskList.get(position);

        holder.tvDescription.setText(task.getDescription());

        int color = 0;
        switch (task.getPriority())
        {
            case 0: color = holder.itemView.getResources().getColor(R.color.priority0);
                    break;
            case 1: color = holder.itemView.getResources().getColor(R.color.priority1);
                    break;
            case 2: color = holder.itemView.getResources().getColor(R.color.priority2);
                    break;
        }

        holder.tvDescription.setBackgroundColor(color);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int priority = task.getPriority();
                if(priority < 2)
                    priority++;
                else
                    priority = 0;
                task.setPriority(priority);
                notifyDataSetChanged();
                TaskApplication.getDB().getTaskDao().updateTask(task);
            }
        });

        final AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle("DO YOU WANT TO DELETE THIS TASK?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        taskList.remove(position);
                        TaskApplication.getDB().getTaskDao().deleteTask(task);
                        notifyDataSetChanged();

                        Toast.makeText(context, "Task Deleted", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create();
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                alertDialog.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvDescription;

        public ViewHolder(View itemView) {
            super(itemView);

            tvDescription = itemView.findViewById(R.id.tvDescription);
        }
    }
}
