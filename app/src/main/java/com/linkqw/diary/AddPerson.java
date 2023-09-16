package com.linkqw.diary;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.linkqw.diary.database.UsersHelper;

public class AddPerson extends AppCompatActivity {

    EditText firstname;
    EditText lastname;
    Button btn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);

        firstname = findViewById(R.id.fname);
        lastname = findViewById(R.id.lname);
        btn = findViewById(R.id.addPerson);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try (UsersHelper uh = new UsersHelper(AddPerson.this)) {
                    uh.addNewPerson(firstname.getText().toString().trim(),
                            lastname.getText().toString().trim());

                    Intent intent = new Intent(AddPerson.this, JournalEdit.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}