package com.example.smarthomeapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.materialswitch.MaterialSwitch;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private TextView tv_status,tv_jendela;
    private MaterialSwitch jendela;
    private String url;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tv_status = findViewById(R.id.status);
        tv_jendela = findViewById(R.id.jendelaStatus);
        jendela = findViewById(R.id.servoJendela);

        url = "http://192.168.1.54:8080";

        requestQueue = MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();

        requestQueue.start();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int maxLength = Math.min(500, response.length());
                        Log.d("Response","Response is : " + response.substring(0, maxLength));
                        tv_status.setText("Online");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error to Connect"," : "+error);
            }
        });

        // Add a request to RequestQueue
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);



        jendela.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                toggleJendela();
                tv_jendela.setText(b ? "Terbuka" : "Tertutup");
            }
        });
    }

    private void toggleJendela(){
        StringRequest request = new StringRequest(Request.Method.GET, url+"/servoJendela",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int maxLength = Math.min(500, response.length());
                        Log.d("Response Jendela","Response is : " + response.substring(0, maxLength));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error to Connect"," : "+error);
            }
        });

        // Add the request to the RequestQueue.
        MySingleton.getInstance(this).addToRequestQueue(request);
    }
}