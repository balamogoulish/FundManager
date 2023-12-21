package com.example.fundmanager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CheckGainActivity extends AppCompatActivity {
    TextView title_txt, gain_txt, origin_txt;
    String user_index, sendMsg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkgain);
        Intent intent = getIntent();
        user_index = intent.getStringExtra("user_index");

        title_txt = findViewById(R.id.checkTypeTxt);
        gain_txt = findViewById(R.id.gainResult);
        origin_txt = findViewById(R.id.totalResult);

        sendMsg = "user_index=" + user_index;
    }
    public void checkUser(View target) {
        try {
            DBActivity task = new DBActivity();

            String results = task.execute(sendMsg, "CheckUser.jsp").get();
            String result[] = results.split(",");
            if(result[0].equals("FAIL")){ //실패 시, FAIL 리턴
                Toast.makeText(getApplicationContext(), "조회에 실패했습니다...", Toast.LENGTH_SHORT).show();
            } else { //성공 시, user_origin, user_gain 리턴
                String user_origin = result[0];
                String user_gain = result[1];
                Toast.makeText(getApplicationContext(), "조회에 성공했습니다!", Toast.LENGTH_SHORT).show();
                title_txt.setText("User");
                gain_txt.setText(user_gain);
                origin_txt.setText(user_origin);
            }

        } catch (Exception e) {
            Log.i("DBtest", ".....ERROR.....!");
            Toast.makeText(getApplicationContext(), "DB 연결 에러 발생", Toast.LENGTH_SHORT).show();
        }
    }

    public void checkFund(View target) {
        try {
            DBActivity task = new DBActivity();
            String results = task.execute(sendMsg, "CheckFund.jsp").get();
            String result[] = results.split(",");
            if(result[0].equals("FAIL")){ //실패 시, FAIL 리턴
                Toast.makeText(getApplicationContext(), "조회에 실패했습니다...", Toast.LENGTH_SHORT).show();
            } else { //성공 시, user_origin, user_gain 리턴
                String fund_origin = result[0];
                String fund_gain = result[1];
                Toast.makeText(getApplicationContext(), "조회에 성공했습니다!", Toast.LENGTH_SHORT).show();
                title_txt.setText("Fund");
                gain_txt.setText(fund_gain);
                origin_txt.setText(fund_origin);
            }
        } catch (Exception e) {
            Log.i("DBtest", ".....ERROR.....!");
            Toast.makeText(getApplicationContext(), "DB 연결 에러 발생", Toast.LENGTH_SHORT).show();
        }
    }
}

