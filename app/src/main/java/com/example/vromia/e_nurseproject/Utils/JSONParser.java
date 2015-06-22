package com.example.vromia.e_nurseproject.Utils;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by Vromia on 5/6/2015.
 */
public class JSONParser {

    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";

    public JSONParser() {

    }

    public JSONObject makeHttpRequest(String url, String method, List<NameValuePair> params) {

        try {

            if (method == "POST") {
               //Create a Client that he will request a web service
                DefaultHttpClient httpClient = new DefaultHttpClient();
                //Create a HttpPost that will request from a server with post method
                HttpPost httpPost = new HttpPost(url);
                //Set the entity of this http post, more specific the params that php script needs to check
                httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
                //take the response of server
                HttpResponse httpResponse = httpClient.execute(httpPost);

                //Get the entities of the response which are messages ( echo )
                HttpEntity httpEntity = httpResponse.getEntity();

                if(httpEntity!=null){
                    String responseBody=EntityUtils.toString(httpEntity,HTTP.UTF_8);
                    Log.i("ResponseBody",responseBody);
                    json=responseBody;
                }




                //Initialize InputStream with the context of HttpEntity from the response of server
                //is = httpEntity.getContent();
               // json= EntityUtils.toString(httpEntity, HTTP.UTF_8);

            } else if (method == "GET") {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                String paramString = URLEncodedUtils.format(params, "utf-8");
                url += "?" + paramString;
                HttpGet httpGet = new HttpGet(url);


                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                if(httpEntity!=null){
                    String responseBody=EntityUtils.toString(httpEntity,HTTP.UTF_8);
                    Log.i("ResponseBody",responseBody);
                    json=responseBody;
                }

               // is = httpEntity.getContent();

            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
/*

        try{
            //Create a BufferedReader that will read the InputStream
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            //Save the string that it is json  ( that happens because the entity contains only json message )
            json = sb.toString();

        }catch (Exception e){
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
*/
        Log.i("JSON",json+"1");

        try {
            //Create a json Object from the string that contains json format
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }


        return jObj;
    }


}
