package com.kaikeyun.android_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.kaikeyun.sdk.KKSDK;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    static private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button room_btn = (Button) findViewById(R.id.room_button);
        room_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://192.168.0.100:10008/joinroom";
                OkHttpClient okHttpClient = new OkHttpClient();
                final Request request = new Request.Builder().url(url).get().build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.w(TAG, "onFailure: ");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        //Log.d(TAG, "onResponse: " + response.body().string());
                        try {
                            JSONObject json = new JSONObject(response.body().string());
                            JSONObject data = json.getJSONObject("data");
                            KKSDK.getInstance().openRoom(
                                    data.getInt("roomid"),
                                    data.getInt("userid"),
                                    data.getString("token"),
                                    MainActivity.this);
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        Button play_btn = (Button) findViewById(R.id.play_button);
        play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://192.168.0.100:10008/getrecord";
                OkHttpClient okHttpClient = new OkHttpClient();
                final Request request = new Request.Builder().url(url).get().build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.w(TAG, "onFailure: ");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        //Log.d(TAG, "onResponse: " + response.body().string());
                        try {
                            JSONObject json = new JSONObject(response.body().string());
                            JSONObject data = json.getJSONObject("data");
                            KKSDK.getInstance().openPlay(
                                    data.getString("recid"),
                                    data.getInt("userid"),
                                    data.getString("token"),
                                    MainActivity.this);
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}