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
public class MoneyFragmentGraph extends Fragment{
    private static  final int LAYOUT = R.layout.fragment_sales_graph;
    private View view;
    private CombinedChart mChart;
    private DataStoreDTO dataStoreDTO;
    private ArrayList<DataDTO> dataDTOs;
    private ArrayList<String> xAxisList;
    private double plane_12Q, plane_3Q, plane_1Q;
    private int color_plane_12Q, color_plane_3Q, color_plane_1Q;
    private String first_line, second_line, third_line;

    public static MoneyFragmentGraph getInstance() {

        Bundle args = new Bundle();
        MoneyFragmentGraph fragment = new MoneyFragmentGraph();
        fragment.setArguments(args);
        return  fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);

        dataStoreDTO = DataStoreDTO.getInstance();

        dataDTOs = dataStoreDTO.getMoneyDTO();

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

        DataAddDTO dataAddDTO = dataStoreDTO.getMoneyAddDTO();

        final Double planeNorm = dataAddDTO.getPlaneNorm();
        final Double plane = dataAddDTO.getPlane();
        final Double fact = dataAddDTO.getFact();
        final Double factNorm = dataAddDTO.getFactNorm();

        final TextView textView_header_graph = (TextView) view.findViewById(R.id.textView_header_graph);

        final ValueAnimator animatorHeaderGraph4 = new ValueAnimator();
        animatorHeaderGraph4.setObjectValues(0,factNorm.intValue());
        animatorHeaderGraph4.setDuration(ConstantsGlobal.SMALL_TIME);
        animatorHeaderGraph4.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                textView_header_graph.setText("" + getString(R.string.nav_salesMoney_ua) + "   "+String.valueOf(plane)+"%/"+String.valueOf(fact)+"%"+"   "+String.valueOf(planeNorm)+"/"+(int) animation.getAnimatedValue());

            }
        });

        final ValueAnimator animatorHeaderGraph3 = new ValueAnimator();
        animatorHeaderGraph3.setObjectValues(0,planeNorm.intValue());
        animatorHeaderGraph3.setDuration(ConstantsGlobal.SMALL_TIME);
        animatorHeaderGraph3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                textView_header_graph.setText("" + getString(R.string.nav_salesMoney_ua) + "   "+String.valueOf(plane)+"%/"+String.valueOf(fact)+"%"+"   "+(int) animation.getAnimatedValue()+"/0");
                if ((int)animation.getAnimatedValue()==planeNorm.intValue()) {
                    animatorHeaderGraph4.start();
                }
            }
        });

        final ValueAnimator animatorHeaderGraph2 = new ValueAnimator();
        animatorHeaderGraph2.setObjectValues(0,fact.intValue());
        animatorHeaderGraph2.setDuration(ConstantsGlobal.SMALL_TIME);
        animatorHeaderGraph2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                textView_header_graph.setText("" + getString(R.string.nav_salesMoney_ua) + "   "+String.valueOf(plane)+"%/"+(int) animation.getAnimatedValue()+"%"+"   "+"0/0");
                if ((int)animation.getAnimatedValue()==fact.intValue()) {
                    animatorHeaderGraph3.start();
                }
            }
        });

        ValueAnimator animatorHeaderGraph1 = new ValueAnimator();
        animatorHeaderGraph1.setObjectValues(0,plane.intValue());
        animatorHeaderGraph1.setDuration(ConstantsGlobal.SMALL_TIME);
        animatorHeaderGraph1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                textView_header_graph.setText("" + getString(R.string.nav_salesMoney_ua) + "   "+(int) animation.getAnimatedValue()+"%/"+"0%"+"   "+"0/0");
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
            if (dataDTO.getTypeData().equals(ConstantsGlobal.PLANE_12Q)) {
                xAxisList.add(String.valueOf(dataDTO.getNumberDay()));
                plane_12Q = dataDTO.getValye();
            }
            if (dataDTO.getTypeData().equals(ConstantsGlobal.PLANE_3Q)) {
                plane_3Q = dataDTO.getValye();
            }
            if (dataDTO.getTypeData().equals(ConstantsGlobal.PLANE_1Q)) {
                plane_1Q = dataDTO.getValye();
            }
        }

        double min=0;
        double max=0;

        if ( plane_3Q < plane_12Q ) {min = plane_3Q; max = plane_12Q;
            color_plane_3Q = getResources().getColor(R.color.color_graph_pink);
            third_line = "plane_3Q";
            color_plane_12Q = getResources().getColor(R.color.color_graph_blue);
            first_line = "plane_12Q";}
        else {min = plane_12Q; max = plane_3Q;
            color_plane_12Q = getResources().getColor(R.color.color_graph_pink);
            first_line = "plane_3Q";
            color_plane_3Q = getResources().getColor(R.color.color_graph_blue);
            third_line = "plane_12Q";}
        if ( plane_1Q < min ) {min = plane_1Q;
            color_plane_1Q = getResources().getColor(R.color.color_graph_pink);
            third_line = "plane_1Q";}
        else if  ( plane_1Q > max ) {
            max = plane_1Q;
            color_plane_1Q = getResources().getColor(R.color.color_graph_blue);
            first_line = "plane_1Q";
        }else{
            color_plane_1Q = getResources().getColor(R.color.color_graph_yellow);
            second_line = "plane_1Q";

        }

        if ( plane_12Q < max && plane_12Q > min){color_plane_12Q = getResources().getColor(R.color.color_graph_yellow);
            second_line = "plane_12Q";}
        if ( plane_3Q<max && plane_3Q>min) {color_plane_3Q = getResources().getColor(R.color.color_graph_yellow);
            second_line = "plane_3Q";}
        if ( plane_1Q<max && plane_1Q>min) {color_plane_1Q = getResources().getColor(R.color.color_graph_yellow);
            second_line = "plane_1Q";}



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

        if(first_line.equals("plane_12Q")){
            d.addDataSet(generateLineData12Q());
        }else if(first_line.equals("plane_3Q")){
            d.addDataSet(generateLineData3Q());
        }else if(first_line.equals("plane_1Q")){
            d.addDataSet(generateLineData1Q());
        }

        if(second_line.equals("plane_12Q")){
            d.addDataSet(generateLineData12Q());
        }else if(second_line.equals("plane_3Q")){
            d.addDataSet(generateLineData3Q());
        }else if(second_line.equals("plane_1Q")){
            d.addDataSet(generateLineData1Q());
        }

        if(third_line.equals("plane_12Q")){
            d.addDataSet(generateLineData12Q());
        }else if(third_line.equals("plane_3Q")){
            d.addDataSet(generateLineData3Q());
        }else if(third_line.equals("plane_1Q")){
            d.addDataSet(generateLineData1Q());
        }


        d.addDataSet(generateLineDataNorm());

        return d;
    }

    private LineDataSet generateLineDataNorm() {

        ArrayList<Entry> entries = new ArrayList<Entry>();

        entries.add(new Entry(0, 0));
        for (DataDTO dataDTO: dataDTOs)
            if(dataDTO.getTypeData().equals(ConstantsGlobal.NORM)) {
                entries.add(new Entry(dataDTO.getNumberDay(), (float)dataDTO.getValye()));
            }

        LineDataSet set = new LineDataSet(entries, getString(R.string.norm_name));
        set.setColor(Color.BLUE);
        set.setLineWidth(getResources().getDimension(R.dimen.graph_lineWidth));
        set.setCircleColor(Color.BLUE);
        set.setCircleRadius(getResources().getDimension(R.dimen.graph_lineCircleRadius));
        set.setFillColor(Color.BLUE);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawValues(true);
        set.setValueTextSize(getResources().getDimension(R.dimen.dot_valueTextSize));
        set.setValueTextColor(Color.BLUE);

        set.setAxisDependency(YAxis.AxisDependency.LEFT);

        return set;
    }

    private LineDataSet generateLineData12Q() {

        ArrayList<Entry> entries = new ArrayList<Entry>();

        entries.add(new Entry(0, (float)plane_12Q));
        for (DataDTO dataDTO: dataDTOs) {
            if (dataDTO.getTypeData().equals(ConstantsGlobal.PLANE_12Q)) {
                entries.add(new Entry(dataDTO.getNumberDay()+ getResources().getDimension(R.dimen.addisize_to_graph), (float)dataDTO.getValye()));
            }
        }


        LineDataSet set = new LineDataSet(entries, getString(R.string.plane_12q_name));
        set.setColor(color_plane_12Q);
        set.setDrawValues(false);
        set.setDrawCircles(false);
        set.setLineWidth(getResources().getDimension(R.dimen.graph_minlineWidth));
        set.setDrawFilled(true);
        set.setFillColor(color_plane_12Q);
        set.setFillAlpha(100);

        return set;
    }

    private LineDataSet generateLineData3Q() {

        ArrayList<Entry> entries = new ArrayList<Entry>();

        entries.add(new Entry(0, (float)plane_3Q));
        for (DataDTO dataDTO: dataDTOs)
            if(dataDTO.getTypeData().equals(ConstantsGlobal.PLANE_3Q)) {
                entries.add(new Entry(dataDTO.getNumberDay()+ getResources().getDimension(R.dimen.addisize_to_graph), (float)dataDTO.getValye()));
            }

        LineDataSet set = new LineDataSet(entries, getString(R.string.plane_3q_name));
        set.setColor(color_plane_3Q);
        set.setDrawValues(false);
        set.setDrawCircles(false);
        set.setLineWidth(getResources().getDimension(R.dimen.graph_minlineWidth));
        set.setDrawFilled(true);
        set.setFillColor(color_plane_3Q);
        set.setFillAlpha(100);

        return set;
    }

    private LineDataSet generateLineData1Q() {

        ArrayList<Entry> entries = new ArrayList<Entry>();

        entries.add(new Entry(0, (float) plane_1Q));
        for (DataDTO dataDTO: dataDTOs)
            if(dataDTO.getTypeData().equals(ConstantsGlobal.PLANE_1Q)) {
                entries.add(new Entry(dataDTO.getNumberDay()+ getResources().getDimension(R.dimen.addisize_to_graph), (float)dataDTO.getValye()));
            }

        LineDataSet set = new LineDataSet(entries, getString(R.string.plane_1q_name));
        set.setColor(color_plane_1Q);
        set.setDrawValues(false);
        set.setDrawCircles(false);
        set.setLineWidth(getResources().getDimension(R.dimen.graph_minlineWidth));
        set.setDrawFilled(true);
        set.setFillColor(color_plane_1Q);
        set.setFillAlpha(100);

        return set;
    }



    private BarData generateBarData() {

        ArrayList<BarEntry> entries1 = new ArrayList<BarEntry>();

        for (DataDTO dataDTO: dataDTOs)
            if(dataDTO.getTypeData().equals(ConstantsGlobal.FACT)) {
                entries1.add(new BarEntry(dataDTO.getNumberDay(), (float)dataDTO.getValye()));
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
    }

}
