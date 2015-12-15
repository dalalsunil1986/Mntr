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
import com.mentor.api.MentorApiService;
import com.mentor.api.models.GetWakieModel;
import com.mentor.ui.adapters.AlarmsAdapter;
import com.mentor.ui.viewmodels.WakieItem;
import com.mentor.util.GeneralUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlarmFragment extends BaseFragment {


    @Bind(R.id.alarms)
    RecyclerView alarms;
    @Bind(R.id.swipelayout)
    SwipeRefreshLayout swipelayout;
    AlarmsAdapter alarmsAdapter;
    ArrayList<WakieItem> wakieItems;

    @Inject
    MentorApiService mentorApiService;

    public AlarmFragment() {
        applicationComponent().inject(this);
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
        Call<List<GetWakieModel>> wakieListCall = mentorApiService.getWakies(1,1000, GeneralUtils.getUniquePsuedoID());

        wakieListCall.enqueue(new Callback<List<GetWakieModel>>() {
            @Override
            public void onResponse(Response<List<GetWakieModel>> response, Retrofit retrofit) {

            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
