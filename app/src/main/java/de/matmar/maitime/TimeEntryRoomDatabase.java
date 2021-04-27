package de.matmar.maitime;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {TimeEntry.class}, version = 1, exportSchema = false)
public abstract class TimeEntryRoomDatabase extends RoomDatabase {

    public abstract TimeEntryDao timeEntryDao();

    private static TimeEntryRoomDatabase INSTANCE;

    public static TimeEntryRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TimeEntryRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TimeEntryRoomDatabase.class, "timeentry_database")
                            // Wipes and rebuilds instead of migrating
                            // if no Migration object.
                            // Migration is not part of this practical.
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
