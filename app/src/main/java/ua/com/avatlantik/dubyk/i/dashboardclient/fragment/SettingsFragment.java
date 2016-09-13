package ua.com.avatlantik.dubyk.i.dashboardclient.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import ua.com.avatlantik.dubyk.i.dashboardclient.MainActivity;
import ua.com.avatlantik.dubyk.i.dashboardclient.Modules.Module_GetURL;
import ua.com.avatlantik.dubyk.i.dashboardclient.Modules.Module_ReadWrite_Data;
import ua.com.avatlantik.dubyk.i.dashboardclient.R;
import ua.com.avatlantik.dubyk.i.dashboardclient.Settings.SettingConnect;
import ua.com.avatlantik.dubyk.i.dashboardclient.Settings.SettingsUser;


public class SettingsFragment extends Fragment{
    private static  final int LAYOUT = R.layout.fragment_settings;
    private View view;
    private Button buttonSave;
    private EditText editT_login, editT_password, editT_server;
    private Switch avtDown_switch;
    private SettingsUser settingsUser;
    private SettingConnect settingConnect;

    public static SettingsFragment getInstance() {

        Bundle args = new Bundle();
        SettingsFragment fragment = new SettingsFragment();
        fragment.setArguments(args);
        return  fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);

        settingsUser = SettingsUser.getInstance();
        settingConnect = SettingConnect.getInstance();

        initElementsForm();

        initeditTexts();

        return view;
    }

    private void initElementsForm() {

        editT_login = (EditText) view.findViewById(R.id.editText_login);
        editT_login.requestFocus();
        editT_password = (EditText) view.findViewById(R.id.editText_password);
        editT_server = (EditText) view.findViewById(R.id.editText_adressServer);
        avtDown_switch = (Switch) view.findViewById(R.id.avtDown_switch);
        buttonSave = (Button) view.findViewById(R.id.btn_enter);

        editT_login.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    editT_login.clearFocus();
                    editT_password.requestFocus();
                    return true;
                }
                return false;
            }
        });

        editT_password.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    editT_password.clearFocus();
                    editT_server.requestFocus();
                    return true;
                }
                return false;
            }
        });

        editT_server.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    editT_server.clearFocus();
                    buttonSave.requestFocus();
                    return true;
                }
                return false;
            }
        });


        buttonSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                saveSettings();
            }
        });
    }

    private void initeditTexts() {
        editT_login.setText(settingsUser.getUserLogin());
        editT_password.setText(settingsUser.getUserPassword());
        editT_server.setText(settingConnect.getAdressServer());
        avtDown_switch.setChecked(settingConnect.isAvtoDownload());
    }


    private void saveSettings() {

        settingsUser.setUserLogin(editT_login.getText().toString());
        settingsUser.setUserPassword(editT_password.getText().toString());
        settingConnect.setAdressServer(editT_server.getText().toString());
        settingConnect.setAvtoDownload(avtDown_switch.isChecked());

        TextView text_nav_heared_login = (TextView) view.getRootView().findViewById(R.id.text_nav_heared_login);;
        text_nav_heared_login.setText(settingsUser.getUserLogin());

        Module_ReadWrite_Data module_readWrite_data = new Module_ReadWrite_Data(getContext());

        module_readWrite_data.saveDataToMemory();

        /* hide keyboard */
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);

        if (!settingConnect.isAvtoDownload()) {

            askToDowloadData();

        }else {
            Toolbar toolbar = (Toolbar) view.getRootView().findViewById(R.id.toolbar);
            toolbar.setTitle(getString(R.string.app_name) + ": " + getString(R.string.nav_salesUgk_ua));

            NavigationView navigationView = (NavigationView) view.getRootView().findViewById(R.id.nav_view);
            navigationView.getMenu().performIdentifierAction(R.id.nav_salesUgk, 0);
        }

    }

    private void askToDowloadData() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getString(R.string.ask_to_dowload_data));

        builder.setPositiveButton(getString(R.string.questions_Exit_answer_yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Module_GetURL module_getURL = new Module_GetURL((MainActivity) getActivity());
                if(module_getURL.getCheckConnektion()) {
                    ((MainActivity) getActivity()).downloadData();
                }

            }
        });

        builder.setNegativeButton(getString(R.string.questions_Exit_answer_no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }



}