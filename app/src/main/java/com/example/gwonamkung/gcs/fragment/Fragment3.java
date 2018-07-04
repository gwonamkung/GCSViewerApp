package com.example.gwonamkung.gcs.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gwonamkung.gcs.R;
import com.example.gwonamkung.gcs.adapter.CommandListAdapter;

import java.text.SimpleDateFormat;

public class Fragment3 extends Fragment {
    static RecyclerView recyclerView;
    static CommandListAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment3, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        adapter = new CommandListAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);
        return view;
    }

    public void getCommand(String command){
        try {
            adapter.getList().add(command);
            adapter.notifyDataSetChanged();
        }catch (Exception e){}
    }
}
