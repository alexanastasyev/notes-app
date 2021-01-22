package com.example.mynotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddNoteActivity extends AppCompatActivity {

    private EditText editTextTitle;
    private EditText editTextDescription;
    private RadioGroup radioGroupPriority;
    private CalendarView calendarView;

    private NotesDBHelper notesDBHelper;
    private SQLiteDatabase database;

    private String dateText; // Переменная для получения даты объявлена здесь, так как метод getDate() возвращает только сегодняшнюю дату
                             // и считывание выбранной даты производится с помощью слушателя onDateChangeListener()

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Date date = new Date(year - 1900, month, dayOfMonth);
                SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM yyyy, EEEE");
                dateText = dateFormat.format(date);
            }
        });

    }

    public void onClickSaveNote(View view) {
        editTextTitle = findViewById(R.id.editTextTextTitle);
        editTextDescription = findViewById(R.id.editTextTextDescription);
        radioGroupPriority = findViewById(R.id.radioGroupPriority);

        String title = editTextTitle.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();

        int radioButtonId = radioGroupPriority.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(radioButtonId);
        int priorityIndex = Integer.parseInt(radioButton.getText().toString());
        Priority priority = null;
        switch (priorityIndex) { // Because priority is set in numbers: 1, 2 or 3. Here we translate them into Priority enum
            case 1:
                priority = Priority.HIGH;
                break;
            case 2:
                priority = Priority.MEDIUM;
                break;
            case 3:
                priority = Priority.LOW;
                break;
            default: // If something goes wrong the priority is medium by default
                priority = Priority.MEDIUM;
        }

        notesDBHelper = new NotesDBHelper(this);
        database = notesDBHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        if (!(title.isEmpty() || description.isEmpty())) {
            contentValues.put(NotesContract.NotesEntry.COLUMN_TITLE, title);
            contentValues.put(NotesContract.NotesEntry.COLUMN_DESCRIPTION, description);
            contentValues.put(NotesContract.NotesEntry.COLUMN_DATE, dateText);
            contentValues.put(NotesContract.NotesEntry.COLUMN_PRIORITY, priority.ordinal() + 1);
            database.insert(NotesContract.NotesEntry.TABLE_NAME, null, contentValues);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, getResources().getString(R.string.all_fields_must_be_filled), Toast.LENGTH_SHORT).show();
        }
    }

}