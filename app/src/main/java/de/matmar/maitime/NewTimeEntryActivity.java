package de.matmar.maitime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static de.matmar.maitime.MainActivity.EXTRA_DATA_ID;
import static de.matmar.maitime.MainActivity.EXTRA_DATA_UPDATE_TIMEENTRY;

public class NewTimeEntryActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY =
            "de.matmar.maitime.REPLY";
    public static final String EXTRA_REPLY_ID = "de.matmar.maitime.REPLY_ID";;

    private EditText editorViewDatum;
    private EditText editorViewStunden;
    private EditText editorViewMinuten;
    private EditText editorViewBeschreibung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_time_entry);
        editorViewDatum = findViewById(R.id.edit_datum);
        editorViewStunden = findViewById(R.id.edit_stunden);
        editorViewMinuten = findViewById(R.id.edit_minuten);
        editorViewBeschreibung = findViewById(R.id.edit_beschreibung);
        int id = -1 ;

        final Bundle extras = getIntent().getExtras();

        // If we are passed content, fill it in for the user to edit.
        if (extras != null) {
            TimeEntry timeEntry = extras.getParcelable(EXTRA_DATA_UPDATE_TIMEENTRY);
            if (!timeEntry.getDatum().toString().isEmpty()) {
                editorViewDatum.setText(timeEntry.getDatum());
                editorViewStunden.setText(timeEntry.getStunden());
                editorViewMinuten.setText(timeEntry.getMinuten());
                editorViewBeschreibung.setText(timeEntry.getBeschreibung());
                editorViewBeschreibung.setSelection(timeEntry.getBeschreibung().length());
                editorViewBeschreibung.requestFocus();
            }
        } // Otherwise, start with empty fields.

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(editorViewDatum.getText())
                        || TextUtils.isEmpty(editorViewStunden.getText())
                        || TextUtils.isEmpty(editorViewMinuten.getText())
                        || TextUtils.isEmpty(editorViewBeschreibung.getText()) ) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    TimeEntry newTimeEntry = new TimeEntry (editorViewDatum.getText().toString(),
                            editorViewStunden.getText().toString(),
                            editorViewMinuten.getText().toString(),
                            editorViewBeschreibung.getText().toString());

                    replyIntent.putExtra(EXTRA_REPLY, (Parcelable) newTimeEntry);
                    if (extras != null && extras.containsKey(EXTRA_DATA_ID)) {
                        int id = extras.getInt(EXTRA_DATA_ID, -1);
                        if (id != -1) {
                            replyIntent.putExtra(EXTRA_REPLY_ID, id);
                        }
                    }
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}
