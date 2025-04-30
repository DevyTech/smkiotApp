package com.example.smarthomeapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

public class SensorFragment extends Fragment {

    private Runnable sensorRunnable;
    private Handler handler = new Handler();

    TextView suhu,kelembapan,air,jarak,gas,asap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sensor, container, false);

        suhu = view.findViewById(R.id.tvSuhu);
        kelembapan = view.findViewById(R.id.tvKelembapan);
        air = view.findViewById(R.id.tvAir);
        jarak = view.findViewById(R.id.tvJarak);
        gas = view.findViewById(R.id.tvGas);
        asap = view.findViewById(R.id.tvAsap);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        startUpdater();
        Log.d("Fragment","onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        stopUpdater();
        Log.d("Fragment","onPause");
    }

    private void startUpdater(){
        sensorRunnable = new Runnable() {
            @Override
            public void run() {
                getSuhu();
                getJarak();
                getGas();
                getAsap();
                handler.postDelayed(this, 500);
            }
        };
        handler.post(sensorRunnable);
    }

    private void stopUpdater(){
        handler.removeCallbacks(sensorRunnable);
    }

    private void getSuhu(){
        JsonObjectRequest suhuRequest = new JsonObjectRequest(Request.Method.GET, MainActivity.mainActivity.url + "/kelembapan",
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String statusSuhu = response.getString("temperature");
                    String statusKelembapan = response.getString("humidity");
                    suhu.setText(String.format("%s\u2103", statusSuhu));
                    kelembapan.setText(String.format("%s%%", statusKelembapan));
                } catch (Exception e) {
                    Log.e("Error get Kelembapan : ", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Response Error Kelembapan : ", error.toString());
            }
        });
        MySingleton.getInstance(getActivity()).addToRequestQueue(suhuRequest);
    }

    private void getJarak(){
        JsonObjectRequest jarakRequest = new JsonObjectRequest(Request.Method.GET, MainActivity.mainActivity.url + "/jarak",
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String status = response.getString("distance");
                    jarak.setText(String.format("%s | CM", status));
                } catch (Exception e) {
                    Log.e("Error get Jarak : ", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Response Error Jarak : ", error.toString());
            }
        });
        MySingleton.getInstance(getActivity()).addToRequestQueue(jarakRequest);
    }

    private void getGas(){
        StringRequest gasRequest = new StringRequest(Request.Method.GET, MainActivity.mainActivity.url + "/gas",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        gas.setText(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Response Error gas : ", error.toString());
            }
        });

        MySingleton.getInstance(getActivity()).addToRequestQueue(gasRequest);
    }

    private void getAsap(){
        StringRequest asapRequest = new StringRequest(Request.Method.GET, MainActivity.mainActivity.url + "/asap",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        asap.setText(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Response Error asap : ", error.toString());
            }
        });

        MySingleton.getInstance(getActivity()).addToRequestQueue(asapRequest);
    }
}