package com.linkqw.diary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.linkqw.diary.additional.ListFillAdapter;
import com.linkqw.diary.database.UsersHelper;

import java.util.ArrayList;

public class FillList extends AppCompatActivity {

    RecyclerView recyclerView;
    ListFillAdapter customAdapter;

    UsersHelper us;
    ArrayList<String> firstname, lastname;
    ArrayList<Integer> id;

    Button end;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_list);

        recyclerView = findViewById(R.id.StateList);
        end = findViewById(R.id.endFilling);

        us = new UsersHelper(FillList.this);
        firstname = new ArrayList<>();
        lastname = new ArrayList<>();
        id = new ArrayList<>();
        fillArrays();
        customAdapter = new ListFillAdapter(FillList.this, firstname, lastname, id,
                getIntent().getExtras(), getSharedPreferences("settings", Context.MODE_PRIVATE)
                .getBoolean("isLastFirst", false));
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(FillList.this));

        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FillList.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    public void fillArrays() {
        Cursor c = us.getAllDataFrom("persons");
        while (c.moveToNext()) {
            firstname.add(c.getString(1));
            lastname.add(c.getString(2));
            id.add(c.getInt(0));
        }
    }

}