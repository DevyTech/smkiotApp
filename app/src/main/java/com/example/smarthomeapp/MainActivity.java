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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fragmentManager;
    private Fragment sensorFragment,ledFragment,servoFragment,cameraFragment;

    private RequestQueue requestQueue;
    private boolean isConnect;
    private AlertDialog dialog;
    public String url;


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

        requestQueue = MySingleton.getInstance(this).getRequestQueue();

        requestQueue.start();


        bottomNavigationView = findViewById(R.id.bottom_navigation);
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



    //=============================================================================================
    // Sensor API Listener
    //=============================================================================================
    public interface SuhuListener{
        void onSuhuReceived(String suhu, String kelembapan);
    }
    public interface JarakListener{
        void onJarakReceived(String jarak, String servo);
    }
    public interface AirListener{
        void onAirReceived(String air);
    }
    public interface GasListener{
        void onGasReceived(String gas);
    }
    public interface AsapListener{
        void onAsapReceived(String asap);
    }

    private SuhuListener suhuListener;
    private JarakListener jarakListener;
    private AirListener airListener;
    private GasListener gasListener;
    private AsapListener asapListener;

    public void setSuhuListener(SuhuListener listener){
        this.suhuListener = listener;
    }
    public void setJarakListener(JarakListener listener){
        this.jarakListener = listener;
    }
    public void setAirListener(AirListener listener){
        this.airListener = listener;
    }
    public void setGasListener(GasListener listener){
        this.gasListener = listener;
    }
    public void setAsapListener(AsapListener listener){
        this.asapListener = listener;
    }

    public void panggilSuhuAPI(){
        API.getSuhu(this, url, new API.SuhuCallback() {
            @Override
            public void onSuccess(String suhu, String kelembapan) {
                if (suhuListener != null){
                    suhuListener.onSuhuReceived(suhu, kelembapan);
                }
            }

            @Override
            public void onError(String error) {
                Log.e("API Suhu Error: ", error);
            }
        });
    }
    public void panggilJarakAPI(){
        API.getJarak(this, url, new API.JarakCallback() {
            @Override
            public void onSuccess(String jarak, String servo) {
                if (jarakListener != null){
                    jarakListener.onJarakReceived(jarak, servo);
                }
            }

            @Override
            public void onError(String error) {
                Log.e("API Jarak Error: ", error);
            }
        });
    }
    public void panggilAirAPI(){
        API.getAir(this, url, new API.AirCallback() {
            @Override
            public void onSuccess(String air) {
                if (airListener != null){
                    airListener.onAirReceived(air);
                }
            }

            @Override
            public void onError(String error) {
                Log.e("API Air Error: ", error);
            }
        });
    }
    public void panggilGasAPI(){
        API.getGas(this, url, new API.GasCallback() {
            @Override
            public void onSuccess(String gas) {
                if (gasListener != null){
                    gasListener.onGasReceived(gas);
                }
            }

            @Override
            public void onError(String error) {
                Log.e("API Gas Error: ", error);
            }
        });
    }
    public void panggilAsapAPI(){
        API.getAsap(this, url, new API.AsapCallback() {
            @Override
            public void onSuccess(String asap) {
                if (asapListener != null){
                    asapListener.onAsapReceived(asap);
                }
            }

            @Override
            public void onError(String error) {
                Log.e("API Asap Error: ", error);
            }
        });
    }

    //=============================================================================================
    // LED API Listener
    //=============================================================================================
    public void toggleLed(String ledId, API.LEDCallback callback){
        API.toggleLed(this, url, ledId, callback);
    }
    //=============================================================================================
    // Servo API Listener
    //=============================================================================================
    public interface ServoPintuListener{
        void onServoPintuReceived(String servo);
    }
    public interface ServoJendelaListener{
        void onServoJendelaReceived(String servo);
    }
    private ServoJendelaListener servoJendelaListener;
    private ServoPintuListener servoPintuListener;
    public void setServoJendelaListener(ServoJendelaListener listener){
        this.servoJendelaListener = listener;
    }
    public void setServoPintuListener(ServoPintuListener listener){
        this.servoPintuListener = listener;
    }
    public void panggilServoJendelaAPI(){
        API.toggleServoJendela(this, url, new API.ServoJendelaCallback() {
            @Override
            public void onSuccess(String servo) {
                if (servoJendelaListener != null){
                    servoJendelaListener.onServoJendelaReceived(servo);
                }
            }

            @Override
            public void onError(String error) {
                Log.e("API Servo Jendela Error: ", error);
            }
        });
    }
    public void panggilServoPintuAPI(){
        API.toggleServoPintu(this, url, new API.ServoPintuCallback() {
            @Override
            public void onSuccess(String servo) {
                if (servoPintuListener != null){
                    servoPintuListener.onServoPintuReceived(servo);
                }
            }

            @Override
            public void onError(String error) {
                Log.e("API Servo Pintu Error: ", error);
            }
        });
    }
}