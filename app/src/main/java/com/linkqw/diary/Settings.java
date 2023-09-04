package com.linkqw.diary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.CompoundButtonCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.linkqw.diary.database.UsersHelper;

public class Settings extends AppCompatActivity {

    SharedPreferences settings;
    UsersHelper usersHelper;
    public static final String FILE_NAME = "settings";

    CheckBox isOutDivide, isHere;
    ToggleButton isLastNameFirst;

    @SuppressLint({"MissingInflatedId", "ResourceAsColor"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        settings = getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        usersHelper = new UsersHelper(Settings.this);

        isOutDivide = findViewById(R.id.isOutDivide);
        isLastNameFirst = findViewById(R.id.isLastFirst);
        isHere = findViewById(R.id.displayIsHere);

        int[][] states = {{android.R.attr.state_checked}, {}};
        int[] colors = {R.color.highlight, R.color.bisque};

        CompoundButtonCompat.setButtonTintList(isOutDivide, new ColorStateList(states, colors));
        CompoundButtonCompat.setButtonTintList(isHere, new ColorStateList(states, colors));

        isOutDivide.setChecked(settings.getBoolean("showSubs", false));
        isHere.setChecked(settings.getBoolean("displayIsHere", true));
        isLastNameFirst.setChecked(settings.getBoolean("isLastFirst", false));

        isOutDivide.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"ResourceAsColor", "CommitPrefEdits"})
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = settings.edit();

                editor.putBoolean("showSubs", isOutDivide.isChecked());
                editor.apply();
            }
        });

        isHere.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"ResourceAsColor", "CommitPrefEdits"})
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = settings.edit();

                editor.putBoolean("displayIsHere", isHere.isChecked());
                editor.apply();
            }
        });

        isLastNameFirst.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = settings.edit();

                editor.putBoolean("isLastFirst", isLastNameFirst.isChecked());
                editor.apply();
            }
        });

        ((Button) findViewById(R.id.deleteHeap)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usersHelper.removeAllFromHeap();
            }
        });
    }
}