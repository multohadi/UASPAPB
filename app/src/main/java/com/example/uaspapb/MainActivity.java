package com.example.uaspapb;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private String url = "https://mgm.ub.ac.id/todo.php";
    private RecyclerView rctodo;
    adapter adapterdata;
    TextView jadwal;
    List<model> listData = new ArrayList<>();
    model dmodel;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            jadwal.setText((String) msg.obj);
            return true;
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        jadwal = findViewById(R.id.jadwal);
        rctodo = findViewById(R.id.rctodo);
        getData();
    }
    private void getData() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listData = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        dmodel = new model();
                        JSONObject data = jsonArray.getJSONObject(i);
                        dmodel.setTime(data.getString("time"));
                        dmodel.setWhat(data.getString("what"));
                        listData.add(dmodel);
                    }
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Calendar calendar = Calendar.getInstance();
                            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
                            String currentTime = sdf.format(calendar.getTime());
                            String text = "";
                            for( int o =0; o < listData.size(); o++){
                                if (currentTime.compareTo(listData.get(o).getTime()) >= 0 && currentTime.compareTo(listData.get(o+1).getTime()) <= 0) {
                                    text = listData.get(o).getWhat();
                                }
                            }
                            Message msg = handler.obtainMessage();
                            msg.obj = text;
                            handler.sendMessage(msg);
                        }
                    }).start();
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
                    rctodo.setLayoutManager(linearLayoutManager);
                    adapterdata = new adapter(MainActivity.this, listData);
                    rctodo.setAdapter(adapterdata);
                    adapterdata.notifyDataSetChanged();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add((stringRequest));
    }
}