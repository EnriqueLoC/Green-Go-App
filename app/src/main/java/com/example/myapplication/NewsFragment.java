package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class NewsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Integer> integerArrayList;
    private RecyclerViewAdapter recyclerViewAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);

        recyclerView = rootView.findViewById(R.id.recycler_view);

        integerArrayList = new ArrayList<>();
        integerArrayList.add(R.drawable.alsuperdiscount);
        integerArrayList.add(R.drawable.oxxodiscount);
        integerArrayList.add(R.drawable.samsdiscount);


        recyclerViewAdapter = new RecyclerViewAdapter(getContext(), integerArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(recyclerViewAdapter);

        return rootView;
    }
}