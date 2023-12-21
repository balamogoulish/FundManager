package com.example.fundmanager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MyInfoActivity extends AppCompatActivity {
    TextView name, account, id;
    String user_index;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo);
        Intent intent = getIntent();
        user_index = intent.getStringExtra("user_index");

        name = findViewById(R.id.nameResult);
        account = findViewById(R.id.accountResult);
        id = findViewById(R.id.idResult);

        showMyInfo();
    }
    public void showMyInfo(){
        String name_result, account_result, id_result;
        try {
            DBActivity task = new DBActivity();
            String sendMsg = "user_index=" + user_index;
            String results = task.execute(sendMsg, "MyInfo.jsp").get();
            String result[] = results.split(",");
            if(result[0].equals("FAIL")){ //실패 시, FAIL 리턴
                Toast.makeText(getApplicationContext(), "정보를 불러오는데 실패했습니다...", Toast.LENGTH_SHORT).show();
            } else { //성공 시, name, account, user_id 리턴
                name_result = result[0];
                account_result = result[1];
                id_result = result[2];
                Toast.makeText(getApplicationContext(), "정보를 불러왔습니다!", Toast.LENGTH_SHORT).show();
                name.setText(name_result);
                account.setText(account_result);
                id.setText(id_result);
            }
        } catch (Exception e) {
            Log.i("DBtest", ".....ERROR.....!");
            Toast.makeText(getApplicationContext(), "DB 연결 에러 발생", Toast.LENGTH_SHORT).show();
        }
    }

    public void moveToMenu(View target){
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        intent.putExtra("user_index", user_index);
        startActivity(intent);
    }
}
