package ua.com.avatlantik.dubyk.i.dashboardclient.fragment;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.AxisValueFormatter;

import java.util.ArrayList;

import ua.com.avatlantik.dubyk.i.dashboardclient.Constants.ConstantsGlobal;
import ua.com.avatlantik.dubyk.i.dashboardclient.R;
import ua.com.avatlantik.dubyk.i.dashboardclient.dto.Data.DataAddDTO;
import ua.com.avatlantik.dubyk.i.dashboardclient.dto.Data.DataDTO;
import ua.com.avatlantik.dubyk.i.dashboardclient.dto.DataStoreDTO;

/**
 * Created by i.dubyk on 24.06.2016.
 */
public class MarginFragmentGraph extends Fragment{
    private static  final int LAYOUT = R.layout.fragment_sales_graph;
    private View view;
    private CombinedChart mChart;
    private DataStoreDTO dataStoreDTO;
    private ArrayList<DataDTO> dataDTOs;
    private ArrayList<String> xAxisList;
    private double stocks_ugk, plane_stocks;

    public static MarginFragmentGraph getInstance() {

        Bundle args = new Bundle();
        MarginFragmentGraph fragment = new MarginFragmentGraph();
        fragment.setArguments(args);
        return  fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);

        dataStoreDTO = DataStoreDTO.getInstance();

        dataDTOs = dataStoreDTO.getMarginDTO();

        if (dataDTOs == null){
            //Toast.makeText(getActivity(),getString(R.string.error_no_data),Toast.LENGTH_SHORT).show();
            return view;
        }

        setCombineGraphxAxis();

        startCountAnimation();

        setCombineGraphIntroTheView();

