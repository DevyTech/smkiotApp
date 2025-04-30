package com.example.smarthomeapp;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fragmentManager;
    private Fragment sensorFragment,ledFragment,servoFragment,cameraFragment;

    private RequestQueue requestQueue;
    private boolean isConnect;
    private AlertDialog dialog;
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


        bottomNavigationView = findViewById(R.id.bottom_navigation);

//        bottomNavigationView.setOnItemSelectedListener(item -> {
//            int itemId = item.getItemId();
//            if (itemId == R.id.sensorMenu){
////                getSupportFragmentManager().beginTransaction().replace(R.id.frame, new SensorFragment()).commit();
//                return true;
//            } else if (itemId == R.id.ledMenu) {
////                getSupportFragmentManager().beginTransaction().replace(R.id.frame, new LedFragment()).commit();
//                return true;
//            } else if (itemId == R.id.servoMenu) {
////                getSupportFragmentManager().beginTransaction().replace(R.id.frame, new ServoFragment()).commit();
//                return true;
//            } else if (itemId == R.id.cameraMenu) {
////                getSupportFragmentManager().beginTransaction().replace(R.id.frame, new CameraFragment()).commit();
//                return true;
//            }
//            return false;
//        });
        fragmentManager = getSupportFragmentManager();
        sensorFragment = fragmentManager.findFragmentByTag("sensor");
        ledFragment = fragmentManager.findFragmentByTag("led");
        servoFragment = fragmentManager.findFragmentByTag("servo");
        cameraFragment = fragmentManager.findFragmentByTag("camera");

        if (sensorFragment == null){
            sensorFragment = new SensorFragment();
            fragmentManager.beginTransaction().add(R.id.frame, sensorFragment,"sensor").hide(sensorFragment).commit();
        }
        if (ledFragment == null){
            ledFragment = new LedFragment();
            fragmentManager.beginTransaction().add(R.id.frame, ledFragment,"led").hide(ledFragment).commit();
        }
        if (servoFragment == null){
            servoFragment = new ServoFragment();
            fragmentManager.beginTransaction().add(R.id.frame, servoFragment,"servo").hide(servoFragment).commit();
        }
        if (cameraFragment == null){
            cameraFragment = new CameraFragment();
            fragmentManager.beginTransaction().add(R.id.frame, cameraFragment,"camera").hide(cameraFragment).commit();
        }

        bottomNavigationView.setOnItemSelectedListener(item -> {
            fragmentManager.beginTransaction().hide(sensorFragment).hide(ledFragment).hide(servoFragment).hide(cameraFragment).commit();
            int itemId = item.getItemId();
            if (itemId == R.id.sensorMenu){
                fragmentManager.beginTransaction().show(sensorFragment).commit();
                return true;
            } else if (itemId == R.id.ledMenu) {
                fragmentManager.beginTransaction().show(ledFragment).commit();
                return true;
            } else if (itemId == R.id.servoMenu) {
                fragmentManager.beginTransaction().show(servoFragment).commit();
                return true;
            } else if (itemId == R.id.cameraMenu) {
                fragmentManager.beginTransaction().show(cameraFragment).commit();
                return true;
            }
            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkConnection();
    }

    private void checkConnection(){
        StringRequest checkConnection = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        fragmentManager.beginTransaction().show(sensorFragment).commit();
                        bottomNavigationView.setSelectedItemId(R.id.sensorMenu);
                        isConnect = true;
                        Toast.makeText(MainActivity.this, "Connection Successfully", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error to Connect ESP32"," : "+error);
                Toast.makeText(MainActivity.this, "Can't Connect to ESP32", Toast.LENGTH_SHORT).show();
                isConnect = false;
                if (dialog != null && dialog.isShowing()){
                    dialog.dismiss();
                }
                showInputDialog();
            }
        });

        MySingleton.getInstance(this).addToRequestQueue(checkConnection);
    }

    private void showInputDialog(){
        if (!isConnect){
            View view = LayoutInflater.from(this).inflate(R.layout.dialog_layout,null);
            TextInputEditText ipAddress = view.findViewById(R.id.ip_address);
            TextInputLayout iplayout = view.findViewById(R.id.iplayout);
            ProgressBar loading = view.findViewById(R.id.loading);
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(MainActivity.this);
            builder.setTitle("Connect to ESP32");
            builder.setView(view);
            builder.setCancelable(false);
            builder.setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    loading.setVisibility(View.VISIBLE);
                    String ip = ipAddress.getText().toString();
                    url = "http://"+ip+":8080";
                }
            });
            builder.setNegativeButton("Cancel", (dialogInterface, i) -> {
                dialogInterface.dismiss();
            });

            dialog = builder.create();
            dialog.setOnShowListener(d -> {
                Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);

                positiveButton.setOnClickListener(v -> {
                    String ip = ipAddress.getText().toString();
                    if (!ip.isEmpty()){
                        positiveButton.setVisibility(View.GONE);
                        negativeButton.setVisibility(View.GONE);
                        iplayout.setVisibility(View.GONE);
                        loading.setVisibility(View.VISIBLE);

                        url = "http://"+ip+":8080";
                        new Handler().postDelayed(()->{
                            checkConnection();
                        },5000);
                    }else {
                        ipAddress.setError("IP Address is Required");
                    }
                });
            });
            dialog.show();
        }else {
            Toast.makeText(MainActivity.this, "Connection Successfully", Toast.LENGTH_SHORT).show();
        }


    }
}