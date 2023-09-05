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
import com.linkqw.diary.additional.CustomAdapter;
import com.linkqw.diary.database.UsersHelper;

import java.util.ArrayList;

public class JournalEdit extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    CustomAdapter customAdapter;

    UsersHelper us;
    ArrayList<String> firstname, lastname;
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
            firstname.remove(delete_id);
            lastname.remove(delete_id);

            try(UsersHelper usersHelper = new UsersHelper(JournalEdit.this)) {
                if (!usersHelper.delete_from(id.get(delete_id), "persons")) {
                    Toast.makeText(JournalEdit.this, "error while delete. person", Toast.LENGTH_SHORT).show();
                }
                if (!usersHelper.delete_from_heap_by_person(id.get(delete_id))) {
                    Toast.makeText(JournalEdit.this, "error while delete. heap", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(JournalEdit.this, "error while delete: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            id.remove(delete_id);
            customAdapter.notifyDataSetChanged();
        }
    };

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
        id = new ArrayList<>();

        fillArrays();
        customAdapter = new CustomAdapter(JournalEdit.this, firstname, lastname);
        recyclerView.setAdapter(customAdapter);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(JournalEdit.this));
    }

    public void fillArrays() {
        Cursor cursor = us.getAllDataFrom("persons");
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Nothing to display", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                id.add(cursor.getInt(0));
                firstname.add(cursor.getString(1));
                lastname.add(cursor.getString(2));
            }
        }
    }
}