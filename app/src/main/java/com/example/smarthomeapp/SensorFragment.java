package com.example.smarthomeapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
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

public class SensorFragment extends Fragment implements MainActivity.SuhuListener, MainActivity.JarakListener, MainActivity.AirListener, MainActivity.GasListener, MainActivity.AsapListener {
    private Handler handler;
    private Runnable pollingTask;

    private TextView suhu,kelembapan,air,jarak,gas,asap;

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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity){
            ((MainActivity) context).setSuhuListener(this);
            ((MainActivity) context).setJarakListener(this);
            ((MainActivity) context).setAirListener(this);
            ((MainActivity) context).setGasListener(this);
            ((MainActivity) context).setAsapListener(this);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((MainActivity) requireActivity()).setSuhuListener(null);
        ((MainActivity) requireActivity()).setJarakListener(null);
        ((MainActivity) requireActivity()).setAirListener(null);
        ((MainActivity) requireActivity()).setGasListener(null);
        ((MainActivity) requireActivity()).setAsapListener(null);
    }

    @Override
    public void onResume() {
        super.onResume();
        startPolling();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopPolling();
    }

    private void startPolling(){
        handler = new Handler();
        pollingTask = new Runnable() {
            @Override
            public void run() {
                ((MainActivity) requireActivity()).panggilSuhuAPI();
                ((MainActivity) requireActivity()).panggilJarakAPI();
                ((MainActivity) requireActivity()).panggilAirAPI();
                ((MainActivity) requireActivity()).panggilGasAPI();
                ((MainActivity) requireActivity()).panggilAsapAPI();
                handler.postDelayed(this,500);
            }
        };
        handler.post(pollingTask);
    }

    private void stopPolling(){
        if (handler != null && pollingTask != null){
            handler.removeCallbacks(pollingTask);
        }
    }

    @Override
    public void onSuhuReceived(String suhuReceived, String kelembapanReceived) {
        suhu.setText(String.format("%s℃", suhuReceived));
        kelembapan.setText(String.format("%s%%", kelembapanReceived));
    }

    @Override
    public void onJarakReceived(String jarakReceived, String servoStatus) {
        jarak.setText(String.format("%s | CM", jarakReceived));
    }

    @Override
    public void onAirReceived(String airReceived) {
        air.setText(airReceived);
    }

    @Override
    public void onAsapReceived(String asapReceived) {
        asap.setText(asapReceived);
    }

    @Override
    public void onGasReceived(String gasReceived) {
        gas.setText(gasReceived);
    }
}