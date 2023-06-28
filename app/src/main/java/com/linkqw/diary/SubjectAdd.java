package com.linkqw.diary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.linkqw.diary.database.UsersHelper;

public class SubjectAdd extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_add);

        findViewById(R.id.addSubAction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try(UsersHelper usersHelper = new UsersHelper(SubjectAdd.this)) {
                    usersHelper.addSubject(((EditText)findViewById(R.id.titleOfSubject))
                            .getText().toString());

                    Intent intent = new Intent(SubjectAdd.this, EditSubjectsList.class);
                    startActivity(intent);
                }
            }
        });
    }
}