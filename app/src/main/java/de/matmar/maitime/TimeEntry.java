package de.matmar.maitime;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "timeentry_table")
public class TimeEntry implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String datum;
    @NonNull
    private String stunden;
    @NonNull
    private String minuten;
    @NonNull
    private String beschreibung;


    public TimeEntry(@NonNull String datum, @NonNull String stunden,
                     @NonNull String minuten, @NonNull String beschreibung) {
        this.datum = datum;
        this.stunden = stunden;
        this.minuten = minuten;
        this.beschreibung = beschreibung;
    }
    @Ignore
    public TimeEntry(int id, @NonNull String datum, @NonNull String stunden,
                     @NonNull String minuten, @NonNull String beschreibung) {
        this.id = id;
        this.datum = datum;
        this.stunden = stunden;
        this.minuten = minuten;
        this.beschreibung = beschreibung;
    }

    public TimeEntry(TimeEntry entry) {
        this.datum = entry.getDatum();
        this.stunden = entry.getStunden();
        this.minuten = entry.getMinuten();
        this.beschreibung = entry.getBeschreibung();
    }

    public String getDatum() {
        return datum;
    }

    public String getStunden() {
        return stunden;
    }

    public String getMinuten() {
        return minuten;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTimeEntry(){
     return this.datum + " " + this.stunden +":" + this.minuten + "  " + this.beschreibung;
    }
}
