package com.example.smarthomeapp;

import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.card.MaterialCardView;

public class LedFragment extends Fragment {
    private MaterialCardView all,tengah,kamar1,kamar2,dapur,garasi;

    private ImageView ivall,ivTengah,ivKamar1,ivKamar2,ivDapur,ivGarasi;
    private boolean isallOn = false;
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

        all = view.findViewById(R.id.mcv_ledAll);
        tengah = view.findViewById(R.id.mcv_ledTengah);
        kamar1 = view.findViewById(R.id.mcv_ledKamar1);
        kamar2 = view.findViewById(R.id.mcv_ledKamar2);
        dapur = view.findViewById(R.id.mcv_ledDapur);
        garasi = view.findViewById(R.id.mcv_ledGarasi);

        ivall = view.findViewById(R.id.imgvAll);
        ivTengah = view.findViewById(R.id.imgvTengah);
        ivKamar1 = view.findViewById(R.id.imgvKamar1);
        ivKamar2 = view.findViewById(R.id.imgvKamar2);
        ivDapur = view.findViewById(R.id.imgvDapur);
        ivGarasi = view.findViewById(R.id.imgvGarasi);

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)requireActivity()).toggleLed("all", new API.LEDCallback() {
                    @Override
                    public void onSuccess(String led) {
                        Log.d("Response Led :", led);
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("Response Error Led : ", error);
                    }
                });
                if (isallOn){
                    all.setCardBackgroundColor(Color.TRANSPARENT);
                    ivall.setImageResource(R.drawable.baseline_lightbulb_outline_148);
                } else {
                    all.setCardBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.blue));
                    ivall.setImageResource(R.drawable.baseline_lightbulb_148);
                }
                isallOn = !isallOn;
            }
        });

        tengah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)requireActivity()).toggleLed("tengah", new API.LEDCallback() {
                    @Override
                    public void onSuccess(String led) {
                        Log.d("Response Led :", led);
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("Response Error Led : ", error);
                    }
                });
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
                ((MainActivity)requireActivity()).toggleLed("kamar1", new API.LEDCallback() {
                    @Override
                    public void onSuccess(String led) {
                        Log.d("Response Led :", led);
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("Response Error Led : ", error);
                    }
                });
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
                ((MainActivity)requireActivity()).toggleLed("kamar2", new API.LEDCallback() {
                    @Override
                    public void onSuccess(String led) {
                        Log.d("Response Led :", led);
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("Response Error Led : ", error);
                    }
                });
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
                ((MainActivity)requireActivity()).toggleLed("dapur", new API.LEDCallback() {
                    @Override
                    public void onSuccess(String led) {
                        Log.d("Response Led :", led);
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("Response Error Led : ", error);
                    }
                });
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
                ((MainActivity)requireActivity()).toggleLed("garasi", new API.LEDCallback() {
                    @Override
                    public void onSuccess(String led) {
                        Log.d("Response Led :", led);
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("Response Error Led : ", error);
                    }
                });
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