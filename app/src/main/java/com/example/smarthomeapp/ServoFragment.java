package com.example.smarthomeapp;

import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.materialswitch.MaterialSwitch;

import org.json.JSONException;
import org.json.JSONObject;

public class ServoFragment extends Fragment {
    private TextView tv_garasi,tv_jendela,tv_pintu;
    private MaterialSwitch jendelaSwitch, pintuSwitch;
    private ImageView img_garasi,img_jendela,img_pintu;

    private boolean isLoading = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_servo, container, false);

        tv_garasi = view.findViewById(R.id.garasiStatus);
        tv_jendela = view.findViewById(R.id.jendelaStatus);
        tv_pintu = view.findViewById(R.id.pintuStatus);

        jendelaSwitch = view.findViewById(R.id.servoJendela);
        pintuSwitch = view.findViewById(R.id.servoPintu);

        img_garasi = view.findViewById(R.id.imgGarasi);
        img_jendela = view.findViewById(R.id.imgJendela);
        img_pintu = view.findViewById(R.id.imgPintu);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, MainActivity.mainActivity.url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int maxLength = Math.min(500, response.length());
                        Log.d("Response","Response is : " + response.substring(0, maxLength));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error to Connect Servo"," : "+error);
            }
        });

        // Add a request to RequestQueue
        MySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);



        jendelaSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!isLoading){
                    toggleJendela();
                }
                tv_jendela.setText(b ? "Jendela Terbuka" : "Jendela Tertutup");
                img_jendela.setColorFilter(b ? ContextCompat.getColor(requireActivity(), R.color.blue) : ContextCompat.getColor(requireActivity(), R.color.gray));
            }
        });

        pintuSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!isLoading){
                    togglePintu();
                }
                tv_pintu.setText(b ? "Pintu Terbuka" : "Pintu Tertutup");
                img_pintu.setColorFilter(b ? ContextCompat.getColor(requireActivity(), R.color.blue) : ContextCompat.getColor(requireActivity(), R.color.gray));
            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getJendelaStatus();
        getPintuStatus();
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
                        jendelaSwitch.setChecked(true);
                    }else {
                        jendelaSwitch.setChecked(false);
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

    private void togglePintu(){
        StringRequest request = new StringRequest(Request.Method.GET, MainActivity.mainActivity.url+"/servoPintu",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int maxLength = Math.min(500, response.length());
                        Log.d("Response Pintu","Response is : " + response.substring(0, maxLength));
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

    public void getPintuStatus(){
        isLoading = true;
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, MainActivity.mainActivity.url + "/servoPintuStatus",
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String status = response.getString("servoPintuState");
                    tv_jendela.setText(status);
                    if (status.equals("Pintu Terbuka")){
                        jendelaSwitch.setChecked(true);
                    }else {
                        jendelaSwitch.setChecked(false);
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
                Log.e("Error get Pintu Status : ",error.toString());
            }
        });
        MySingleton.getInstance(getActivity()).addToRequestQueue(objectRequest);
    }
}