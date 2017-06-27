package com.qhackers.sci18.sleepin;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Adapter class to render data in the ListView on the AlarmActivity (Home) page
 */
public class AlarmAdapter extends ArrayAdapter {

    // Class to hold the different views in each list item
    private static class ViewHolder {
        TextView tvAlarmTime;
        Switch sIsSet;
    }

    private ViewHolder holder;
    private Context context;
    private final int layoutResourceId;
    private ArrayList<Alarm> alarms;

    public AlarmAdapter(Context context, int layoutResourceId, ArrayList<Alarm> alarms) {
        super(context, layoutResourceId, alarms);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.alarms = alarms;
    }

    @Override
    public int getCount() {
        return alarms.size();
    }

    @Override
    public Object getItem(int pos) {
        return alarms.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        // return alarms.get(pos).getId();
        // return 0 if list items do not have id attribute
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        holder = null;

        if (row == null) {
            LayoutInflater inflater;
            inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ViewHolder();
            holder.tvAlarmTime = (TextView) row.findViewById(R.id.list_item_alarm_text);
            holder.sIsSet = (Switch) row.findViewById(R.id.list_item_is_set);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        Alarm alarm = alarms.get(position);
        holder.tvAlarmTime.setText(alarm.getAlarmTime());
        holder.sIsSet.setChecked(alarm.getIsSet());

        holder.tvAlarmTime.setTag(position);
        holder.sIsSet.setTag(position);

        holder.sIsSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO finish this
                int pos = (Integer) view.getTag();
            }
        });

        return row;
    }
}