        return view;
    }


    private void startCountAnimation() {

        DataAddDTO dataAddDTO = dataStoreDTO.getStoksAddDTO();

        final Double plane = dataAddDTO.getPlane();
        final Double fact = dataAddDTO.getFact();

        final TextView textView_header_graph = (TextView) view.findViewById(R.id.textView_header_graph);


        final ValueAnimator animatorHeaderGraph2 = new ValueAnimator();
        animatorHeaderGraph2.setObjectValues(0,fact.intValue());
        animatorHeaderGraph2.setDuration(ConstantsGlobal.SMALL_TIME);
        animatorHeaderGraph2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                textView_header_graph.setText("" + getString(R.string.nav_stocks_ua) + "   "+String.valueOf(plane)+"%/"+(int) animation.getAnimatedValue());
            }
        });

        ValueAnimator animatorHeaderGraph1 = new ValueAnimator();
        animatorHeaderGraph1.setObjectValues(0,plane.intValue());
        animatorHeaderGraph1.setDuration(ConstantsGlobal.SMALL_TIME);
        animatorHeaderGraph1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                textView_header_graph.setText("" + getString(R.string.nav_stocks_ua) + "   "+(int) animation.getAnimatedValue()+"%/"+"0");
                if ((int)animation.getAnimatedValue()==plane.intValue()) {
                    animatorHeaderGraph2.start();
                }
            }
        });

        animatorHeaderGraph1.start();

    }

    private void setCombineGraphxAxis() {

        xAxisList = new ArrayList<>();
        xAxisList.add("0");
        for (DataDTO dataDTO : dataDTOs) {
            if (dataDTO.getTypeData().equals(ConstantsGlobal.NAME_PLANE_STOCKS)) {
                xAxisList.add(String.valueOf(dataDTO.getNumberDay()));
                if(plane_stocks==0){
                    plane_stocks = dataDTO.getValye();
                }
            }
            if (dataDTO.getTypeData().equals(ConstantsGlobal.NAME_STOCKS_UGK)&& stocks_ugk==0) {
                stocks_ugk = dataDTO.getValye();
            }
        }
    }


    private void setCombineGraphIntroTheView(){

        mChart = (CombinedChart) view.findViewById(R.id.chart);
        mChart.setDescription("");
        mChart.setBackgroundColor(getResources().getColor(R.color.colorBlueWhite));
        mChart.setDrawGridBackground(false);
        mChart.setDrawBarShadow(false);
        mChart.setHighlightFullBarEnabled(false);

        mChart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.LINE,
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
        data.setData(generateLineData());

        xAxis.setAxisMaxValue(data.getXMax() + getResources().getDimension(R.dimen.additions_to_max_sizeLine));

        mChart.setData(data);
        mChart.invalidate();
    }

    private LineData generateLineData() {

        LineData d = new LineData();

        d.addDataSet(generateLineDataPlaneStocks());
        d.addDataSet(generateLineDataStocksUGK());

        return d;
    }

    private LineDataSet generateLineDataStocksUGK() {

        ArrayList<Entry> entries = new ArrayList<Entry>();

        entries.add(new Entry(0, (float)stocks_ugk));
        for (DataDTO dataDTO: dataDTOs)
            if(dataDTO.getTypeData().equals(ConstantsGlobal.NAME_STOCKS_UGK)) {
                entries.add(new Entry(dataDTO.getNumberDay(), (float)dataDTO.getValye()));
            }

        LineDataSet set = new LineDataSet(entries, getString(R.string.name_stocks_ugk));
        set.setColor(Color.RED);
        set.setLineWidth(getResources().getDimension(R.dimen.graph_lineWidth));
        set.setCircleColor(Color.RED);
        set.setCircleRadius(getResources().getDimension(R.dimen.graph_lineCircleRadius));
        set.setFillColor(Color.RED);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawValues(true);
        set.setValueTextSize(getResources().getDimension(R.dimen.dot_valueTextSize));
        set.setValueTextColor(Color.RED);

        set.setAxisDependency(YAxis.AxisDependency.LEFT);

        return set;
    }

    private LineDataSet generateLineDataPlaneStocks() {

        ArrayList<Entry> entries = new ArrayList<Entry>();

        entries.add(new Entry(0, (float)plane_stocks));
        for (DataDTO dataDTO: dataDTOs) {
            if (dataDTO.getTypeData().equals(ConstantsGlobal.NAME_PLANE_STOCKS)) {
                entries.add(new Entry(dataDTO.getNumberDay()+ getResources().getDimension(R.dimen.addisize_to_graph), (float)dataDTO.getValye()));
            }
        }


        LineDataSet set = new LineDataSet(entries, getString(R.string.name_plane_stocks));
        set.setColor(getResources().getColor(R.color.color_graph_pink));
        set.setDrawValues(false);
        set.setDrawCircles(false);
        set.setLineWidth(getResources().getDimension(R.dimen.graph_minlineWidth));
        set.setDrawFilled(true);
        set.setFillColor(getResources().getColor(R.color.color_graph_pink));
        set.setFillAlpha(100);

        return set;
    }



    private BarData generateBarData() {

        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();

        for (DataDTO dataDTO: dataDTOs)
            if(dataDTO.getTypeData().equals(ConstantsGlobal.NAME_12ZF)) {
                //entries1.add(new BarEntry(dataDTO.getNumberDay(), (float)dataDTO.getValye()));
                entries.add(new BarEntry(dataDTO.getNumberDay(), new float[]{(float)dataDTO.getValye(), (float)dataDTO.getValye()}));
            }

//        BarDataSet set1 = new BarDataSet(entries1, getString(R.string.fact_name));
//        set1.setColor(Color.RED);
//        set1.setValueTextColor(Color.RED);
//        set1.setValueTextSize(getResources().getDimension(R.dimen.dot_valueTextSize));
//        set1.setAxisDependency(YAxis.AxisDependency.LEFT);

        BarDataSet set = new BarDataSet(entries, "");
        set.setStackLabels(new String[]{"Stack 1", "Stack 2"});
        set.setColors(new int[]{Color.rgb(61, 165, 255), Color.rgb(23, 197, 255)});
        set.setValueTextColor(Color.rgb(61, 165, 255));
        set.setValueTextSize(10f);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);

        float barWidth = getResources().getDimension(R.dimen.barWidth_middle); // x2 dataset

        BarData d = new BarData(set);//, set2);
        d.setBarWidth(barWidth);

        return d;
    }

}
