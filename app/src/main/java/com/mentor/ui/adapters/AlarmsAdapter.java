package com.mentor.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mentor.R;
import com.mentor.ui.viewmodels.WakieItem;
import com.mentor.util.Spanny;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import uk.co.chrisjenx.calligraphy.CalligraphyTypefaceSpan;
import uk.co.chrisjenx.calligraphy.TypefaceUtils;

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
        WakieItem wakieItem = wakieItems.get(position);

        holder.date.setText(wakieItem.getDate().toString("EEEE, MMMM d, yyyy").toUpperCase());

        CalligraphyTypefaceSpan regularSpan = new CalligraphyTypefaceSpan(TypefaceUtils.load(context.getAssets(), "Roboto-Light.ttf"));
        CalligraphyTypefaceSpan lightSpan = new CalligraphyTypefaceSpan(TypefaceUtils.load(context.getAssets(), "Roboto-Thin.ttf"));

        Spanny alarmText = new Spanny()
                .append(wakieItem.getTime().toString("H"), regularSpan)
                .append(wakieItem.getTime().toString(":mm"), lightSpan);

        holder.time.setText(alarmText);
        holder.period.setText(wakieItem.getTime().toString("AA"));

        holder.name.setText(wakieItem.getMentorName());
        Picasso.with(context).load(wakieItem.getMentorPicUrl()).into(holder.picture);
    }


    @Override
    public int getItemCount() {
        return wakieItems.size();
    }

    public void add(WakieItem item, int position) {
        wakieItems.add(position, item);
        notifyItemInserted(position);
    }

    public void add(WakieItem item) {
        wakieItems.add(item);
        int position=wakieItems.indexOf(wakieItems.get(wakieItems.size()-1));
        notifyItemInserted(position);
    }


    public void remove(WakieItem item) {
        int position = wakieItems.indexOf(item);
        wakieItems.remove(position);
        notifyItemRemoved(position);
    }

    public static class AlarmViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.picture)
        CircleImageView picture;
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.period)
        TextView period;
        @Bind(R.id.date)
        TextView date;
        @Bind(R.id.time)
        TextView time;

        public AlarmViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
