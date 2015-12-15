package com.mentor.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mentor.R;
import com.mentor.ui.adapters.AlarmsAdapter;
import com.mentor.ui.viewmodels.WakieItem;

import java.lang.reflect.Array;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlarmFragment extends Fragment {


    @Bind(R.id.alarms)
    RecyclerView alarms;
    @Bind(R.id.swipelayout)
    SwipeRefreshLayout swipelayout;
    AlarmsAdapter alarmsAdapter;
    ArrayList<WakieItem> wakieItems;

    public AlarmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_alarm, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        alarms.setLayoutManager(layoutManager);

        alarmsAdapter=new AlarmsAdapter(getActivity(),wakieItems);
        alarms.setAdapter(alarmsAdapter);

    }

    public void fetchWakies()
    {
        
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
