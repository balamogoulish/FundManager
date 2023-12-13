package com.example.fundmanager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class InOutActivity extends AppCompatActivity {
    String user_index;
    int user_money;
    EditText inout_amount_txt;
    TextView user_money_txt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inout);

        Intent intent = getIntent();
        user_index = intent.getStringExtra("user_index");
        user_money_txt = findViewById(R.id.totalAmtRestultTxt);
        inout_amount_txt = findViewById(R.id.AmtEdit);

        getUserMoney();
    }

    public void getUserMoney(){ //user_index를 통해 user_money를 조회함
        try {
            String result;
            GetUserMoneyDBActivity task = new GetUserMoneyDBActivity();
            result = task.execute(user_index).get();
            if(result.equals("FAIL")){
                Toast.makeText(getApplicationContext(), "투자액을 불러오는데 실패했습니다...", Toast.LENGTH_SHORT).show();

            } else { //user_money return
                Toast.makeText(getApplicationContext(), "투자액을 불러왔습니다!", Toast.LENGTH_SHORT).show();
                try {
                    user_money = Integer.parseInt(result);
                    user_money_txt.setText(result);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            Log.i("DBtest", ".....ERROR.....!");
            Toast.makeText(getApplicationContext(), "DB 연결 에러 발생", Toast.LENGTH_SHORT).show();
        }
    }

    public void inMoney(View view){
        int inAmt = Integer.parseInt(inout_amount_txt.getText().toString());
        inOutHandle(inAmt);
    }
    public void outMoney(View view){
        int outAmt = Integer.parseInt(inout_amount_txt.getText().toString());
        inOutHandle(-outAmt);
    }
    public void inOutHandle(int change) { //업데이트된 user_money를 가져외 출력함
        int user_money_update;
        try {
            String result;
            InOutDBActivity task = new InOutDBActivity();
            result = task.execute(user_index, String.valueOf(change)).get();
            if(result.equals("SUCCESS")){
                user_money_update = user_money + change;
                if(user_money_update < 0){
                    Toast.makeText(getApplicationContext(), "소지 금액보다 출금액이 더 큽니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "입/출금에 성공했습니다!", Toast.LENGTH_SHORT).show();
                    user_money_txt.setText(String.valueOf(user_money_update));
                    user_money = user_money_update;
                }
            } else {

                Toast.makeText(getApplicationContext(), "입/출금에 실패했습니다...", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Log.i("DBtest", ".....ERROR.....!");
        }
    }

}