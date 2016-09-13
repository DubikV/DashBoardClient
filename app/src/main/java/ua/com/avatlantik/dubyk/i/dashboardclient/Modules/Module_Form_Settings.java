package ua.com.avatlantik.dubyk.i.dashboardclient.Modules;

import android.content.res.Configuration;

import ua.com.avatlantik.dubyk.i.dashboardclient.Constants.ConstantsForms;
import ua.com.avatlantik.dubyk.i.dashboardclient.fragment.SalesUgkFragmentGraph;

public class Module_Form_Settings {

    SalesUgkFragmentGraph activity;

    public Module_Form_Settings(SalesUgkFragmentGraph activity) {
        this.activity = activity;
    }

    public int getSizeHeightView(String nameSize, int heightLayout, int orientation) {

        if (nameSize.equalsIgnoreCase(ConstantsForms.BIG_SIZE_NAME)) {

            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {

                return (int) (heightLayout * ConstantsForms.COEF_BIG_SIZE_1);

            } else {

                return (int) (heightLayout * ConstantsForms.COEF_BIG_SIZE_2);

            }

        } else if (nameSize.equalsIgnoreCase(ConstantsForms.MIDDLE_SIZE_NAME)) {

            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {

                return (int) (heightLayout * ConstantsForms.COEF_MIDDLE_SIZE_1);

            } else {

                return (int) (heightLayout * ConstantsForms.COEF_MIDDLE_SIZE_2);

            }

        } else if (nameSize.equalsIgnoreCase(ConstantsForms.SMALL_SIZE_NAME)) {

            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {

                return (int) (heightLayout * ConstantsForms.COEF_SMALL_SIZE_1);

            } else {

                return (int) (heightLayout * ConstantsForms.COEF_SMALL_SIZE_2);

            }

        }
        return 0;
    }
}
