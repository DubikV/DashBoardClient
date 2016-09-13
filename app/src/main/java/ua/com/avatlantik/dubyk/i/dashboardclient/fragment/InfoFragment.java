package ua.com.avatlantik.dubyk.i.dashboardclient.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ua.com.avatlantik.dubyk.i.dashboardclient.R;


public class InfoFragment extends Fragment{
    private static  final int LAYOUT = R.layout.fragment_info;
    private View view;
    private TextView tv;


    public static InfoFragment getInstance() {

        Bundle args = new Bundle();
        InfoFragment fragment = new InfoFragment();
        fragment.setArguments(args);
        return  fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);

        tv = (TextView) view.findViewById(R.id.text_info);

        initTextIfo();

        return view;
    }

    private void initTextIfo() {

        String text = "dev: "+ getString(R.string.dev_info)+"    dev mail:  "+ getString(R.string.dev_info_mail);

        tv.setText(text);

    }


}
