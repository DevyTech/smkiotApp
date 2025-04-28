package com.example.smarthomeapp;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.card.MaterialCardView;

import org.json.JSONException;
import org.json.JSONObject;

public class LedFragment extends Fragment {
    private MaterialCardView teras,tengah,kamar1,kamar2,dapur,garasi;

    private ImageView ivTeras,ivTengah,ivKamar1,ivKamar2,ivDapur,ivGarasi;

    private boolean isLoading = false;
    private boolean isTerasOn = false;
    private boolean isTengahOn = false;
    private boolean isKamar1On = false;
    private boolean isKamar2On = false;
    private boolean isDapurOn = false;
    private boolean isGarasiOn = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_led, container, false);

        teras = view.findViewById(R.id.mcv_ledTeras);
        tengah = view.findViewById(R.id.mcv_ledTengah);
        kamar1 = view.findViewById(R.id.mcv_ledKamar1);
        kamar2 = view.findViewById(R.id.mcv_ledKamar2);
        dapur = view.findViewById(R.id.mcv_ledDapur);
        garasi = view.findViewById(R.id.mcv_ledGarasi);

        ivTeras = view.findViewById(R.id.imgvTeras);
        ivTengah = view.findViewById(R.id.imgvTengah);
        ivKamar1 = view.findViewById(R.id.imgvKamar1);
        ivKamar2 = view.findViewById(R.id.imgvKamar2);
        ivDapur = view.findViewById(R.id.imgvDapur);
        ivGarasi = view.findViewById(R.id.imgvGarasi);

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
                Log.e("Error to Connect LED"," : "+error);
            }
        });

        MySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);

        teras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isLoading){
                    toggleLed("teras");
                }
                if (isTerasOn){
                    teras.setCardBackgroundColor(Color.TRANSPARENT);
                    ivTeras.setImageResource(R.drawable.baseline_lightbulb_outline_148);
                } else {
                    teras.setCardBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.blue));
                    ivTeras.setImageResource(R.drawable.baseline_lightbulb_148);
                }
                isTerasOn = !isTerasOn;
            }
        });

        tengah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isLoading){
                    toggleLed("tengah");
                }
                if (isTengahOn){
                    tengah.setCardBackgroundColor(Color.TRANSPARENT);
                    ivTengah.setImageResource(R.drawable.baseline_lightbulb_outline_148);
                } else {
                    tengah.setCardBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.blue));
                    ivTengah.setImageResource(R.drawable.baseline_lightbulb_148);
                }
                isTengahOn = !isTengahOn;
            }
        });

        kamar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isLoading){
                    toggleLed("kamar1");
                }
                if (isKamar1On){
                    kamar1.setCardBackgroundColor(Color.TRANSPARENT);
                    ivKamar1.setImageResource(R.drawable.baseline_lightbulb_outline_148);
                } else {
                    kamar1.setCardBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.blue));
                    ivKamar1.setImageResource(R.drawable.baseline_lightbulb_148);
                }
                isKamar1On = !isKamar1On;
            }
        });

        kamar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isLoading){
                    toggleLed("kamar2");
                }
                if (isKamar2On){
                    kamar2.setCardBackgroundColor(Color.TRANSPARENT);
                    ivKamar2.setImageResource(R.drawable.baseline_lightbulb_outline_148);
                } else {
                    kamar2.setCardBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.blue));
                    ivKamar2.setImageResource(R.drawable.baseline_lightbulb_148);
                }
                isKamar2On = !isKamar2On;
            }
        });

        dapur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isLoading){
                    toggleLed("dapur");
                }
                if (isDapurOn){
                    dapur.setCardBackgroundColor(Color.TRANSPARENT);
                    ivDapur.setImageResource(R.drawable.baseline_lightbulb_outline_148);
                } else {
                    dapur.setCardBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.blue));
                    ivDapur.setImageResource(R.drawable.baseline_lightbulb_148);
                }
                isDapurOn = !isDapurOn;
            }
        });

        garasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isLoading){
                    toggleLed("garasi");
                }
                if (isGarasiOn){
                    garasi.setCardBackgroundColor(Color.TRANSPARENT);
                    ivGarasi.setImageResource(R.drawable.baseline_lightbulb_outline_148);
                } else {
                    garasi.setCardBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.blue));
                    ivGarasi.setImageResource(R.drawable.baseline_lightbulb_148);
                }
                isGarasiOn = !isGarasiOn;
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getLedStatus();
    }

    private void toggleLed(String ledId){
        StringRequest request = new StringRequest(Request.Method.GET, MainActivity.mainActivity.url + "/toggle-led?led=" + ledId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int maxLength = Math.min(500, response.length());
                        Log.d("Response "+ledId,"Response is : " + response.substring(0, maxLength));
                    }
                },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error to Connect LED : "+ledId, error.toString());
            }
        });

        MySingleton.getInstance(getActivity()).addToRequestQueue(request);
    }

    private void getLedStatus(){
        isLoading = true;
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, MainActivity.mainActivity.url + "/get-led-status",
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String statusTeras = response.getString("stateTeras");
                    String statusTengah = response.getString("stateTengah");
                    String statusKamar1 = response.getString("stateKamar1");
                    String statusKamar2 = response.getString("stateKamar2");
                    String statusDapur = response.getString("stateDapur");
                    String statusGarasi = response.getString("stateGarasi");

                    if (statusTeras.equals("ON")){
                        teras.setCardBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.blue));
                        ivTengah.setImageResource(R.drawable.baseline_lightbulb_148);
                    }else {
                        tengah.setCardBackgroundColor(Color.TRANSPARENT);
                        ivTengah.setImageResource(R.drawable.baseline_lightbulb_outline_148);
                    }

                } catch (JSONException e){
                    Log.e("Error JsonObject Response LED: ", e.toString());
                } finally {
                    isLoading = false;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error get Status : ", error.toString());
            }
        });

        MySingleton.getInstance(getActivity()).addToRequestQueue(objectRequest);
    }
}