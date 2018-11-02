package com.dronefive.gwonamkung.gcs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.dronefive.gwonamkung.gcs.adapter.FragmentAdapterJ;
import com.dronefive.gwonamkung.gcs.fragment.Fragment1;
import com.dronefive.gwonamkung.gcs.fragment.Fragment2;
import com.dronefive.gwonamkung.gcs.fragment.Fragment3;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity2 extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tabLayout;
    WebView webView;
    Context context;
    MqttClient mqttClient;
    Toast toast;
    Button dd;
    public static String message2;
    public static double lat, lng, heading;
    public static double homeLat, homeLng;
    public static double gotoLat, gotoLng;
    public static double x, y, r;
    public static boolean gotoCheck, missionCheck, noflyzoneCheck, d_noflyzone, mission_start_check;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        String ip = getIntent().getStringExtra("ip");

        final Fragment3 fragment3 = new Fragment3();
        webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/www/map.html");
        toast = Toast.makeText(this, message2, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 70);
        try {
            mqttClient = new MqttClient("tcp://"+ip+":1883", MqttClient.generateClientId(), null);
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
                            new Fragment1().Companion.json(jsonObject);
                            new Fragment2().setJson(jsonObject);

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
                                noflyzoneCheck = jsonObject.getBoolean("noflyzone_check");
                                d_noflyzone = jsonObject.getBoolean("d_noflyzone");
                                mission_start_check = jsonObject.getBoolean("mission_do_check");

                                message2 = jsonObject.getString("statustext");
                                if(!message2.equals("")) {
                                    fragment3.getCommand(message2);
                                    toast.setText(message2);
                                    toast.show();
                                }
                                if (gotoCheck) {
                                    gotoLng = jsonObject.getDouble("gotoLng");
                                    gotoLat = jsonObject.getDouble("gotoLat");
                                    JSONObject json = new JSONObject();
                                    json.put("lat", gotoLat);
                                    json.put("lng", gotoLng);
                                    webView.loadUrl("javascript:jsproxy.gotoStart(" + json.toString() + ")");
                                }

                                // GCS에서 Mission Download시 미션 받아오기
                                if (missionCheck) {
                                    JSONArray jsonArray = jsonObject.getJSONArray("android_waypoints");
                                    System.out.println(jsonArray.toString() + "   11111111");
                                    webView.loadUrl("javascript:jsproxy.setMission(" + jsonArray.toString() + ")");
                                }

                                if (noflyzoneCheck) {
                                    x = jsonObject.getDouble("x");
                                    y = jsonObject.getDouble("y");
                                    r = jsonObject.getDouble("r");
                                    webView.loadUrl("javascript:jsproxy.makeNoFlyZone(" + x + "," + y + "," + r + ")");
                                }
                                if (d_noflyzone) {
                                    webView.loadUrl("javascript:jsproxy.deleteNoFlyZone()");
                                }
                                if (mission_start_check) {
                                    webView.loadUrl("javascript:jsproxy.missionStart()");
                                }
                                webView.loadUrl("javascript:jsproxy.setNextWaypointNo(" + jsonObject.getInt("next_waypoint_no") + ")");
                                webView.loadUrl("javascript:jsproxy.setUavLocation(" + lat + "," + lng + "," + heading + ")");
                                webView.loadUrl("javascript:jsproxy.setHomeLocation(" + homeLat + "," + homeLng + ")");
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
            if(mqttClient.isConnected()) {
                Toast.makeText(this, ip + " 에 연결되었습니다.", Toast.LENGTH_SHORT).show();
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }

        webView = findViewById(R.id.webView);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        context = this;
        tabLayout.addTab(tabLayout.newTab().setText("Data"));
        tabLayout.addTab(tabLayout.newTab().setText("Detail Data"));
        tabLayout.addTab(tabLayout.newTab().setText("Log"));
        viewPager.setAdapter(new FragmentAdapterJ(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(2);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
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
