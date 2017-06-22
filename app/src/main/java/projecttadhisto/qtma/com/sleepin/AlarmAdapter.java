package projecttadhisto.qtma.com.sleepin;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AlarmAdapter extends BaseAdapter {

    private ArrayList<String> alarms = new ArrayList<>();
    private Context context;

    public AlarmAdapter(ArrayList<String> alarms, Context context) {
        this.alarms = alarms;
        this.context = context;
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
    public View getView(int pos, View view, ViewGroup viewGroup) {
        View v = view;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_item_alarm, null);
        }

        // Handle TextView and display string from list
        TextView tvAlarmItem = (TextView) v.findViewById(R.id.list_item_alarm_text);
        tvAlarmItem.setText(alarms.get(pos));
        return v;
    }
}
