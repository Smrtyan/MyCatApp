package com.example.ubnt.mycatapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void likeCat_click(View view) {


        Ion.with(this)
                .load("https://api.thecatapi.com/api/images/get?format=xml&size=med&results_per_page=9")
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        try {
                            GridLayout layout = findViewById(R.id.grid_pic);
                            layout.removeAllViews();
                            ImageView imageView;
                            JSONObject object = XML.toJSONObject(result);
                            JSONArray array = object.getJSONObject("response")
                                    .getJSONObject("data")
                                    .getJSONObject("images")
                                    .getJSONArray("image");
                            for(int i=0;i<array.length();i++){
                                imageView = new ImageView(MainActivity.this);
                                String url = ((JSONObject)array.get(i)).getString("url");
                                Log.v("inIon",url);
                                Ion.with(MainActivity.this)
                                        .load(url)
                                        .intoImageView(imageView);
                                layout.addView(imageView);
                            }
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }

                    }
                });
    }
}
