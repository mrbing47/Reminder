package garg.sarthik.reminder.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import garg.sarthik.reminder.Task;

@Dao
public interface TaskDao {


    @Query("SELECT * FROM task")
    List<Task> getAllData();

    @Query("SELECT * FROM task WHERE id = :id")
    Task getDataWithId(int id);

    @Update
    void updateTask(Task task);

    @Insert
    void insertTask(Task... task);

    @Insert
    void insertTaskList(List<Task> taskList);

    @Delete
    void deleteTask(Task task);
}
