package com.linkqw.diary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.linkqw.diary.additional.CustomAdapter;
import com.linkqw.diary.additional.SubjectBtnAdapter;
import com.linkqw.diary.database.UsersHelper;

import java.util.ArrayList;

public class SubjectPick extends AppCompatActivity {

    RecyclerView recyclerView;
    SubjectBtnAdapter customAdapter;
    UsersHelper us;
    ArrayList<String> names;
    String date;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_pick);

        recyclerView = findViewById(R.id.pickableSubs);

        us = new UsersHelper(SubjectPick.this);
        names = new ArrayList<>();

        fillArrays();
        customAdapter = new SubjectBtnAdapter(SubjectPick.this, names);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(SubjectPick.this));

        Bundle args = getIntent().getExtras();
        date = args.getString("date");
    }

    public void fillArrays() {
        Cursor cursor = us.getAllDataFrom("subjects");
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Nothing to display. Add subject for start", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                names.add(cursor.getString(1));
            }
        }
    }

    public void startFill(View view) {
        Intent intent = new Intent(SubjectPick.this, FillList.class);
        intent.putExtra("subject", ((Button)view).getText().toString());
        intent.putExtra("date", date);
        startActivity(intent);
    }
}