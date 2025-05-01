package com.example.smarthomeapp;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.card.MaterialCardView;

public class LedFragment extends Fragment implements MainActivity.LedStatusListener{
    private MaterialCardView all,tengah,kamar1,kamar2,dapur,garasi;

    private ImageView ivall,ivTengah,ivKamar1,ivKamar2,ivDapur,ivGarasi;
    private boolean isallOn = false;
    private int countLamp = 0;

    private Handler handler;
    private Runnable pollingTask;

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
                        Log.d("Response Led All:", led);

                        updateLampUI(tengah,ivTengah,led);
                        updateLampUI(kamar1,ivKamar1,led);
                        updateLampUI(kamar2,ivKamar2,led);
                        updateLampUI(dapur,ivDapur,led);
                        updateLampUI(garasi,ivGarasi,led);

                        if (led.equals("OFF")){
                            all.setCardBackgroundColor(Color.TRANSPARENT);
                            ivall.setImageResource(R.drawable.baseline_lightbulb_outline_148);
                            countLamp = 0;
                        } else {
                            all.setCardBackgroundColor(ContextCompat.getColor(getView().getContext(), R.color.blue));
                            ivall.setImageResource(R.drawable.baseline_lightbulb_148);
                            countLamp = 5;
                        }
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("Response Error Led : ", error);
                    }
                });
            }
        });

        tengah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)requireActivity()).toggleLed("tengah", new API.LEDCallback() {
                    @Override
                    public void onSuccess(String led) {
                        Log.d("Response Led Tengah:", led);
                        if (led.equals("OFF")){
                            tengah.setCardBackgroundColor(Color.TRANSPARENT);
                            ivTengah.setImageResource(R.drawable.baseline_lightbulb_outline_148);
                            countLamp-=1;
                        } else {
                            tengah.setCardBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.blue));
                            ivTengah.setImageResource(R.drawable.baseline_lightbulb_148);
                            countLamp+=1;
                        }
                        updateLampAll();
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("Response Error Led : ", error);
                    }
                });
            }
        });

        kamar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)requireActivity()).toggleLed("kamar1", new API.LEDCallback() {
                    @Override
                    public void onSuccess(String led) {
                        Log.d("Response Led :", led);
                        if (led.equals("OFF")){
                            kamar1.setCardBackgroundColor(Color.TRANSPARENT);
                            ivKamar1.setImageResource(R.drawable.baseline_lightbulb_outline_148);
                            countLamp-=1;
                        } else {
                            kamar1.setCardBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.blue));
                            ivKamar1.setImageResource(R.drawable.baseline_lightbulb_148);
                            countLamp+=1;
                        }
                        updateLampAll();
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("Response Error Led : ", error);
                    }
                });
            }
        });
        kamar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)requireActivity()).toggleLed("kamar2", new API.LEDCallback() {
                    @Override
                    public void onSuccess(String led) {
                        Log.d("Response Led :", led);
                        if (led.equals("OFF")){
                            kamar2.setCardBackgroundColor(Color.TRANSPARENT);
                            ivKamar2.setImageResource(R.drawable.baseline_lightbulb_outline_148);
                            countLamp-=1;
                        } else {
                            kamar2.setCardBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.blue));
                            ivKamar2.setImageResource(R.drawable.baseline_lightbulb_148);
                            countLamp+=1;
                        }
                        updateLampAll();
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("Response Error Led : ", error);
                    }
                });
            }
        });
        dapur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)requireActivity()).toggleLed("dapur", new API.LEDCallback() {
                    @Override
                    public void onSuccess(String led) {
                        Log.d("Response Led :", led);
                        if (led.equals("OFF")){
                            dapur.setCardBackgroundColor(Color.TRANSPARENT);
                            ivDapur.setImageResource(R.drawable.baseline_lightbulb_outline_148);
                            countLamp-=1;
                        } else {
                            dapur.setCardBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.blue));
                            ivDapur.setImageResource(R.drawable.baseline_lightbulb_148);
                            countLamp+=1;
                        }
                        updateLampAll();
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("Response Error Led : ", error);
                    }
                });
            }
        });
        garasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)requireActivity()).toggleLed("garasi", new API.LEDCallback() {
                    @Override
                    public void onSuccess(String led) {
                        Log.d("Response Led :", led);
                        if (led.equals("OFF")){
                            garasi.setCardBackgroundColor(Color.TRANSPARENT);
                            ivGarasi.setImageResource(R.drawable.baseline_lightbulb_outline_148);
                            countLamp-=1;
                        } else {
                            garasi.setCardBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.blue));
                            ivGarasi.setImageResource(R.drawable.baseline_lightbulb_148);
                            countLamp+=1;
                        }
                        updateLampAll();
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("Response Error Led : ", error);
                    }
                });
            }
        });

        return view;
    }

    private void updateLampAll(){
        Log.d("Count Lamp: ", String.valueOf(countLamp));
        if (countLamp==5){
            all.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue));
            ivall.setImageResource(R.drawable.baseline_lightbulb_148);
        }else {
            all.setCardBackgroundColor(Color.TRANSPARENT);
            ivall.setImageResource(R.drawable.baseline_lightbulb_outline_148);
        }
    }
    private void updateLampUI(MaterialCardView cardView, ImageView imageView, String ledStatus){
        if (ledStatus.equals("ON")){
            cardView.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue));
            imageView.setImageResource(R.drawable.baseline_lightbulb_148);
        }else {
            cardView.setCardBackgroundColor(Color.TRANSPARENT);
            imageView.setImageResource(R.drawable.baseline_lightbulb_outline_148);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ((MainActivity)context).setLedStatusListener(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((MainActivity)requireActivity()).setLedStatusListener(null);
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
                ((MainActivity) requireActivity()).panggilLedStatusAPI();
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
    public void onLedStatusReceived(String allStatus, String tengahStatus, String kamar1Status, String kamar2Status, String dapurStatus, String garasiStatus) {
        updateLampUI(all,ivall,allStatus);
        updateLampUI(tengah,ivTengah,tengahStatus);
        updateLampUI(kamar1,ivKamar1,kamar1Status);
        updateLampUI(kamar2,ivKamar2,kamar2Status);
        updateLampUI(dapur,ivDapur,dapurStatus);
        updateLampUI(garasi,ivGarasi,garasiStatus);
    }
}