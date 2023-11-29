package com.example.fundmanager;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MyInfoActivity extends AppCompatActivity {
    EditText updateName, updateAccount;
    String _id;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        _id = intent.getStringExtra("_id");

        setContentView(R.layout.activity_myinfo);
        updateName = (EditText) findViewById(R.id.infonameEdit);
        updateAccount = (EditText) findViewById(R.id.infoaccountEdit);
    }

}
