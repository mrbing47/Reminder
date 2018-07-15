package garg.sarthik.reminder;

import android.app.Application;
import android.arch.persistence.room.Room;

import garg.sarthik.reminder.db.TaskDatabase;

public class TaskApplication extends Application {

    static TaskDatabase taskDatabase;

    public static TaskDatabase getDB() {

        return taskDatabase;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        taskDatabase = Room.databaseBuilder(getApplicationContext(),
                TaskDatabase.class,
                "task-db")
                //Thia is not a good practice bcoz never run it on the UI thread
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

    }
}
