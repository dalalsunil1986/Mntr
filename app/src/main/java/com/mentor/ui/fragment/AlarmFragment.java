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
import android.widget.TextView;

import com.github.glomadrian.loadingballs.BallView;
import com.mentor.R;
import com.mentor.api.MentorApiService;
import com.mentor.api.models.GetWakieModel;
import com.mentor.ui.adapters.AlarmsAdapter;
import com.mentor.ui.viewmodels.WakieItem;
import com.mentor.util.GeneralUtils;
import com.mentor.util.SnackBarFactory;

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
    @Bind(R.id.progress)
    BallView progress;
    @Bind(R.id.empty)
    TextView empty;

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

        alarmsAdapter = new AlarmsAdapter(getActivity(), wakieItems);
        alarms.setAdapter(alarmsAdapter);

        swipelayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchWakies();
            }
        });

        fetchWakies();

    }

    public void fetchWakies() {
        empty.setVisibility(View.GONE);

        progress.setVisibility(View.VISIBLE);
        Call<List<GetWakieModel>> wakieListCall = mentorApiService.getWakies(1, 1000, GeneralUtils.getUniquePsuedoID());

        wakieListCall.enqueue(new Callback<List<GetWakieModel>>() {
            @Override
            public void onResponse(Response<List<GetWakieModel>> response, Retrofit retrofit) {

                List<GetWakieModel> wakieModels = response.body();
                if(wakieModels!=null)
                {
                    if(wakieModels.isEmpty())
                    {
                        empty.setVisibility(View.VISIBLE);
                        progress.setVisibility(View.GONE);

                        return;
                    }

                    alarmsAdapter.removeAll();

                    for(GetWakieModel wakieModel:wakieModels)
                    {
                        WakieItem wakieItem = new WakieItem();
                        wakieItem.setDate(wakieModel.getTime());
                        wakieItem.setTime(wakieModel.getTime());
                        wakieItem.setWakieId(wakieModel.getWakieId());
                        wakieItem.setMentorName(wakieModel.getMentorName());
                        wakieItem.setMentorPicUrl(wakieModel.getMentorPicUrl());
                        wakieItem.setVibrate(wakieModel.getVibrate());

                        alarmsAdapter.add(wakieItem);
                    }
                }
                else
                {
                    SnackBarFactory.createSnackbar(getActivity(),getView(),R.string.something_wrong).show();
                }

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Throwable t) {
                progress.setVisibility(View.GONE);
                SnackBarFactory.createSnackbar(getActivity(),getView(),t.getMessage()).show();

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
