package de.matmar.maitime;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TimeEntryDao {

    @Insert
    void insert(TimeEntry timeEntry);

    @Query("DELETE FROM timeentry_table")
    void deleteAll();

    @Query("SELECT * from timeentry_table ORDER BY datum DESC")
    LiveData<List<TimeEntry>> getAllTimeEntries();


}
