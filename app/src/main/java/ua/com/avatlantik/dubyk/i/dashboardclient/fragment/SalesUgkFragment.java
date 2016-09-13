package ua.com.avatlantik.dubyk.i.dashboardclient.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ua.com.avatlantik.dubyk.i.dashboardclient.Constants.ConstantsGlobal;
import ua.com.avatlantik.dubyk.i.dashboardclient.R;
import ua.com.avatlantik.dubyk.i.dashboardclient.dto.Data.DataTableDTO;
import ua.com.avatlantik.dubyk.i.dashboardclient.dto.DataStoreDTO;

/**
 * Created by i.dubyk on 24.06.2016.
 */
public class SalesUgkFragment  extends Fragment{
    private static  final int LAYOUT = R.layout.fragment_sales_ugk;
    private View view;
    private DataStoreDTO dataStoreDTO;
    private ArrayList<DataTableDTO> dataTableDTOs;

    public static SalesUgkFragment getInstance() {

        Bundle args = new Bundle();
        SalesUgkFragment fragment = new SalesUgkFragment();
        fragment.setArguments(args);
        return  fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);

        dataStoreDTO = DataStoreDTO.getInstance();

        dataTableDTOs = dataStoreDTO.getSalesUGKTableDTO();

        if (dataTableDTOs == null){
            //Toast.makeText(getActivity(),getString(R.string.error_no_data),Toast.LENGTH_SHORT).show();
            return view;
        }

        setData();

        return view;
    }

    private void setData() {

        for (DataTableDTO data: dataTableDTOs){
            if (data.getTypeData().equalsIgnoreCase(ConstantsGlobal.PLANE_12Q)){
                setDataToView(R.id.textView1_1, data.getSumMonth());
                setDataToView(R.id.textView1_2, data.getSumDay());
                setDataToView(R.id.textView1_3, data.getDelta12());
                setDataToView(R.id.textView1_4, data.getDelta3());
                setDataToView(R.id.textView1_5, data.getDelta1());
            }
            if (data.getTypeData().equalsIgnoreCase(ConstantsGlobal.PLANE_3Q)){
                setDataToView(R.id.textView2_1, data.getSumMonth());
                setDataToView(R.id.textView2_2, data.getSumDay());
                setDataToView(R.id.textView2_3, data.getDelta12());
                setDataToView(R.id.textView2_4, data.getDelta3());
                setDataToView(R.id.textView2_5, data.getDelta1());
            }
            if (data.getTypeData().equalsIgnoreCase(ConstantsGlobal.PLANE_1Q)){
                setDataToView(R.id.textView3_1, data.getSumMonth());
                setDataToView(R.id.textView3_2, data.getSumDay());
                setDataToView(R.id.textView3_3, data.getDelta12());
                setDataToView(R.id.textView3_4, data.getDelta3());
                setDataToView(R.id.textView3_5, data.getDelta1());
            }
            if (data.getTypeData().equalsIgnoreCase(ConstantsGlobal.NORM)){
                setDataToView(R.id.textView4_1, data.getSumMonth());
                setDataToView(R.id.textView4_2, data.getSumDay());
                setDataToView(R.id.textView4_3, data.getDelta12());
                setDataToView(R.id.textView4_4, data.getDelta3());
                setDataToView(R.id.textView4_5, data.getDelta1());
            }
            if (data.getTypeData().equalsIgnoreCase(ConstantsGlobal.FACT)){
                setDataToView(R.id.textView5_1, data.getSumMonth());
                setDataToView(R.id.textView5_2, data.getSumDay());
                setDataToView(R.id.textView5_3, data.getDelta12());
                setDataToView(R.id.textView5_4, data.getDelta3());
                setDataToView(R.id.textView5_5, data.getDelta1());
            }
            if (data.getTypeData().equalsIgnoreCase(ConstantsGlobal.QC)){
                setDataToView(R.id.textView6_1, data.getSumMonth());
                setDataToView(R.id.textView6_2, data.getSumDay());
                setDataToView(R.id.textView6_3, data.getDelta12());
                setDataToView(R.id.textView6_4, data.getDelta3());
                setDataToView(R.id.textView6_5, data.getDelta1());
            }
            if (data.getTypeData().equalsIgnoreCase(ConstantsGlobal.AZ)){
                setDataToView(R.id.textView7_1, data.getSumMonth());
                setDataToView(R.id.textView7_2, data.getSumDay());
                setDataToView(R.id.textView7_3, data.getDelta12());
                setDataToView(R.id.textView7_4, data.getDelta3());
                setDataToView(R.id.textView7_5, data.getDelta1());
            }
        }

    }


    private void setDataToView(int tviewid, double data) {
        TextView tview = (TextView) view.findViewById(tviewid);
        if(data == 0){
            tview.setText("");
        }else{
            tview.setText(""+data);
        }


    }


}
