package com.example.smarthomeapp;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    private RequestQueue requestQueue;

    public String url;

    public static MainActivity mainActivity;

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

        mainActivity = this;

        requestQueue = MySingleton.getInstance(this).getRequestQueue();

        requestQueue.start();

//        url = "http://192.168.1.54:8080";
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().add(R.id.frame, new SensorFragment()).commit();
        bottomNavigationView.setSelectedItemId(R.id.sensorMenu);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.sensorMenu){
                getSupportFragmentManager().beginTransaction().replace(R.id.frame, new SensorFragment()).commit();
                return true;
            } else if (itemId == R.id.ledMenu) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame, new LedFragment()).commit();
                return true;
            } else if (itemId == R.id.servoMenu) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame, new ServoFragment()).commit();
                return true;
            } else if (itemId == R.id.cameraMenu) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame, new CameraFragment()).commit();
                return true;
            }
            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        StringRequest checkConnection = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MainActivity.this, "Connection Successfully", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error to Connect ESP32"," : "+error);
                showInputDialog();
            }
        });

        MySingleton.getInstance(this).addToRequestQueue(checkConnection);
    }

    private void showInputDialog(){
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_layout,null);
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(MainActivity.this);
        builder.setTitle("Connect to ESP32");
        builder.setView(view);
        builder.setCancelable(false);
        builder.setPositiveButton("Connect", (dialogInterface, i) -> {
            String ipAddress = view.findViewById(R.id.ip_address).toString();
            url = "http://"+ipAddress+":8080";
        });
        builder.setNegativeButton("Cancel", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });
        builder.create().show();
    }
}