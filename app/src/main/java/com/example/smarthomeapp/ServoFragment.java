package com.example.smarthomeapp;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
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

public class ServoFragment extends Fragment implements MainActivity.ServoJendelaListener, MainActivity.ServoPintuListener {
    private TextView tv_garasi,tv_jendela,tv_pintu;
    private MaterialSwitch jendelaSwitch, pintuSwitch;
    private ImageView img_garasi,img_jendela,img_pintu;

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



        jendelaSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                tv_jendela.setText(b ? "Jendela Terbuka" : "Jendela Tertutup");
                ((MainActivity)requireActivity()).panggilServoJendelaAPI();
                img_jendela.setColorFilter(b ? ContextCompat.getColor(requireActivity(), R.color.blue) : ContextCompat.getColor(requireActivity(), R.color.gray));
            }
        });

        pintuSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                tv_pintu.setText(b ? "Pintu Terbuka" : "Pintu Tertutup");
                ((MainActivity)requireActivity()).panggilServoPintuAPI();
                img_pintu.setColorFilter(b ? ContextCompat.getColor(requireActivity(), R.color.blue) : ContextCompat.getColor(requireActivity(), R.color.gray));
            }
        });


        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ((MainActivity)context).setServoJendelaListener(this);
        ((MainActivity)context).setServoPintuListener(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((MainActivity)requireActivity()).setServoJendelaListener(null);
        ((MainActivity)requireActivity()).setServoPintuListener(null);
    }

    @Override
    public void onServoJendelaReceived(String servo) {
        tv_jendela.setText(servo);
    }

    @Override
    public void onServoPintuReceived(String servo) {
        tv_pintu.setText(servo);
    }
}