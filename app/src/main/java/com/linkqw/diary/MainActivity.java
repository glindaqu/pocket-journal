package com.linkqw.diary;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.linkqw.diary.database.UsersHelper;
import com.linkqw.diary.shared.DateConvert;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    DatePickerDialog datePickerDialog;
    String date;

    @SuppressLint("SimpleDateFormat")
    DateFormat format = new SimpleDateFormat("yy-MM-dd");

    Boolean ready = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        date = getCurrentDate();

        ((TextView) findViewById(R.id.date)).setText(DateConvert.ConvertToHuman(getCurrentDate()));

        try (UsersHelper usersHelper = new UsersHelper(this)) {
            if (usersHelper.getAllNames().size() > 0) {
                ready = true;
            }
        }
    }

    public void startFill_onClick(View view) {
        try (UsersHelper usersHelper = new UsersHelper(this)) {
            if (usersHelper.getAllNames().size() > 0) {
                ready = true;
            }
        }
        if (ready) {
            Intent intent = new Intent(MainActivity.this, SubjectPick.class);
            intent.putExtra("date", date);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Journal is empty", Toast.LENGTH_SHORT).show();
        }
    }

    public void change_onClick(View view) {
        try (UsersHelper usersHelper = new UsersHelper(this)) {
            if (usersHelper.getAllNames().size() > 0) {
                ready = true;
            }
        }
        if (ready) {
            Intent intent = new Intent(MainActivity.this, JournalView.class);
            intent.putExtra("date", date);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Journal is empty", Toast.LENGTH_SHORT).show();
        }
    }

    public void list_onClick(View view) {
        Intent intent = new Intent(MainActivity.this, ListPick.class);
        startActivity(intent);
    }

    public void datePicker_onClick(View view) {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month++;
                String data = String.valueOf(year).substring(2) + "-" +
                        (String.valueOf(month).length() > 1 ? month : "0" + month) + "-" +
                        (String.valueOf(dayOfMonth).length() > 1 ? dayOfMonth : "0" + dayOfMonth);

                date = data;
                ((Button) findViewById(R.id.date)).setText(DateConvert.ConvertToHuman(data));
            }
        };

        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT,
                dateSetListener, year, month, day);

        datePickerDialog.show();
    }

    public void settings_click(View view) {
        Intent intent = new Intent(MainActivity.this, Settings.class);
        startActivity(intent);
    }

    public String getCurrentDate() {
        Date dateLocal = new Date();

        date = format.format(dateLocal);

        return format.format(dateLocal);
    }
}