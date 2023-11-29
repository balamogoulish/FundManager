package com.example.fundmanager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class CheckGainActivity extends AppCompatActivity {
    TextView title_txt, gain_txt, origin_txt;
    String userIndex;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkgain);
        Intent intent = getIntent();
        userIndex = intent.getStringExtra("userIndex");

        title_txt = (TextView) findViewById(R.id.checkTypeTxt);
        gain_txt = (TextView) findViewById(R.id.gainResult);
        origin_txt = (TextView) findViewById(R.id.totalResult);
    }
    public void checkUser(View target) {
        // Use AsyncTask or a background thread for network operations
        new AsyncTask<String, Void, String[]>() {
            @Override
            protected String[] doInBackground(String... params) {
                String url = "http://your_server/check_user.jsp";

                try {
                    URL serverUrl = new URL(url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) serverUrl.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);

                    // Write data to the server
                    OutputStream outputStream = new BufferedOutputStream(httpURLConnection.getOutputStream());
                    String postData = "user_index=" + params[0];
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

                    // Split the response into separate values (user_origin and user_gain)
                    return response.toString().split(",");
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String[] result) {
                handleUserCheckResult(result);
            }
        }.execute(userIndex);
    }

    public void checkFund(View target) {
        // No need to send specific parameters for fund check since you want the most recent data
        // You can send an empty string or any placeholder value
        new AsyncTask<Void, Void, String[]>() {
            @Override
            protected String[] doInBackground(Void... params) {
                String url = "http://your_server/check_fund.jsp";

                try {
                    URL serverUrl = new URL(url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) serverUrl.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);

                    // No need to send specific parameters for fund check
                    // You can send an empty string or any placeholder value
                    OutputStream outputStream = new BufferedOutputStream(httpURLConnection.getOutputStream());
                    String postData = "";  // You can modify this if needed
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

                    // Split the response into separate values (fund_origin and fund_gain)
                    return response.toString().split(",");
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String[] result) {
                handleFundCheckResult(result);
            }
        }.execute();
    }

    private void handleUserCheckResult(String[] result) {
        // Handle the result for user check
        if (result != null && result.length == 2) {
            // Process the result as needed
            title_txt.setText("User");
            origin_txt.setText(result[0]);
            gain_txt.setText(result[1]);
        } else {
            Toast.makeText(getApplicationContext(), "Failed to check user. Please try again", Toast.LENGTH_SHORT).show();
            Log.e("Check User Error", "Error checking user data on the server");
        }
    }

    private void handleFundCheckResult(String[] result) {
        // Handle the result for fund check
        if (result != null && result.length == 2) {
            // Process the result as needed
            title_txt.setText("Manager");
            origin_txt.setText(result[0]);
            gain_txt.setText(result[1]);
        } else {
            Toast.makeText(getApplicationContext(), "Failed to check fund. Please try again", Toast.LENGTH_SHORT).show();
            Log.e("Check Fund Error", "Error checking fund data on the server");
        }
    }

}
