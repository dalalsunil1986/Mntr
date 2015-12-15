package com.mentor.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mentor.R;
import com.mentor.ui.viewmodels.WakieItem;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Joel on 14/12/2015.
 */
public class AlarmsAdapter extends RecyclerView.Adapter<AlarmsAdapter.AlarmViewHolder> {

    private Context context;
    private ArrayList<WakieItem> wakieItems;

    public AlarmsAdapter(Context context, ArrayList<WakieItem> wakieItems) {
        this.wakieItems = wakieItems;
        this.context = context;

    }

    @Override
    public AlarmViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.wakie_row, parent, false);

        return new AlarmViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AlarmViewHolder holder, int position) {
        WakieItem wakieItem=wakieItems.get(position);


    }


    @Override
    public int getItemCount() {
        return wakieItems.size();
    }

    public static class AlarmViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.name)
        TextView name;

        public AlarmViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
