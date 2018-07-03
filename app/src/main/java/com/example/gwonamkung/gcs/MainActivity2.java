package com.example.gwonamkung.gcs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.gwonamkung.gcs.adapter.FragmentAdapterJ;
import com.example.gwonamkung.gcs.fragment.Fragment1;
import com.example.gwonamkung.gcs.fragment.Fragment2;
import com.example.gwonamkung.gcs.javacall.JavascriptCall;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity2 extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tabLayout;
    WebView webView;
    Context context;
    MqttClient mqttClient;
    public static double lat,log,heading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
//        webView.loadUrl("file:///android_asset/www/map.html");
        webView.addJavascriptInterface(new JavascriptCall(webView),"jsproxy");
        webView.setWebViewClient(new JavaWebViewClient());
        getSupportActionBar().hide();
        try {
            mqttClient = new MqttClient("tcp://192.168.3.16:1883", MqttClient.generateClientId(), null);
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
                                lat = jsonObject.getDouble("latitude");
                                log = jsonObject.getDouble("longitude");
                                heading = jsonObject.getDouble("heading");

//                                webView.loadUrl("javascript:setUavLocation("+lat+","+log+","+heading+")");
                                webView.loadDataWithBaseURL("file:///android_asset/www/","jsproxy.js","text/html","UTF-8",null);
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

    private class JavaWebViewClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl("file:///android_asset/www/map.html");
            return true;
        }
    }
}
