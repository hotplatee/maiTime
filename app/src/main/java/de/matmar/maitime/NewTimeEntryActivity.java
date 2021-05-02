package de.matmar.maitime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewTimeEntryActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY =
            "de.matmar.maitime.REPLY";

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

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(editorViewDatum.getText())
                        && TextUtils.isEmpty(editorViewStunden.getText())
                        && TextUtils.isEmpty(editorViewMinuten.getText())
                        && TextUtils.isEmpty(editorViewBeschreibung.getText()) ) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    TimeEntry newTimeEntry = new TimeEntry (editorViewDatum.getText().toString(),
                            editorViewStunden.getText().toString(),
                            editorViewMinuten.getText().toString(),
                            editorViewBeschreibung.getText().toString());

                    replyIntent.putExtra(EXTRA_REPLY, newTimeEntry);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}
