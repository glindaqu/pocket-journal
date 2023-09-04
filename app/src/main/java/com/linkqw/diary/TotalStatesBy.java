package com.linkqw.diary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.linkqw.diary.additional.CustomAdapter;
import com.linkqw.diary.additional.JournalSectionAdapter;
import com.linkqw.diary.additional.TotalAdapter;
import com.linkqw.diary.database.UsersHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TotalStatesBy extends AppCompatActivity {

    RecyclerView recyclerView;
    TotalAdapter customAdapter;
    FloatingActionButton floatingActionButton;
    Button date1, date2;

    UsersHelper us;
    ArrayList<ArrayList<String>> firstname, lastname, status;
    ArrayList<String> title;

    @SuppressLint({"MissingInflatedId", "SimpleDateFormat"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_states_by);

        recyclerView = findViewById(R.id.totals);
        floatingActionButton = findViewById(R.id.switchToDay);
        date1 = findViewById(R.id.firstDatePick);
        date2 = findViewById(R.id.secondDatePick);

        us = new UsersHelper(TotalStatesBy.this);
        firstname = new ArrayList<>();
        lastname = new ArrayList<>();
        status = new ArrayList<>();
        title = new ArrayList<>();

        if (getIntent().getExtras().getString("date1") != null) {
            date1.setText(getIntent().getExtras().getString("date1"));
        } else {
            date1.setText(new SimpleDateFormat("yy-MM-dd").format(new Date()));
        }

        if (getIntent().getExtras().getString("date2") != null) {
            date2.setText(getIntent().getExtras().getString("date2"));
        } else {
            date2.setText(new SimpleDateFormat("yy-MM-dd").format(new Date()));
        }

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TotalStatesBy.this, JournalView.class);
                intent.putExtra("date", getIntent().getExtras().getString("date"));
                startActivity(intent);
                finish();
            }
        });

        date1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month++;
                        String data = String.valueOf(year).substring(2) + "-" +
                                (String.valueOf(month).length() > 1 ? month : "0" + month) + "-" +
                                (String.valueOf(dayOfMonth).length() > 1 ? dayOfMonth : "0" + dayOfMonth);
                        date1.setText(data);

                        Intent intent = new Intent(TotalStatesBy.this, TotalStatesBy.class);
                        intent.putExtra("date1", data);
                        intent.putExtra("date2", date2.getText().toString());

                        startActivity(intent);
                        finish();
                    }
                };

                Calendar calendar = Calendar.getInstance();

                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(TotalStatesBy.this,
                        AlertDialog.THEME_HOLO_LIGHT,
                        dateSetListener, year, month, day);

                datePickerDialog.show();
            }
        });

        date2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month++;
                        String data = String.valueOf(year).substring(2) + "-" +
                                (String.valueOf(month).length() > 1 ? month : "0" + month) + "-" +
                                (String.valueOf(dayOfMonth).length() > 1 ? dayOfMonth : "0" + dayOfMonth);
                        date2.setText(data);

                        Intent intent = new Intent(TotalStatesBy.this, TotalStatesBy.class);
                        intent.putExtra("date1", date1.getText().toString());
                        intent.putExtra("date2", data);

                        startActivity(intent);
                        finish();
                    }
                };

                Calendar calendar = Calendar.getInstance();

                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(TotalStatesBy.this,
                        AlertDialog.THEME_HOLO_LIGHT,
                        dateSetListener, year, month, day);

                datePickerDialog.show();
            }
        });

        fillArrays();
        customAdapter = new TotalAdapter(TotalStatesBy.this, firstname,
                lastname, status, title,
                getSharedPreferences("settings", Context.MODE_PRIVATE)
                        .getBoolean("isLastFirst", false));
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(TotalStatesBy.this));
    }

    public void datePicker_onClick(View view) {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month++;
                String data = String.valueOf(year).substring(2) + "-" +
                        (String.valueOf(month).length() > 1 ? month : "0" + month) + "-" +
                        (String.valueOf(dayOfMonth).length() > 1 ? dayOfMonth : "0" + dayOfMonth);
                ((Button)findViewById(R.id.date)).setText(data);
            }
        };

        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT,
                dateSetListener, year, month, day);

        datePickerDialog.show();
    }

    public void fillArrays() {
        ArrayList<String> subjects = us.getAllSubjects();
        String dateEdge1 = date1.getText().toString();
        String dateEdge2 = date2.getText().toString();

        for (String subject : subjects) {
            ArrayList<String> curFirstname = new ArrayList<>();
            ArrayList<String> curLastname = new ArrayList<>();
            ArrayList<String> curStatus = new ArrayList<>();

            for (String line : us.getAllSkippedBySubject(subject, dateEdge1, dateEdge2)) {
                String status = line.split(",")[1];
                String frst = us.getFirstname(Integer.parseInt(line.split(",")[0]));
                String scnd = us.getLastname(Integer.parseInt(line.split(",")[0]));

                curFirstname.add(frst);
                curLastname.add(scnd);
                curStatus.add(status);
            }

            if (curFirstname.size() != 0 && curLastname.size() != 0) {
                curStatus.size();
                firstname.add(curFirstname);
                lastname.add(curLastname);
                status.add(curStatus);
                title.add(subject);
            }
        }
    }
}