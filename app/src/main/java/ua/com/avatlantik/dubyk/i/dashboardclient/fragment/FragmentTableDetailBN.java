package ua.com.avatlantik.dubyk.i.dashboardclient.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import ua.com.avatlantik.dubyk.i.dashboardclient.Constants.ConstantsGlobal;
import ua.com.avatlantik.dubyk.i.dashboardclient.Data.Data_tableDetail_cap;
import ua.com.avatlantik.dubyk.i.dashboardclient.Database.DBHelper;
import ua.com.avatlantik.dubyk.i.dashboardclient.R;
import ua.com.avatlantik.dubyk.i.dashboardclient.adapter.TableDetailCapAdapter;

/**
 * Created by i.dubyk on 13.09.2016.
 */
public class FragmentTableDetailBN extends Fragment {
    private static final int LAYOUT = R.layout.fragment_table_detal;
    private View view;
    private FragmentManager mFragmentManager;
    private ArrayList<Data_tableDetail_cap> listCap = new ArrayList<>();
    private String parameter_bn, parameter_period, typeData;
    private Button button_data, button_s_table_datail;


    public static FragmentTableDetailBN getInstance() {

        Bundle args = new Bundle();
        FragmentTableDetailBN fragment = new FragmentTableDetailBN();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);

        mFragmentManager = getActivity().getSupportFragmentManager();

        initElementsForm();

        initCap();

        initData();

        return view;
    }


    private void initElementsForm() {
        button_data = (Button)view.findViewById(R.id.button_data);
        button_data.setText(getString(R.string.brunch_name_bunch));
        button_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });

        button_s_table_datail = (Button)view.findViewById(R.id.button_s_table_datail);
        button_s_table_datail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentSmallGraph fragmentSmallGraph = new FragmentSmallGraph();
                FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                xfragmentTransaction.replace(R.id.containerView, fragmentSmallGraph);
                xfragmentTransaction.addToBackStack(this.getClass().getName());
                xfragmentTransaction.commit();

            }
        });


    }

    private void initCap() {

        ArrayList<Data_tableDetail_cap> listCap = new ArrayList<>();

        if(parameter_bn!=null){
            listCap.add(new Data_tableDetail_cap("Бізнес напрямок : ", parameter_bn));
        }

        if(parameter_period!=null){
            listCap.add(new Data_tableDetail_cap("Період : ", parameter_period));
        }


        ListView listView = (ListView)view.findViewById(R.id.table_detail_cap_listView);
        TableDetailCapAdapter adapter = new TableDetailCapAdapter(getActivity(), listCap);
        listView.setAdapter(adapter);
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


    public void setParameters(String parameter_bn, String parameter_period, String typeData) {
        this.parameter_bn = parameter_bn;
        this.parameter_period = parameter_period;
        this.typeData = typeData;
    }
}