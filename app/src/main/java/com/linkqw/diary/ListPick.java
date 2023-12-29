package com.linkqw.diary;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ListPick extends AppCompatActivity {

    Button personsList;
    Button subjectsList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pick);

        personsList = findViewById(R.id.personsListEdit);
        subjectsList = findViewById(R.id.subjectsListEdit);

        personsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListPick.this, JournalEdit.class);
                startActivity(intent);
            }
        });

        subjectsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListPick.this, EditSubjectsList.class);
                startActivity(intent);
            }
        });
    }
}