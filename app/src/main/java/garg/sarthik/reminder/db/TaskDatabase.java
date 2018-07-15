package garg.sarthik.reminder.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import garg.sarthik.reminder.Task;

@Database(entities = {Task.class}, version = 1)
public abstract class TaskDatabase extends RoomDatabase {

    public abstract TaskDao getTaskDao();
}
