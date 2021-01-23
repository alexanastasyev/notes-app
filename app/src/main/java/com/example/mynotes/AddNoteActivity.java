package com.example.mynotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;

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

    private MainViewModel viewModel;

    private String dateText; // Переменная для получения даты объявлена здесь, так как метод getDate() возвращает только сегодняшнюю дату
                             // и считывание выбранной даты производится с помощью слушателя onDateChangeListener()

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(MainViewModel.class);

        calendarView = findViewById(R.id.calendarView);
        Date date = new Date(calendarView.getDate());
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM yyyy, EEEE");
        dateText = dateFormat.format(date);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // Вычитаем из года 1900, так как в параметрах этого метода (onSelectedDayChange()) год приходит в нормальном формате (2021),
                // а в конструктор Date должен передаваться год на 1900 меньший (121).
                Date date = new Date(year - 1900, month, dayOfMonth);
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
        int priority = Integer.parseInt(radioButton.getText().toString());

        if (!(title.isEmpty() || description.isEmpty())) {

            Note note = new Note(title, description, dateText, priority);
            viewModel.insertNote(note);

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        } else {
            Toast.makeText(this, getResources().getString(R.string.all_fields_must_be_filled), Toast.LENGTH_SHORT).show();
        }
    }

}