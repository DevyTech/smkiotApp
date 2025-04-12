package com.example.smarthomeapp;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.card.MaterialCardView;

public class LedFragment extends Fragment {
    private MaterialCardView teras,tengah,kamar1,kamar2,dapur,garasi;

    private ImageView ivTeras,ivTengah,ivKamar1,ivKamar2,ivDapur,ivGarasi;

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

        teras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
}