package ua.com.avatlantik.dubyk.i.dashboardclient.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.formatter.AxisValueFormatter;

import java.util.ArrayList;

import ua.com.avatlantik.dubyk.i.dashboardclient.Constants.ConstantsGlobal;
import ua.com.avatlantik.dubyk.i.dashboardclient.Data.Data_smallGraph;
import ua.com.avatlantik.dubyk.i.dashboardclient.Data.Data_tableDetail_cap;
import ua.com.avatlantik.dubyk.i.dashboardclient.R;
import ua.com.avatlantik.dubyk.i.dashboardclient.adapter.TableDetailCapAdapter;

/**
 * Created by i.dubyk on 13.09.2016.
 */
public class FragmentSmallGraph extends Fragment {
    private static final int LAYOUT = R.layout.fragment_graph;
    private View view;
    private CombinedChart mChart;
    private ArrayList<String> xAxisList;
    private ArrayList<Data_tableDetail_cap> listCap = new ArrayList<>();
    private ArrayList<Data_smallGraph> dataList;
    private String type_data;


    public static FragmentSmallGraph getInstance() {

        Bundle args = new Bundle();
        FragmentSmallGraph fragment = new FragmentSmallGraph();
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

        setCombineGraphIntroTheView();

        return view;
    }


    private void initCap() {
        Button buttonBack = (Button)view.findViewById(R.id.button_graph_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        ListView listView = (ListView)view.findViewById(R.id.table_detail_cap_listView);
        TableDetailCapAdapter adapter = new TableDetailCapAdapter(getActivity(), listCap);
        listView.setAdapter(adapter);
    }

    public void setListCap(ArrayList<Data_tableDetail_cap> listCap) {
        this.listCap = listCap;
    }

    public void setType_data(String type_data) {
        this.type_data = type_data;
    }

    public void setDataList(ArrayList<Data_smallGraph> dataList) {
        this.dataList = dataList;
    }

    private void setCombineGraphIntroTheView(){

        mChart = (CombinedChart) view.findViewById(R.id.chart);
        mChart.setDescription("");
        mChart.setBackgroundColor(getResources().getColor(R.color.colorBlueWhite));
        mChart.setDrawGridBackground(false);
        mChart.setDrawBarShadow(false);
        mChart.setHighlightFullBarEnabled(false);

        mChart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR
        });

        mChart.animateY(ConstantsGlobal.MAX_TIME);

        Legend l = mChart.getLegend();
        l.setWordWrapEnabled(true);
        l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        l.setTextSize(getResources().getDimension(R.dimen.graph_legend_textsize));

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawAxisLine(true);
        rightAxis.setAxisMinValue(getResources().getDimension(R.dimen.zero_size)); // this replaces setStartAtZero(true)
        rightAxis.setEnabled(false);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawGridLines(true);
        leftAxis.setAxisMinValue(getResources().getDimension(R.dimen.zero_size)); // this replaces setStartAtZero(true)
        leftAxis.setTextSize(getResources().getDimension(R.dimen.axis_y_textSize));


        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinValue(getResources().getDimension(R.dimen.zero_size));
        xAxis.setTextSize(getResources().getDimension(R.dimen.axis_x_textSize));
        xAxis.setDrawGridLines(false);
        xAxisList = new ArrayList<>();
        xAxisList.add("0");

        xAxis.setLabelCount(xAxisList.size()-1);
        xAxis.setValueFormatter(new AxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xAxisList.get((int) value % xAxisList.size());
            }

            @Override
            public int getDecimalDigits() {
                return 0;
            }

        });

        CombinedData data = new CombinedData();


        data.setData(generateBarData());

        xAxis.setAxisMaxValue(data.getXMax() + getResources().getDimension(R.dimen.additions_to_max_sizeLine));

        mChart.setData(data);
        mChart.invalidate();
    }


    private BarData generateBarData() {

        ArrayList<BarEntry> entries1 = new ArrayList<BarEntry>();

        for (Data_smallGraph data: dataList)
            if(data.getType().equals(ConstantsGlobal.FACT)) {
                entries1.add(new BarEntry(data.getxAxic(), (float)data.getValue()));
            }

        BarDataSet set1 = new BarDataSet(entries1, getString(R.string.fact_name));
        set1.setColor(Color.RED);
        set1.setValueTextColor(Color.RED);
        set1.setValueTextSize(getResources().getDimension(R.dimen.dot_valueTextSize));
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);

        float barWidth = getResources().getDimension(R.dimen.barWidth_middle); // x2 dataset

        BarData d = new BarData(set1);//, set2);
        d.setBarWidth(barWidth);

        return d;

//        ArrayList<BarEntry> entries1 = new ArrayList<BarEntry>();
//        ArrayList<BarEntry> entries2 = new ArrayList<BarEntry>();
//
//        for (int index = 0; index < itemcount; index++) {
//            entries1.add(new BarEntry(0, getRandom(25, 25)));
//
//            // stacked
//            entries2.add(new BarEntry(0, new float[]{getRandom(13, 12), getRandom(13, 12)}));
//        }
//
//
//        BarDataSet set2 = new BarDataSet(entries2, "");
//        set2.setStackLabels(new String[]{"Stack 1", "Stack 2"});
//        set2.setColors(new int[]{Color.rgb(61, 165, 255), Color.rgb(23, 197, 255)});
//        set2.setValueTextColor(Color.rgb(61, 165, 255));
//        set2.setValueTextSize(10f);
//        set2.setAxisDependency(YAxis.AxisDependency.LEFT);
//
//        float groupSpace = 0.06f;
//        float barSpace = 0.02f; // x2 dataset
//        float barWidth = 0.45f; // x2 dataset
//        // (0.45 + 0.02) * 2 + 0.06 = 1.00 -> interval per "group"
//
//        BarData d = new BarData(set1, set2);
//        d.setBarWidth(barWidth);
//
//        // make this BarData object grouped
//        d.groupBars(0, groupSpace, barSpace); // start at x = 0
//
//        return d;
    }

}