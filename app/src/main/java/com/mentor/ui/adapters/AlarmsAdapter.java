package com.mentor.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Joel on 14/12/2015.
 */
public class AlarmsAdapter extends RecyclerView.Adapter<AlarmsAdapter.AlarmViewHolder> {


    @Override
    public AlarmViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(AlarmViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return 0;
    }

    public static class AlarmViewHolder extends RecyclerView.ViewHolder
    {

        public AlarmViewHolder(View itemView) {
            super(itemView);
        }
    }
}
