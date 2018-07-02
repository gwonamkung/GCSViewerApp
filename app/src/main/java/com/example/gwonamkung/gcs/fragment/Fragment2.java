package com.example.gwonamkung.gcs.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gwonamkung.gcs.R;

public class Fragment2 extends Fragment {
    static TextView textView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2,container,false);
        textView = view.findViewById(R.id.textView2);
        return view;
    }

    static public void setJson(String json){
        try {
            textView.setText(json);
        }catch (Exception e){}
    }
}
