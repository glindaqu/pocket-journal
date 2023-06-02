package com.linkqw.diary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.linkqw.diary.additional.CustomAdapter;
import com.linkqw.diary.additional.JournalSectionAdapter;
import com.linkqw.diary.additional.TotalAdapter;
import com.linkqw.diary.additional.WithoutSubjectAdapter;
import com.linkqw.diary.database.UsersHelper;

import java.util.ArrayList;

public class TotalsWithoutSubjects extends AppCompatActivity {

    RecyclerView recyclerView;
    WithoutSubjectAdapter customAdapter;
    FloatingActionButton floatingActionButton;
    Button date1, date2;

    UsersHelper us;
    ArrayList<String> firstname, lastname, status;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_states_by);

        recyclerView = findViewById(R.id.totals);
        floatingActionButton = findViewById(R.id.switchToDay);
        date1 = findViewById(R.id.firstDatePick);
        date2 = findViewById(R.id.secondDatePick);

        us = new UsersHelper(TotalsWithoutSubjects.this);
        firstname = new ArrayList<>();
        lastname = new ArrayList<>();
        status = new ArrayList<>();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TotalsWithoutSubjects.this, TotalStatesBy.class);
                intent.putExtra("date", getIntent().getExtras().getString("date"));
                startActivity(intent);
            }
        });

        fillArrays();
        customAdapter = new WithoutSubjectAdapter(TotalsWithoutSubjects.this, firstname,
                lastname, status);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(TotalsWithoutSubjects.this));
    }

    public void fillArrays() {
        ArrayList<String> subjects = us.getAllSubjects();
        String dateEdge1 = date1.getText().toString();
        String dateEdge2 = date2.getText().toString();

        for (String line : us.getAllSkipped()) {
            String curStatus = line.split(",")[1];
            String frst = us.getFirstname(Integer.parseInt(line.split(",")[0]));
            String scnd = us.getLastname(Integer.parseInt(line.split(",")[0]));

            firstname.add(frst);
            lastname.add(scnd);
            status.add(curStatus);
        }
    }
}