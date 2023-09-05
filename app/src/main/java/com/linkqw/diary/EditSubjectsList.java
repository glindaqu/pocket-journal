package com.linkqw.diary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
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
    ArrayList<Integer> id;

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int delete_id = viewHolder.getAdapterPosition();
            name.remove(delete_id);
            subjectAdapter.notifyDataSetChanged();
            try(UsersHelper usersHelper = new UsersHelper(EditSubjectsList.this)) {
                if (!usersHelper.delete_from(id.get(delete_id), "subjects")) {
                    Toast.makeText(EditSubjectsList.this, "error while delete. subject", Toast.LENGTH_SHORT).show();
                }
                if (!usersHelper.delete_from_heap_by_subject(id.get(delete_id))) {
                    Toast.makeText(EditSubjectsList.this, "error while delete. heap", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(EditSubjectsList.this, "error while delete: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            id.remove(delete_id);
        }
    };

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
                finish();
            }
        });

        us = new UsersHelper(EditSubjectsList.this);
        name = new ArrayList<>();
        id = new ArrayList<>();

        fillArrays();
        subjectAdapter = new SubjectAdapter(EditSubjectsList.this, name);
        recyclerView.setAdapter(subjectAdapter);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(EditSubjectsList.this));
    }

    public void fillArrays() {
        Cursor cursor = us.getAllDataFrom("subjects");
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Nothing to display", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                name.add(cursor.getString(1));
                id.add(cursor.getInt(0));
            }
        }
    }
}