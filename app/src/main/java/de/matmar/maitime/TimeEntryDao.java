package de.matmar.maitime;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TimeEntryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(TimeEntry timeEntry);

    @Query("DELETE FROM timeentry_table")
    void deleteAll();

    @Query("SELECT * from timeentry_table ORDER BY datum DESC")
    LiveData<List<TimeEntry>> getAllTimeEntries();

    @Query("SELECT * FROM timeentry_table LIMIT 1")
    TimeEntry[] getAnyTimeEntry();

    @Delete
    void deleteTimeEntry(TimeEntry timeEntry);

    @Update
    void update(TimeEntry... timeEntry);
}
