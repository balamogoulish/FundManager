package com.example.fundmanager;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {
    String user_index;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        user_index = intent.getStringExtra("user_index");

        setContentView(R.layout.activity_menu);
    }
    public void LogOut(View target){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
    public void ViewMyGain(View target){
        Intent intent = new Intent(getApplicationContext(), CheckGainActivity.class);
        intent.putExtra("user_index", user_index);
        startActivity(intent);
    }
    public void InOutMoney(View target){
        Intent intent = new Intent(getApplicationContext(), InOutActivity.class);
        intent.putExtra("user_index", user_index);
        startActivity(intent);
    }
    public void ShowMyInfo(View target){
        Intent intent = new Intent(getApplicationContext(), MyInfoActivity.class);
        intent.putExtra("user_index", user_index);
        startActivity(intent);
    }
}
