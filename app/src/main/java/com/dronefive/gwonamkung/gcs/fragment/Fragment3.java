package com.dronefive.gwonamkung.gcs.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dronefive.gwonamkung.gcs.R;
import com.dronefive.gwonamkung.gcs.adapter.CommandListAdapter;
import com.dronefive.gwonamkung.gcs.data.CommandData;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Fragment3 extends Fragment {
    static RecyclerView recyclerView;
    static CommandListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment3, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        adapter = new CommandListAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        return view;
    }

    public void getCommand(String command) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("hh : mm : ss");
            adapter.getList().add(new CommandData(format.format(new Date().getTime()), command));
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
        }
    }
}
