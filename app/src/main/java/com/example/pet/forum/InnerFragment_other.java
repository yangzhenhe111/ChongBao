package com.example.pet.forum;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pet.R;
import com.example.pet.other.entity.Tips;

import java.util.ArrayList;
import java.util.List;

public class InnerFragment_other extends Fragment {

    private List<Tips> tipsList;
    private RecyclerView recyclerView;
    private RecyAdapter recyAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inner_other, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        init();
        return view;
    }

    public void init(){
        tipsList = new ArrayList<>();
        for (int i=0;i<10;i++){
            Tips tips = new Tips();
            tips.setComments(i);
            tips.setLikes(i);
            tips.setTime("时间"+i);
            tips.setTitle("标题"+i);
            tipsList.add(tips);
        }
        recyAdapter = new RecyAdapter(getContext(),tipsList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyAdapter);
    }
}