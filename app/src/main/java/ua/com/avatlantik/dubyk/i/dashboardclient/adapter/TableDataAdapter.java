package ua.com.avatlantik.dubyk.i.dashboardclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import ua.com.avatlantik.dubyk.i.dashboardclient.Data.Data_table;
import ua.com.avatlantik.dubyk.i.dashboardclient.R;

/**
 * Created by i.dubyk on 14.09.2016.
 */
public class TableDataAdapter extends BaseAdapter {

    private ArrayList<Data_table> list;
    private Context context;
    private LayoutInflater layoutInflater;

    public TableDataAdapter(Context context, ArrayList<Data_table> list) {
        this.list = list;
        this.context = context;
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
            view = layoutInflater.inflate(R.layout.fragment_item_table, parent, false);
        }
        Data_table data_table = getData_table(position);

        TextView button_table_item_1_textView = (TextView) view.findViewById(R.id.button_table_item_1_textView);
        button_table_item_1_textView.setText(data_table.getName());

        TextView button_table_item_2_textView = (TextView) view.findViewById(R.id.button_table_item_2_textView);
        button_table_item_2_textView.setText(Double.toString(data_table.getValue1()));

        TextView button_table_item_3_textView = (TextView) view.findViewById(R.id.button_table_item_3_textView);
        button_table_item_3_textView.setText(Double.toString(data_table.getValue2()));

        TextView button_table_item_4_textView = (TextView) view.findViewById(R.id.button_table_item_4_textView);
        button_table_item_4_textView.setText(Double.toString(data_table.getValue3()));

        Button deleteImageView = (Button) view.findViewById(R.id.button_table_item);
        deleteImageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        return view;
    }

    private Data_table getData_table(int position) {
        return (Data_table)getItem(position);
    }

}
