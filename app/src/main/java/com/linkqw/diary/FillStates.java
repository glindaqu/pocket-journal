package com.linkqw.diary;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.linkqw.diary.database.UsersHelper;

import java.util.ArrayList;

public class FillStates extends AppCompatActivity {

    UsersHelper usersHelper;
    String subject;
    TextView title;
    int pairNumber = 0;
    ArrayList<String> names;
    int index = 1;
    int currentId = 0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_states);

        title = findViewById(R.id.curName);
        usersHelper = new UsersHelper(FillStates.this);
        names = usersHelper.getAllNames();

        String[] splited = names.get(0).split(",");
        currentId = Integer.parseInt(splited[0]);
        title.setText(splited[1]);

        Bundle args = getIntent().getExtras();
        subject = args.getString("subject");

        pairNumber = usersHelper.getCurrentPairNum(args.getString("date")) + 1;
    }

    public void fill(View view) {
        if (index < names.size()) {
            usersHelper.addToHeap(currentId, (String) ((Button)view).getText(), subject, pairNumber,
                    getIntent().getExtras().getString("date"));
            String[] splited = names.get(index).split(",");
            currentId = Integer.parseInt(splited[0]);
            title.setText(splited[1]);
            index++;
        } else {
            usersHelper.addToHeap(currentId, (String) ((Button)view).getText(), subject, pairNumber,
                    getIntent().getExtras().getString("date"));
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}