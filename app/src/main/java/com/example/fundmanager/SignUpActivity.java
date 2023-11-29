package com.example.fundmanager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SignUpActivity extends AppCompatActivity {
    EditText edit_id, edit_pw, edit_name, edit_account;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        edit_id = findViewById(R.id.register_id);
        edit_pw = findViewById(R.id.register_pw);
        edit_name = findViewById(R.id.register_name);
        edit_account = findViewById(R.id.register_account);
    }

    public void insert(View target) {
        // To do: check id, pw, name, account valid
        String id = edit_id.getText().toString();
        String pw = edit_pw.getText().toString();
        String name = edit_name.getText().toString();
        String account = edit_account.getText().toString();

        // Use AsyncTask or a background thread for network operations
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                String url = "http://your_server/register.jsp";

                try {
                    URL serverUrl = new URL(url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) serverUrl.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);

                    // Write data to the server
                    OutputStream outputStream = new BufferedOutputStream(httpURLConnection.getOutputStream());
                    String postData = "id=" + params[0] + "&pw=" + params[1] + "&name=" + params[2] + "&account=" + params[3];
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
                handleInsertResult(result);
            }
        }.execute(id, pw, name, account);
    }

    private void handleInsertResult(String result) {
        if (result != null && result.equals("success")) {
            Toast.makeText(getApplicationContext(), "Registered successfully", Toast.LENGTH_SHORT).show();
            edit_id.setText("");
            edit_pw.setText("");
            edit_name.setText("");
            edit_account.setText("");
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Failed to register. Please try again", Toast.LENGTH_SHORT).show();
            // You can log the exception for further analysis if needed
            Log.e("Insert Error", "Error registering data on the server");
        }
    }
}