package garg.sarthik.reminder;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btnAdd;
    EditText etTask;
    EditText etTaskID;
    FloatingActionButton fabSearch;
    RecyclerView rvTask;

    List<Task> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTask = findViewById(R.id.etTask);
        btnAdd = findViewById(R.id.btnAdd);
        fabSearch = findViewById(R.id.fabSearch);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Task task = new Task(etTask.getText().toString(), false, 0);
                TaskApplication.getDB().getTaskDao().insertTask(task);
                taskList.add(task);

            }
        });

        final View view = LayoutInflater.from(this).inflate(R.layout.alertdialog_layout, null, true);

        final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("Enter the ID")
                .setView(view)
                .setPositiveButton("Search", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        etTaskID = view.findViewById(R.id.etTaskID);

                        ArrayList<Task> tasks = new ArrayList<>();
                        Task task = TaskApplication.getDB().getTaskDao().getDataWithId(Integer.parseInt(etTaskID.getText().toString()));
                        tasks.add(task);
                        rvTask = findViewById(R.id.rvTask);
                        rvTask.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                        rvTask.setAdapter(new TaskAdaptor(tasks, getBaseContext()));
                    }
                })
                .create();

        fabSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            alertDialog.show();

            }
        });


        taskList = TaskApplication.getDB().getTaskDao().getAllData();
        rvTask = findViewById(R.id.rvTask);
        rvTask.setLayoutManager(new LinearLayoutManager(this));
        rvTask.setAdapter(new TaskAdaptor(taskList, this));

    }
}
