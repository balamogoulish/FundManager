package com.example.fundmanager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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

        // Use AsyncTask, Thread, or a library like Retrofit for network operations (avoid running on the main thread)
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                // Perform HTTP POST request to the JSP script on the server
                String url = "http://your_server/login.jsp";
                try {
                    URL serverUrl = new URL(url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) serverUrl.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);

                    // Write data to the server
                    OutputStream outputStream = new BufferedOutputStream(httpURLConnection.getOutputStream());
                    String postData = "id=" + params[0] + "&pw=" + params[1];
                    outputStream.write(postData.getBytes());
                    outputStream.flush();
                    outputStream.close();

                    // Read response from the server
                    BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    reader.close();
                    httpURLConnection.disconnect();

                    return response.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String result) {
                handleLoginResult(result);
            }
        }.execute(id, pw);
    }

    private void handleLoginResult(String result) {
        if (result != null) {
            try {
                JSONObject jsonResult = new JSONObject(result);

                if ("success".equals(jsonResult.getString("status"))) {
                    Toast.makeText(getApplicationContext(), "로그인에 성공했습니다! ", Toast.LENGTH_SHORT).show();
                    String userIndex = jsonResult.getString("index");
                    Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                    intent.putExtra("userIndex", userIndex);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "로그인에 실패했습니다,\n 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    public void goToSignUp(View target){
        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivity(intent);
    }
}
