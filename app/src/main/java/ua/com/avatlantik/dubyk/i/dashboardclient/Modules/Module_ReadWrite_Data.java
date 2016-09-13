package ua.com.avatlantik.dubyk.i.dashboardclient.Modules;

import android.content.Context;
import android.content.SharedPreferences;

import ua.com.avatlantik.dubyk.i.dashboardclient.Settings.SettingConnect;
import ua.com.avatlantik.dubyk.i.dashboardclient.Settings.SettingsUser;

/**
 * Created by i.dubyk on 07.07.2016.
 */
public class Module_ReadWrite_Data {
    private SettingsUser settingsUser;
    private SettingConnect settingConnect;
    private Context context;

    public Module_ReadWrite_Data(Context context) {
        this.context = context;
        settingsUser = SettingsUser.getInstance();
        settingConnect = SettingConnect.getInstance();
    }

    public void readDataFromMemory() {

        SharedPreferences sharedPref = context.getSharedPreferences("SettingsApp", context.MODE_PRIVATE);

        settingConnect.setAdressServer(sharedPref.getString("SettingConnect_adressServer", null));
        settingsUser.setUserLogin(sharedPref.getString("SettingUser_login", null));
        settingsUser.setUserPassword(sharedPref.getString("SettingUser_password", null));

    }

    public void saveDataToMemory() {

        SharedPreferences sharedPref = context.getSharedPreferences("SettingsApp", context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("SettingUser_login", settingsUser.getUserLogin());
        editor.putString("SettingUser_password", settingsUser.getUserPassword());
        editor.putString("SettingConnect_adressServer", settingConnect.getAdressServer());
        editor.commit();

    }

}
