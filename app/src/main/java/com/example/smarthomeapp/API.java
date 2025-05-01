package com.example.smarthomeapp;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

public class API {

    public interface SuhuCallback{
        void onSuccess(String suhu, String kelembapan);
        void onError(String error);
    }

    public interface JarakCallback{
        void onSuccess(String jarak, String servo);
        void onError(String error);
    }

    public interface AirCallback{
        void onSuccess(String air);
        void onError(String error);
    }

    public interface GasCallback{
        void onSuccess(String gas);
        void onError(String error);
    }

    public interface AsapCallback{
        void onSuccess(String asap);
        void onError(String error);
    }

    public interface LEDCallback{
        void onSuccess(String led);
        void onError(String error);
    }
    public interface ServoJendelaCallback{
        void onSuccess(String servo);
        void onError(String error);
    }
    public interface ServoPintuCallback{
        void onSuccess(String servo);
        void onError(String error);
    }
    public static void getSuhu(Context context, String url, SuhuCallback suhuCallback){
        JsonObjectRequest suhuRequest = new JsonObjectRequest(Request.Method.GET, url+"/kelembapan",
                null, response -> {
            try {
                String statusSuhu = response.getString("temperature");
                String statusKelembapan = response.getString("humidity");
                suhuCallback.onSuccess(statusSuhu, statusKelembapan);
            }catch (Exception e){
                suhuCallback.onError(e.toString());
                Log.e("Error get Kelembapan : ", e.toString());
            }
        }, error -> {
            if (error != null){
                suhuCallback.onError(error.toString());
                Log.e("Response Error Kelembapan : ", error.toString());
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(suhuRequest);
    }

    public static void getJarak(Context context, String url, JarakCallback jarakCallback){
        JsonObjectRequest jarakRequest = new JsonObjectRequest(Request.Method.GET, url+"/jarak",
                null, response -> {
            try {
                String statusJarak = response.getString("distance");
                String statusServo = response.getString("servo");
                jarakCallback.onSuccess(statusJarak, statusServo);
            }catch (Exception e){
                jarakCallback.onError(e.toString());
                Log.e("Error get Jarak : ", e.toString());
            }
        }, error -> {
            if (error != null){
                jarakCallback.onError(error.toString());
                Log.e("Response Error Jarak : ", error.toString());
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(jarakRequest);
    }

    public static void getAir(Context context, String url, AirCallback airCallback){
        StringRequest airRequest = new StringRequest(Request.Method.GET, url + "/hujan"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String statusAir = response;
                airCallback.onSuccess(statusAir);
                Log.d("Response Air : ", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                airCallback.onError(error.toString());
                Log.e("Response Error Air : ", error.toString());
            }
        });

        MySingleton.getInstance(context).addToRequestQueue(airRequest);
    }
    public static void getGas(Context context, String url, GasCallback gasCallback){
        StringRequest gasRequest = new StringRequest(Request.Method.GET, url + "/gas"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String statusGas = response;
                gasCallback.onSuccess(statusGas);
                Log.d("Response Gas : ", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                gasCallback.onError(error.toString());
                Log.e("Response Error Gas : ", error.toString());
            }
        });

        MySingleton.getInstance(context).addToRequestQueue(gasRequest);
    }

    public static void getAsap(Context context, String url, AsapCallback asapCallback){
        StringRequest asapRequest = new StringRequest(Request.Method.GET, url + "/asap"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String statusAsap = response;
                asapCallback.onSuccess(statusAsap);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                asapCallback.onError(error.toString());
                Log.e("Response Error Asap : ", error.toString());
            }
        });

        MySingleton.getInstance(context).addToRequestQueue(asapRequest);
    }

    public static void toggleLed(Context context, String url, String ledId, LEDCallback ledCallback){
        String fullUrl = url + "/toggle-led?led=" + ledId;

        StringRequest ledRequest = new StringRequest(Request.Method.GET, fullUrl
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int maxLength = Math.min(500, response.length());
                Log.d("Response " + ledId, "Response is : " + response.substring(0, maxLength));
                ledCallback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ledCallback.onError(error.toString());
                Log.e("Response Error Led : ", error.toString());
            }
        });

        MySingleton.getInstance(context).addToRequestQueue(ledRequest);
    }
    public static void toggleServoJendela(Context context, String url, ServoJendelaCallback jendelaCallback){
        StringRequest jendelaRequest = new StringRequest(Request.Method.GET, url + "/servoJendela"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String statusServo = response;
                jendelaCallback.onSuccess(statusServo);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                jendelaCallback.onError(error.toString());
                Log.e("Response Error Jendela : ", error.toString());
            }
        });

        MySingleton.getInstance(context).addToRequestQueue(jendelaRequest);
    }
    public static void toggleServoPintu(Context context, String url, ServoPintuCallback pintuCallback){
        StringRequest pintuRequest = new StringRequest(Request.Method.GET, url + "/servoPintu"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String statusServo = response;
                pintuCallback.onSuccess(statusServo);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pintuCallback.onError(error.toString());
                Log.e("Response Error Pintu : ", error.toString());
            }
        });

        MySingleton.getInstance(context).addToRequestQueue(pintuRequest);
    }

}
