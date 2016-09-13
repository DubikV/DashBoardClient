package ua.com.avatlantik.dubyk.i.dashboardclient.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ua.com.avatlantik.dubyk.i.dashboardclient.Constants.ConstantsGlobal;
import ua.com.avatlantik.dubyk.i.dashboardclient.fragment.MarginFragment;
import ua.com.avatlantik.dubyk.i.dashboardclient.fragment.MarginFragmentGraph;
import ua.com.avatlantik.dubyk.i.dashboardclient.fragment.MoneyFragment;
import ua.com.avatlantik.dubyk.i.dashboardclient.fragment.MoneyFragmentGraph;
import ua.com.avatlantik.dubyk.i.dashboardclient.fragment.SalesUgkFragment;
import ua.com.avatlantik.dubyk.i.dashboardclient.fragment.SalesUgkFragmentGraph;
import ua.com.avatlantik.dubyk.i.dashboardclient.fragment.StocksFragment;
import ua.com.avatlantik.dubyk.i.dashboardclient.fragment.StocksFragmentGraph;

/**
 * Created by i.dubyk on 24.06.2016.
 */
public class TabPageFragmentAdapter extends FragmentPagerAdapter {

    private String[] tabs;
    private static String nameData;

    public TabPageFragmentAdapter(FragmentManager fm, String nameData) {
        super(fm);
        this.nameData = nameData;

        tabs = new String[] {
            "Графік",
            "Дані"
        };
    }

    @Override
        public CharSequence getPageTitle(int position) {
           return tabs[position];
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                if(nameData.equals(ConstantsGlobal.SALES_GET_NAME)){
                    return SalesUgkFragmentGraph.getInstance();
                }else if(nameData.equals(ConstantsGlobal.SALESMONEY_GET_NAME)){
                    return MoneyFragmentGraph.getInstance();
                }else if(nameData.equals(ConstantsGlobal.STOCKS_GET_NAME)){
                    return StocksFragmentGraph.getInstance();
                }else if(nameData.equals(ConstantsGlobal.MARGIN_GET_NAME)){
                    return MarginFragmentGraph.getInstance();
                }else {
                    return SalesUgkFragmentGraph.getInstance();
                }
            case 1:
                if(nameData.equals(ConstantsGlobal.SALES_GET_NAME)){
                    return SalesUgkFragment.getInstance();
                }else if(nameData.equals(ConstantsGlobal.SALESMONEY_GET_NAME)){
                    return MoneyFragment.getInstance();
                }else if(nameData.equals(ConstantsGlobal.STOCKS_GET_NAME)){
                    return StocksFragment.getInstance();
                }else if(nameData.equals(ConstantsGlobal.MARGIN_GET_NAME)){
                    return MarginFragment.getInstance();
                }else {
                    return SalesUgkFragmentGraph.getInstance();
                }
            }
            return null;
    }

    @Override
    public int getCount() {
        return tabs.length;
    }



}


