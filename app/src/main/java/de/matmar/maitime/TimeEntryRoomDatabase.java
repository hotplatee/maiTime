package de.matmar.maitime;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.sql.Time;

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
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback() {

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    /**
     * Populate the database in the background.
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final TimeEntryDao timeEntryDao;
        TimeEntry entry1 = new TimeEntry("24.04.2021", "7", "30", "Sprintwechsel");
        TimeEntry entry2 = new TimeEntry("26.04.2021", "8", "30", "US#54321");
        TimeEntry [] timeEntries = {entry1, entry2};
                //"24.04.2021 8:30 Sprintwechsel", "25.04.2021 8:30 US#54321");

        PopulateDbAsync(TimeEntryRoomDatabase db) {
            timeEntryDao = db.timeEntryDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate the database
            // when it is first created
            timeEntryDao.deleteAll();

            for (int i = 0; i <= timeEntries.length -1; i++) {
                TimeEntry timeEntry = new TimeEntry(timeEntries[i]);
                timeEntryDao.insert(timeEntry);
            }
            return null;
        }
    }

}
