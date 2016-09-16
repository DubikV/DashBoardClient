package ua.com.avatlantik.dubyk.i.dashboardclient.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import ua.com.avatlantik.dubyk.i.dashboardclient.Constants.ConstantsGlobal;
import ua.com.avatlantik.dubyk.i.dashboardclient.Data.Data_table;
import ua.com.avatlantik.dubyk.i.dashboardclient.Data.Data_tableDetail_cap;
import ua.com.avatlantik.dubyk.i.dashboardclient.Database.DBHelper;
import ua.com.avatlantik.dubyk.i.dashboardclient.R;
import ua.com.avatlantik.dubyk.i.dashboardclient.adapter.TableDataAdapter;
import ua.com.avatlantik.dubyk.i.dashboardclient.adapter.TableDetailCapAdapter;

/**
 * Created by i.dubyk on 13.09.2016.
 */
public class FragmentTableBranch extends Fragment {
    private static final int LAYOUT = R.layout.fragment_table;
    private View view;
    private FragmentManager mFragmentManager;
    private ArrayList<Data_tableDetail_cap> listCap = new ArrayList<>();
    private ArrayList<Data_table> listData = new ArrayList<>();
    private String parameter_bn, parameter_period, typeData;
    private Button button_table_data, button_table_back;


    public static FragmentTableBranch getInstance() {

        Bundle args = new Bundle();
        FragmentTableBranch fragment = new FragmentTableBranch();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);

        mFragmentManager = getActivity().getSupportFragmentManager();

        initCap();

        initElementsForm();

        initData();

        return view;
    }


    private void initElementsForm() {

        button_table_back = (Button)view.findViewById(R.id.button_table_back);
        button_table_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });



    }

    private void initCap() {

        ListView listView = (ListView)view.findViewById(R.id.table_detail_cap_listView);
        TableDetailCapAdapter adapter = new TableDetailCapAdapter(getActivity(), listCap);
        listView.setAdapter(adapter);

        listData.add(new Data_table("Депропетровская филия", 23.9,4324,234));
        listData.add(new Data_table("Киевская филия", 4543,987,123));
        listData.add(new Data_table("Николаевская филия", 98,234,23));

        ListView table_data_listView = (ListView)view.findViewById(R.id.table_data_listView);
        TableDataAdapter adapterData = new TableDataAdapter(getActivity(), listData);
        table_data_listView.setAdapter(adapterData);

        table_data_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
            }
        });


    }

    private void initData() {

        DBHelper dbHelper = new DBHelper(getActivity());

        Cursor res = dbHelper.getDataWithSelection("*",
                ""+ConstantsGlobal.TABLE_COLUMN_TYPE_DATA+" = '"+ConstantsGlobal.TYPE_DATA_BN_DATA_RES+"'");

        while(res.isAfterLast() == false){
            //list.add(res.getString(res.getColumnIndex(ConstantsGlobal.TABLE_COLUMN_BN_NAME)));
            res.moveToNext();
        }

    }


    public void setListCap(ArrayList<Data_tableDetail_cap> listCap) {
        this.listCap = listCap;
    }
}