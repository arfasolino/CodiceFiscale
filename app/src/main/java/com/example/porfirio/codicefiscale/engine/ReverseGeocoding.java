package com.example.porfirio.codicefiscale.engine;

import android.content.Context;
import android.location.Location;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

public class ReverseGeocoding {


    public static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    private static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static String getCity(String lat, String lon) {
        //https://maps.googleapis.com/maps/api/geocode/json?&latlng=40.75,14.05
        //MY API key AIzaSyAXtPSk3UEy7HoU5jTPhQLxSk30RwEYrj8
        JSONObject jsonObject = null;
        InputStream is = null;
        try {
            is = new URL("https://maps.googleapis.com/maps/api/geocode/json?latlng="+lat+","+lon+"&key=AIzaSyAXtPSk3UEy7HoU5jTPhQLxSk30RwEYrj8").openStream();
            //is = new URL("https://maps.googleapis.com/maps/api/geocode/json?latlng=40.75,14.05&key=AIzaSyAXtPSk3UEy7HoU5jTPhQLxSk30RwEYrj8").openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String jsonText = null;
        try {
            jsonText = convertStreamToString(is);
            jsonObject = new JSONObject(jsonText);
            JSONArray results=jsonObject.getJSONArray("results");
            JSONObject adcomp = results.getJSONObject(0);
            JSONArray adcomp2=adcomp.getJSONArray("address_components");
            JSONObject city=adcomp2.getJSONObject(2);
            String nomeCitta=(String) city.get("long_name");
            return nomeCitta;

        } catch (JSONException e) {
            return "Roma";
        }

        //CERCA MEGLIO LA CITTA NEL JSON
    //return "Roma";
    }


}