package com.example.fundmanager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText edit_id, edit_pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edit_id = findViewById(R.id.idEdit);
        edit_pw = findViewById(R.id.pwEdit);
    }

    public void search(View target) {
        String id = edit_id.getText().toString();
        String pw = edit_pw.getText().toString();
        String user_index;
        if(id.length() > 0 && pw.length() > 0){
            try {
                String result;
                LoginDBActivity task = new LoginDBActivity();
                result = task.execute(id, pw).get();
                if(result.equals("FAIL")){
                    edit_id.setText("");
                    edit_pw.setText("");
                    Toast.makeText(getApplicationContext(), "로그인에 실패했습니다...", Toast.LENGTH_SHORT).show();

                } else {
                    user_index = result;
                    Toast.makeText(getApplicationContext(), "로그인에 성공했습니다!", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), user_index, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                    intent.putExtra("user_index", user_index);
                    startActivity(intent);
                }

            } catch (Exception e) {
                Log.i("DBtest", ".....ERROR.....!");
                Toast.makeText(getApplicationContext(), "DB 연결 에러 발생", Toast.LENGTH_SHORT).show();
            }
        } else{
            Toast.makeText(getApplicationContext(), "아이디와 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
        }
    }

    public void goToSignUp(View target){
        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivity(intent);
    }
}
