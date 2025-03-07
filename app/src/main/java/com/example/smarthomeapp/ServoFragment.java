package com.example.smarthomeapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.materialswitch.MaterialSwitch;

import org.json.JSONException;
import org.json.JSONObject;

public class ServoFragment extends Fragment {

    private TextView tv_status,tv_jendela;
    private MaterialSwitch jendela;

    private boolean isLoading = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_servo, container, false);

        tv_status = view.findViewById(R.id.status);
        tv_jendela = view.findViewById(R.id.jendelaStatus);
        jendela = view.findViewById(R.id.servoJendela);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, MainActivity.mainActivity.url,
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
        MySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);



        jendela.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!isLoading){
                    toggleJendela();
                }
                tv_jendela.setText(b ? "Jendela Terbuka" : "Jendela Tertutup");
            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getJendelaStatus();
    }

    private void toggleJendela(){
        StringRequest request = new StringRequest(Request.Method.GET, MainActivity.mainActivity.url+"/servoJendela",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int maxLength = Math.min(500, response.length());
                        Log.d("Response Jendela","Response is : " + response.substring(0, maxLength));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error to Connect : ", error.toString());
            }
        });

        // Add the request to the RequestQueue.
        MySingleton.getInstance(getActivity()).addToRequestQueue(request);
    }

    public void getJendelaStatus(){
        isLoading = true;
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, MainActivity.mainActivity.url + "/servoJendelaStatus",
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String status = response.getString("servoJendelaState");
                    tv_jendela.setText(status);
                    if (status.equals("Jendela Terbuka")){
                        jendela.setChecked(true);
                    }else {
                        jendela.setChecked(false);
                    }
                } catch (JSONException e) {
                    Log.e("Error JsonObject Response : ", e.toString());
                } finally {
                    isLoading = false;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error get Jendela Status : ",error.toString());
            }
        });
        MySingleton.getInstance(getActivity()).addToRequestQueue(objectRequest);
    }
}