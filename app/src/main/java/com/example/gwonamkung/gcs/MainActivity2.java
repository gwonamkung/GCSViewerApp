package com.example.gwonamkung.gcs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.gwonamkung.gcs.adapter.FragmentAdapterJ;
import com.example.gwonamkung.gcs.fragment.Fragment1;
import com.example.gwonamkung.gcs.fragment.Fragment2;
import com.example.gwonamkung.gcs.mission.WayPoint;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tabLayout;
    WebView webView;
    Context context;
    MqttClient mqttClient;
    public static double lat,lng,heading;
    public static double homeLat, homeLng;
    public static double gotoLat, gotoLng;
    public static boolean gotoCheck, missionCheck;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl("file:///android_asset/www/map.html");
//        webView.loadDataWithBaseURL("file:///android_asset/www/","jsproxy.js","text/html","UTF-8",null);
//        webView.loadDataWithBaseURL("file:///android_asset/www/","map.js","text/html","UTF-8",null);
//        webView.loadDataWithBaseURL("file:///android_asset/www/","uav.js","text/html","UTF-8",null);

        try {
            mqttClient = new MqttClient("tcp://106.253.56.122:1883", MqttClient.generateClientId(), null);
            mqttClient.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {

                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    final String strJson = new String(message.getPayload());
                    final JSONObject jsonObject = new JSONObject(strJson);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new Fragment2().setJson(jsonObject.toString());
                            new Fragment1().Companion.str(jsonObject.toString());
                            try {
                                // 이동 방향 및 Heading
                                lat = jsonObject.getDouble("latitude");
                                lng = jsonObject.getDouble("longitude");
                                heading = jsonObject.getDouble("heading");

                                // home 좌표
                                homeLat = jsonObject.getDouble("homeLat");
                                homeLng = jsonObject.getDouble("homeLng");

                                // goto 인지 체크
                                gotoCheck = jsonObject.getBoolean("goto_check");
                                missionCheck = jsonObject.getBoolean("mission_check");
                                if(gotoCheck){
                                    gotoLng = jsonObject.getDouble("gotoLng");
                                    gotoLat = jsonObject.getDouble("gotoLat");
                                    JSONObject json = new JSONObject();
                                    json.put("lat",gotoLat);
                                    json.put("lng",gotoLng);
                                    webView.loadUrl("javascript:jsproxy.gotoStart("+json.toString()+")");
                                    Log.d("111111",json.toString());
                                }

                                // GCS에서 Mission Download시 미션 받아오기
                                JSONArray jsonArray = jsonObject.getJSONArray("waypoints");
                                if(jsonArray.length() > 0) {
                                    webView.loadUrl("javascript:jsproxy.setMission("+jsonArray.toString()+")");
                                    System.out.println(jsonArray.get(0).toString());
                                }
                                webView.loadUrl("javascript:jsproxy.setUavLocation("+lat+","+lng+","+heading+")");
                                webView.loadUrl("javascript:jsproxy.setHomeLocation("+homeLat+","+homeLng+")");
                                //setHomeLocation
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {

                }
            });
            mqttClient.connect();
            mqttClient.subscribe("/uav2/pub");
        } catch (MqttException e) {
            e.printStackTrace();
        }

        webView = findViewById(R.id.webView);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        context = this;
        tabLayout.addTab(tabLayout.newTab().setText("1"));
        tabLayout.addTab(tabLayout.newTab().setText("2"));
        viewPager.setAdapter(new FragmentAdapterJ(getSupportFragmentManager()));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Toast.makeText(context, tab.getPosition() + 1 + " selected.", Toast.LENGTH_LONG).show();
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }
}
