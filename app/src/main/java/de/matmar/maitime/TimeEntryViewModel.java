package de.matmar.maitime;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TimeEntryViewModel extends AndroidViewModel {

    private TimeEntryRepository timeEntryRepository;

    private LiveData<List<TimeEntry>> allTimeEntries;

    public TimeEntryViewModel(Application application) {
        super(application);
        timeEntryRepository = new TimeEntryRepository((application));
        allTimeEntries = timeEntryRepository.getAllTimeEntries();
    }

    LiveData<List<TimeEntry>> getAllTimeEntries() { return allTimeEntries; }

    public void insert(TimeEntry timeEntry) { timeEntryRepository.insert(timeEntry); }


    public void deleteAll () {
        timeEntryRepository.deleteAll();
    }

    public void deleteTimeEntry(TimeEntry timeEntry) {
        timeEntryRepository.deleteTimeEntry(timeEntry);
    }

    public void update(TimeEntry timeEntry) {timeEntryRepository.update(timeEntry);}
}
