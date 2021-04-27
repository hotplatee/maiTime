package de.matmar.maitime;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.sql.Time;
import java.util.List;

public class TimeEntryRepository {

    private TimeEntryDao timeEntryDao;
    private LiveData<List<TimeEntry>> allTimeEntries;

    TimeEntryRepository(Application application) {
        TimeEntryRoomDatabase db = TimeEntryRoomDatabase.getDatabase(application);
        timeEntryDao = db.timeEntryDao();
        allTimeEntries = timeEntryDao.getAllTimeEntries();
    }

    LiveData<List<TimeEntry>> getAllTimeEntries() {
        return allTimeEntries;
    }

    public void insert (TimeEntry timeEntry) {
        new insertAsyncTask(timeEntryDao).execute(timeEntry);
    }

    private static class insertAsyncTask extends    AsyncTask<TimeEntry, Void, Void> {

        private TimeEntryDao mAsyncTaskDao;

        insertAsyncTask(TimeEntryDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final TimeEntry... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
