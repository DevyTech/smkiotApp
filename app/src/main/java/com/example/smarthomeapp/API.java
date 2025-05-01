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
    public static void getSuhu(Context context, String url, SuhuCallback callback){
        JsonObjectRequest suhuRequest = new JsonObjectRequest(Request.Method.GET, url+"/kelembapan",
                null, response -> {
            try {
                String statusSuhu = response.getString("temperature");
                String statusKelembapan = response.getString("humidity");
                callback.onSuccess(statusSuhu, statusKelembapan);
            }catch (Exception e){
                callback.onError(e.toString());
                Log.e("Error get Kelembapan : ", e.toString());
            }
        }, error -> {
            if (error != null){
                callback.onError(error.toString());
                Log.e("Response Error Kelembapan : ", error.toString());
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(suhuRequest);
    }

    public static void getJarak(Context context, String url, JarakCallback callback){
        JsonObjectRequest jarakRequest = new JsonObjectRequest(Request.Method.GET, url+"/jarak",
                null, response -> {
            try {
                String statusJarak = response.getString("distance");
                String statusServo = response.getString("servo");
                callback.onSuccess(statusJarak, statusServo);
            }catch (Exception e){
                callback.onError(e.toString());
                Log.e("Error get Jarak : ", e.toString());
            }
        }, error -> {
            if (error != null){
                callback.onError(error.toString());
                Log.e("Response Error Jarak : ", error.toString());
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(jarakRequest);
    }

    public static void getAir(Context context, String url, AirCallback callback){
        StringRequest airRequest = new StringRequest(Request.Method.GET, url + "/hujan"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String statusAir = response;
                callback.onSuccess(statusAir);
                Log.d("Response Air : ", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(error.toString());
                Log.e("Response Error Air : ", error.toString());
            }
        });

        MySingleton.getInstance(context).addToRequestQueue(airRequest);
    }
    public static void getGas(Context context, String url, GasCallback callback){
        StringRequest gasRequest = new StringRequest(Request.Method.GET, url + "/gas"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String statusGas = response;
                callback.onSuccess(statusGas);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(error.toString());
                Log.e("Response Error Gas : ", error.toString());
            }
        });

        MySingleton.getInstance(context).addToRequestQueue(gasRequest);
    }

    public static void getAsap(Context context, String url, AsapCallback callback){
        StringRequest asapRequest = new StringRequest(Request.Method.GET, url + "/asap"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String statusAsap = response;
                callback.onSuccess(statusAsap);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(error.toString());
                Log.e("Response Error Asap : ", error.toString());
            }
        });

        MySingleton.getInstance(context).addToRequestQueue(asapRequest);
    }

    public static void toggleLed(Context context, String url, String ledId, LEDCallback callback){
        String fullUrl = url + "/toggle-led?led=" + ledId;

        StringRequest ledRequest = new StringRequest(Request.Method.GET, fullUrl
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int maxLength = Math.min(500, response.length());
                Log.d("Response " + ledId, "Response is : " + response.substring(0, maxLength));
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(error.toString());
                Log.e("Response Error Led : ", error.toString());
            }
        });

        MySingleton.getInstance(context).addToRequestQueue(ledRequest);
    }
    public static void toggleServoJendela(Context context, String url, ServoJendelaCallback callback){
        StringRequest jendelaRequest = new StringRequest(Request.Method.GET, url + "/servoJendela"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String statusServo = response;
                callback.onSuccess(statusServo);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(error.toString());
                Log.e("Response Error Jendela : ", error.toString());
            }
        });

        MySingleton.getInstance(context).addToRequestQueue(jendelaRequest);
    }
    public static void toggleServoPintu(Context context, String url, ServoPintuCallback callback){
        StringRequest pintuRequest = new StringRequest(Request.Method.GET, url + "/servoPintu"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String statusServo = response;
                callback.onSuccess(statusServo);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(error.toString());
                Log.e("Response Error Pintu : ", error.toString());
            }
        });

        MySingleton.getInstance(context).addToRequestQueue(pintuRequest);
    }

}
