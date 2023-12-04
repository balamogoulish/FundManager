package com.example.fundmanager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SignUpActivity extends AppCompatActivity {
    EditText edit_id, edit_pw, edit_name, edit_account, edit_pwCheck;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        edit_id = findViewById(R.id.register_id);
        edit_pw = findViewById(R.id.register_pw);
        edit_name = findViewById(R.id.register_name);
        edit_account = findViewById(R.id.register_account);
        edit_pwCheck = findViewById(R.id.checkPw);
    }

    public void insert(View target) {
        // To do: check id, pw, name, account valid
        String id = edit_id.getText().toString();
        String pw = edit_pw.getText().toString();
        String pwCheck = edit_pwCheck.getText().toString();
        String name = edit_name.getText().toString();
        String account = edit_account.getText().toString();
        String valid = signUpValidCheck(id, pw, pwCheck, name, account);
        if(valid.equals("SUCCESS")){
            try {
                String result;
                SignUpDBActivity task = new SignUpDBActivity();
                result = task.execute(id, pw, name, account).get();
                if(result.equals("SUCCESS")){
                    edit_id.setText("");
                    edit_pw.setText("");
                    edit_name.setText("");
                    edit_account.setText("");
                    Toast.makeText(getApplicationContext(), "성공적으로 가입되었습니다!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                Log.i("DBtest", ".....ERROR.....!");
                Toast.makeText(getApplicationContext(), "DB 연결 에러 발생", Toast.LENGTH_SHORT).show();
            }
        } else{
            Toast.makeText(getApplicationContext(), valid, Toast.LENGTH_SHORT).show();
        }
    }

    public String signUpValidCheck(String id, String pw, String check, String name, String account){
        String response = "SUCCESS";
        String pwPattern = "([0-9].*[!,@,#,^,&,*,(,)])|([!,@,#,^,&,*,(,)].*[0-9])";
        Pattern pattern_pw = Pattern.compile(pwPattern);
        Matcher matcher = pattern_pw.matcher(pw);

        if(id.length() < 6 ||id.length() > 12){
            response = "6-12자리의 아이디를 입력해주세요.";
        } else if(!matcher.find()){
            response = "숫자, 특수문자가 포함된 0-9자를 비밀번호로 입력해주세요.";
        } else if(name.length() < 2){
            response = "이름을 두 자 이상입력해주세요.";
        } else if(account.length() == 0){
            response = "계좌번호를 입력해주세요.";
        } else if(pw.equals(check) != true){
            response = "비밀번호가 일치하지 않습니다.";
        } else if(account.length() == 0){
            response = "계좌번호를 입력해주세요.";
        }
        return response;
    }
}