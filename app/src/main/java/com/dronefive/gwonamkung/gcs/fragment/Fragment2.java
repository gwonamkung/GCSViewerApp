package com.dronefive.gwonamkung.gcs.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dronefive.gwonamkung.gcs.MainActivity2;
import com.dronefive.gwonamkung.gcs.R;

import org.json.JSONObject;

public class Fragment2 extends Fragment {
    static TextView mode_text2;
    static TextView airspeed_text2;
    static TextView ground_text2;
    static TextView dist_home_text;
    static TextView fence_text;
    static TextView alt_text2;
    static TextView missiontime_text;
    static TextView airtime_text;
    static TextView mission_text;
    static TextView nofly_text;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2, container, false);
        mode_text2 = view.findViewById(R.id.mode_text2);
        airspeed_text2 = view.findViewById(R.id.airspeed_text2);
        ground_text2 = view.findViewById(R.id.ground_text2);
        dist_home_text = view.findViewById(R.id.dist_home_text);
        fence_text = view.findViewById(R.id.fence_text);
        alt_text2 = view.findViewById(R.id.alt_text2);
        missiontime_text = view.findViewById(R.id.missiontime_text);
        airtime_text = view.findViewById(R.id.airtime_text);
        mission_text = view.findViewById(R.id.mission_text);
        nofly_text = view.findViewById(R.id.nofly_text);
        return view;
    }

    static public void setJson(JSONObject jsonObject) {
        try {
            mode_text2.setText(jsonObject.getString("mode"));
            airspeed_text2.setText(("" + jsonObject.getDouble("airspeed")).substring(0, 6));
            ground_text2.setText(("" + jsonObject.getDouble("groundspeed")).substring(0, 6));
            alt_text2.setText(jsonObject.getDouble("altitude") + "");
            if (jsonObject.getJSONObject("fence_info").getDouble("fence_enable") != 0) fence_text.setText("Fence uploaded");
            if (MainActivity2.missionCheck) mission_text.setText("Mission uploaded");
            if (MainActivity2.mission_start_check) missiontime_text.setText(jsonObject.getString(""));
            if (MainActivity2.noflyzoneCheck) nofly_text.setText("Nofly zone set");
        } catch (Exception e) {
        }
    }
}
