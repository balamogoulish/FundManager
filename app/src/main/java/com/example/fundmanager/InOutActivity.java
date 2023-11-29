package com.example.fundmanager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
    String userIndex;
    EditText inoutAmount;
    TextView userMoney;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inout);

        Intent intent = getIntent();
        userIndex = intent.getStringExtra("_id");
        userMoney = findViewById(R.id.totalAmtRestultTxt);
        inoutAmount = findViewById(R.id.AmtEdit);
    }

    public void inMoney(View view){
        int inAmt = Integer.parseInt(inoutAmount.getText().toString());
        inOutHandle(inAmt);
    }
    public void outMoney(View view){
        int outAmt = Integer.parseInt(inoutAmount.getText().toString());
        inOutHandle(-outAmt);
    }
    public void inOutHandle(int change) {
            // Use AsyncTask or background thread for network operations
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String url = "http://your_server/inout.jsp?user_index="+userIndex+"&change_amt="+change;
                try {
                    URL serverUrl = new URL(url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) serverUrl.openConnection();
                    httpURLConnection.setRequestMethod("GET");
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
                handleInOutResult(result);
            }
        }.execute();
    }

    void handleInOutResult(String result){
        if (result != null) {
            userMoney.setText(result);
        } else {
            Toast.makeText(getApplicationContext(), "Failed to record deposit. Please try again", Toast.LENGTH_SHORT).show();
        }
    }
}