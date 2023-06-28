package com.linkqw.diary;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.linkqw.diary.database.UsersHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    DatePickerDialog datePickerDialog;

    public static String CurrentDate;

    @SuppressLint("SimpleDateFormat")
    DateFormat format = new SimpleDateFormat("yy-MM-dd");

    Boolean ready = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((TextView)findViewById(R.id.date)).setText(getCurrentDate());

        try(UsersHelper usersHelper = new UsersHelper(this)) {
            if (usersHelper.getAllNames().size() > 0) {
                ready = true;
            }
        }
    }

    public void startFill_onClick(View view) {
        if (ready) {
            Intent intent = new Intent(MainActivity.this, SubjectPick.class);
            String d = ((Button)findViewById(R.id.date)).getText().toString();
            intent.putExtra("date", d);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Journal is empty", Toast.LENGTH_SHORT).show();
        }
    }

    public void change_onClick(View view) {
        if (ready) {
            Intent intent = new Intent(MainActivity.this, JournalView.class);
            String d = ((Button)findViewById(R.id.date)).getText().toString();
            intent.putExtra("date", d);
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

                CurrentDate = data;
                ((Button)findViewById(R.id.date)).setText(data);
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
        Date date = new Date();

        CurrentDate = format.format(date);

        return format.format(date);
    }
}