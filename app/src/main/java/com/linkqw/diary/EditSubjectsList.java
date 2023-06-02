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
import com.linkqw.diary.additional.SubjectAdapter;
import com.linkqw.diary.database.UsersHelper;

import java.util.ArrayList;

public class EditSubjectsList extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    SubjectAdapter subjectAdapter;

    UsersHelper us;
    ArrayList<String> name;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_subjects_list);

        recyclerView = findViewById(R.id.allSubs);
        floatingActionButton = findViewById(R.id.addSub);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditSubjectsList.this, SubjectAdd.class);
                startActivity(intent);
            }
        });

        us = new UsersHelper(EditSubjectsList.this);
        name = new ArrayList<>();

        fillArrays();
        subjectAdapter = new SubjectAdapter(EditSubjectsList.this, name);
        recyclerView.setAdapter(subjectAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(EditSubjectsList.this));
    }

    public void fillArrays() {
        Cursor cursor = us.getAllDataFrom("subjects");
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Nothing to display", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                name.add(cursor.getString(1));
            }
        }
    }
}