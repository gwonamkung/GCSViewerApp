package com.example.gwonamkung.gcs.javacall;

import android.os.Handler;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.example.gwonamkung.gcs.MainActivity2;

public class JavascriptCall {
    private  final Handler handler = new Handler();
    private WebView mWebView;

    public JavascriptCall(WebView webView){
        this.mWebView = webView;
    }

    @JavascriptInterface
    public void requestLocation(){
        handler.post(new Runnable() {
            @Override
            public void run() {
                mWebView.loadUrl("javascript:setUavLocation("+ MainActivity2.lat+","+MainActivity2.log+","+MainActivity2.heading+")");
            }
        });
    }
}
