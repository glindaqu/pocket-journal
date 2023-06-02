package com.linkqw.diary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.linkqw.diary.additional.CustomAdapter;
import com.linkqw.diary.additional.JournalSectionAdapter;
import com.linkqw.diary.database.UsersHelper;

import java.util.ArrayList;

public class JournalView extends AppCompatActivity {

    RecyclerView recyclerView;
    JournalSectionAdapter customAdapter;
    FloatingActionButton floatingActionButton;

    UsersHelper us;
    ArrayList<ArrayList<String>> firstname, lastname, status;
    ArrayList<String> title;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_view);

        recyclerView = findViewById(R.id.allSections);
        floatingActionButton = findViewById(R.id.switchToMonth);

        us = new UsersHelper(JournalView.this);
        firstname = new ArrayList<>();
        lastname = new ArrayList<>();
        status = new ArrayList<>();
        title = new ArrayList<>();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JournalView.this, TotalsWithoutSubjects.class);
                intent.putExtra("date", getIntent().getExtras().getString("date"));
                startActivity(intent);
            }
        });

        fillArrays();
        customAdapter = new JournalSectionAdapter(JournalView.this, firstname,
                lastname, status, title);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(JournalView.this));
    }

    public void fillArrays() {
         ArrayList<String> data = us.getAllPerformedPais(getIntent().getExtras()
                .getString("date"));

        for (int i = 0; i < data.size(); i++) {
            ArrayList<String> cur = us.getAllByPairAndDate(data.get(i).split(",")[1], getIntent().getExtras().getString("date"));
            ArrayList<String> curFirsts = new ArrayList<>();
            ArrayList<String> curLasts = new ArrayList<>();
            ArrayList<String> curStatuses = new ArrayList<>();
            for (String line : cur) {
                String[] splitedLine = line.split(",");
                curFirsts.add(us.getFirstname(Integer.parseInt(splitedLine[0])));
                curLasts.add(us.getLastname(Integer.parseInt(splitedLine[0])));
                curStatuses.add(splitedLine[1]);
            }
            firstname.add(curFirsts);
            lastname.add(curLasts);
            status.add(curStatuses);
            title.add(data.get(i).split(",")[0]);
        }
    }
}