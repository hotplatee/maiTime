package de.matmar.maitime;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import static de.matmar.maitime.NewTimeEntryActivity.EXTRA_REPLY;

public class MainActivity extends AppCompatActivity {

    private TimeEntryViewModel timeEntryViewModel;
    public static final int NEW_TIMEENTRY_ACTIVITY_REQUEST_CODE = 1;

    public static final int UPDATE_TIMEENTRY_ACTIVITY_REQUEST_CODE = 2;

    public static final String EXTRA_DATA_UPDATE_TIMEENTRY = "extra_timeentry_to_be_updated";
    public static final String EXTRA_DATA_ID = "extra_data_id";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        timeEntryViewModel = ViewModelProviders.of(this).get(TimeEntryViewModel.class);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final TimeEntryListAdapter adapter = new TimeEntryListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewTimeEntryActivity.class);
                startActivityForResult(intent, NEW_TIMEENTRY_ACTIVITY_REQUEST_CODE);
            }
        });

        timeEntryViewModel.getAllTimeEntries().observe(this, new Observer<List<TimeEntry>>(){

            @Override
            public void onChanged(@Nullable List<TimeEntry> timeEntries) {
                adapter.setTimeEntry(timeEntries);
            }
        });

        // Add the functionality to swipe items in the
        // recycler view to delete that item
        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder,
                                         int direction) {
                        int position = viewHolder.getAdapterPosition();
                        TimeEntry myTimeEntry = adapter.getTimeEntryAtPosition(position);
                        Toast.makeText(MainActivity.this, "Deleting " +
                                myTimeEntry.getTimeEntry(), Toast.LENGTH_LONG).show();

                        // Delete the word
                        timeEntryViewModel.deleteTimeEntry(myTimeEntry);
                    }
                });

        helper.attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener((v, position) -> {
            TimeEntry timeEntry = adapter.getTimeEntryAtPosition(position);
            launchUpdateTimeEntrydActivity(timeEntry);
        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_TIMEENTRY_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            TimeEntry timeEntry = new TimeEntry((TimeEntry) data.getSerializableExtra(EXTRA_REPLY));
            timeEntryViewModel.insert(timeEntry);
        } else if (requestCode == UPDATE_TIMEENTRY_ACTIVITY_REQUEST_CODE
                && resultCode == RESULT_OK) {
            TimeEntry timeEntry_data = data.getParcelableExtra(NewTimeEntryActivity.EXTRA_REPLY);
            int id = data.getIntExtra(NewTimeEntryActivity.EXTRA_REPLY_ID, -1);

            if (id != -1) {
                timeEntryViewModel.update(new TimeEntry(id, timeEntry_data.getDatum(), timeEntry_data.getStunden(),
                        timeEntry_data.getMinuten(),timeEntry_data.getBeschreibung()));
            } else {
                Toast.makeText(this, R.string.unable_to_update,
                        Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

    public void launchUpdateTimeEntrydActivity(TimeEntry timeEntry) {
        Intent intent = new Intent(this, NewTimeEntryActivity.class);
        intent.putExtra(EXTRA_DATA_UPDATE_TIMEENTRY, (Parcelable) timeEntry.getTimeEntryObject());
        intent.putExtra(EXTRA_DATA_ID, timeEntry.getId());
        startActivityForResult(intent, UPDATE_TIMEENTRY_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.clear_data) {
            // Add a toast just for confirmation
            Toast.makeText(this, "Alle Zeiteinträge werden gelöscht...",
                    Toast.LENGTH_SHORT).show();

            // Delete the existing data
            timeEntryViewModel.deleteAll();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}