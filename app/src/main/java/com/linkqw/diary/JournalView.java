package com.linkqw.diary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.linkqw.diary.additional.JournalSectionAdapter;
import com.linkqw.diary.database.UsersHelper;

import java.util.ArrayList;

public class JournalView extends AppCompatActivity {

    RecyclerView recyclerView;
    JournalSectionAdapter customAdapter;
    FloatingActionButton floatingActionButton;
    SharedPreferences settings;

    UsersHelper us;
    ArrayList<ArrayList<String>> firstname, lastname, status;
    ArrayList<String> title;

    public static final String FILE_NAME = "settings";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_view);

        recyclerView = findViewById(R.id.allSections);
        floatingActionButton = findViewById(R.id.switchToMonth);

        settings = getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

        us = new UsersHelper(JournalView.this);
        firstname = new ArrayList<>();
        lastname = new ArrayList<>();
        status = new ArrayList<>();
        title = new ArrayList<>();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if (settings.getBoolean("showSubs", false)) {
                    intent = new Intent(JournalView.this, TotalStatesBy.class);
                } else {
                    intent = new Intent(JournalView.this, TotalsWithoutSubjects.class);
                }
                intent.putExtra("date", getIntent().getExtras().getString("date"));
                startActivity(intent);
                finish();
            }
        });

        fillArrays();
        customAdapter = new JournalSectionAdapter(JournalView.this, firstname,
                lastname, status, title, settings.getBoolean("isLastFirst", false),
                getIntent().getExtras().getString("date"));
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

                if (!settings.getBoolean("displayIsHere", true) && splitedLine[1].equals("Был")) {
                    continue;
                }

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