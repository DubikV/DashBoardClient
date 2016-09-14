package ua.com.avatlantik.dubyk.i.dashboardclient.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import ua.com.avatlantik.dubyk.i.dashboardclient.Data.Data_tableDetail_cap;
import ua.com.avatlantik.dubyk.i.dashboardclient.R;
import ua.com.avatlantik.dubyk.i.dashboardclient.adapter.TableCapAdapter;

/**
 * Created by i.dubyk on 13.09.2016.
 */
public class FragmentTableDetail extends Fragment {
    private static final int LAYOUT = R.layout.fragment_table_detal;
    private View view;
    private ArrayList<Data_tableDetail_cap> listCap;


    public static FragmentTableDetail getInstance() {

        Bundle args = new Bundle();
        FragmentTableDetail fragment = new FragmentTableDetail();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);

        if (listCap.size()==0){
            return view;
        }
        initCap();

        return view;
    }

    private void initCap() {
        ListView listView = (ListView)view.findViewById(R.id.table_detail_cap_listView);
        TableCapAdapter adapter = new TableCapAdapter(getActivity(), listCap);
        listView.setAdapter(adapter);
    }

    public void setListCap(ArrayList<Data_tableDetail_cap> listCap) {
        this.listCap = listCap;
    }
}