package com.example.uaspapb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String url = "https://mgm.ub.ac.id/todo.php";
    private RecyclerView rctodo;
    adapter adapterdata;

    TextView jadwal;
    List<model> listData = new ArrayList<>();

    model dmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jadwal = findViewById(R.id.jadwal);

        rctodo = findViewById(R.id.rctodo);

        getData();


    }

    private void getData(){
        RequestQueue requestQueue =  Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                listData = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i=0; i<jsonArray.length();i++){
                        dmodel = new model();
                        JSONObject data = jsonArray.getJSONObject(i);
                        dmodel.setTime(data.getString("time"));
                        dmodel.setWhat(data.getString("what"));
                        listData.add(dmodel);

                    }

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