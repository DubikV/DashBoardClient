package ua.com.avatlantik.dubyk.i.dashboardclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ua.com.avatlantik.dubyk.i.dashboardclient.Data.Data_tableDetail_cap;
import ua.com.avatlantik.dubyk.i.dashboardclient.R;

/**
 * Created by i.dubyk on 30.08.2016.
 */
public class TableCapAdapter extends BaseAdapter {

    private List<Data_tableDetail_cap> list;
    private LayoutInflater layoutInflater;

    public TableCapAdapter(Context context, List<Data_tableDetail_cap> list) {
        this.list = list;
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.fragment_item_cap, parent, false);
        }
        TextView tablecap_item_text_left = (TextView) view.findViewById(R.id.tablecap_item_text_left);
        tablecap_item_text_left.setText((String) getItem(position));

        TextView tablecap_item_text_right = (TextView) view.findViewById(R.id.tablecap_item_text_right);
        tablecap_item_text_right.setText((String) getItem(position));
        return view;
    }
}
