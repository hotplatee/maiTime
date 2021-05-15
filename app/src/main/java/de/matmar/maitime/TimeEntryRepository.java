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

    public void deleteAll(){
        new deleteAllTimeEntriesAsyncTask(timeEntryDao).execute();
    }

    public void deleteTimeEntry(TimeEntry timeEntry)  {
        new deleteTimeEntryAsyncTask(timeEntryDao).execute(timeEntry);
    }

    public void update(TimeEntry timeEntry)  {
        new updateTimeEntryAsyncTask(timeEntryDao).execute(timeEntry);
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

    private static class deleteAllTimeEntriesAsyncTask extends AsyncTask<Void, Void, Void> {
        private TimeEntryDao mAsyncTaskDao;

        deleteAllTimeEntriesAsyncTask(TimeEntryDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    private static class deleteTimeEntryAsyncTask extends AsyncTask<TimeEntry, Void, Void> {
        private TimeEntryDao mAsyncTaskDao;

        deleteTimeEntryAsyncTask(TimeEntryDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final TimeEntry... params) {
            mAsyncTaskDao.deleteTimeEntry(params[0]);
            return null;
        }
    }

    private static class updateTimeEntryAsyncTask extends AsyncTask<TimeEntry, Void, Void> {
        private TimeEntryDao mAsyncTaskDao;

        updateTimeEntryAsyncTask(TimeEntryDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final TimeEntry... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }


}
