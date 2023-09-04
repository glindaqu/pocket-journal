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
import com.linkqw.diary.database.UsersHelper;

import java.util.ArrayList;

public class JournalEdit extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    CustomAdapter customAdapter;

    UsersHelper us;
    ArrayList<String> firstname, lastname;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_edit);

        recyclerView = findViewById(R.id.allSections);
        floatingActionButton = findViewById(R.id.add);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JournalEdit.this, AddPerson.class);
                startActivity(intent);
                finish();
            }
        });

        us = new UsersHelper(JournalEdit.this);
        firstname = new ArrayList<>();
        lastname = new ArrayList<>();

        fillArrays();
        customAdapter = new CustomAdapter(JournalEdit.this, firstname, lastname);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(JournalEdit.this));
    }

    public void fillArrays() {
        Cursor cursor = us.getAllDataFrom("persons");
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Nothing to display", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                firstname.add(cursor.getString(1));
                lastname.add(cursor.getString(2));
            }
        }
    }
}