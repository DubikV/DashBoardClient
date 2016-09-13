package ua.com.avatlantik.dubyk.i.dashboardclient.Modules;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import ua.com.avatlantik.dubyk.i.dashboardclient.Constants.ConstantsGlobal;
import ua.com.avatlantik.dubyk.i.dashboardclient.MainActivity;
import ua.com.avatlantik.dubyk.i.dashboardclient.R;
import ua.com.avatlantik.dubyk.i.dashboardclient.Settings.SettingConnect;
import ua.com.avatlantik.dubyk.i.dashboardclient.Settings.SettingsUser;


public class Module_GetURL {
    private MainActivity mainActivity;
    private SettingsUser settingsUser;
    private SettingConnect settingConnect;


    public Module_GetURL() {
        settingsUser = SettingsUser.getInstance();
        settingConnect = SettingConnect.getInstance();
    }

    public Module_GetURL(MainActivity mainActivity) {
        settingsUser = SettingsUser.getInstance();
        settingConnect = SettingConnect.getInstance();
        this.mainActivity = mainActivity;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public String getGetURL(String dataName){

        String textURL;

        if(settingsUser.getUserLogin()==null || settingsUser.getUserLogin().isEmpty()){
            mainActivity.setToastToActivity((mainActivity.getString(R.string.error_login_null)));
            return "";
        }

        if(settingConnect.getAdressServer()==null || settingConnect.getAdressServer().isEmpty()){
            mainActivity.setToastToActivity((mainActivity.getString(R.string.error_server_null)));
            return "";
        }

        return "http://" + settingConnect.getAdressServer() + ConstantsGlobal.HTTPSERVICE_1C_NAME + "/" + dataName;

    }

    public String getusrlogin(){

        String usrLog = settingsUser.getUserLogin();
//        String usrlogin ="";
//        try {
//            usrlogin = URLEncoder.encode(usrLog, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            usrlogin = usrLog;
//        }
        return usrLog;
    }

    public String getusrPassword(){

        String usrPas = settingsUser.getUserPassword();
//        String usrPasw ="";
//        try {
//            usrPasw = URLEncoder.encode(usrPas, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            usrPasw = usrPas;
//        }
        return usrPas;
    }

    public boolean getCheckConnektion() {

       if (getCheckEnternet()) {



           //try {
//                URL url = new URL(urlString);
//
//                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//                urlConnection.setRequestMethod("GET");
//                urlConnection.setUseCaches(false);
//                urlConnection.setConnectTimeout(1000);
//                urlConnection.connect();
//                int status = urlConnection.getResponseCode();
//
//                switch (status) {
//                    case 200:
//                    case 201:
                       return true;
//                }
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//                mainActivity.setToastToActivity(mainActivity.getString(R.string.error_connection_server));
//                return false;
//            } catch (IOException e) {
//                e.printStackTrace();
//                mainActivity.setToastToActivity(mainActivity.getString(R.string.error_connection_server));
//                return false;
//            }
//            mainActivity.setToastToActivity(mainActivity.getString(R.string.error_connection_server));
//            return false;
        }
      return false;
    }

    public boolean getCheckEnternet() {

            final ConnectivityManager conMgr = (ConnectivityManager)mainActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
            final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();

              if (activeNetwork != null && activeNetwork.isConnected()) {
                  return true;
              } else {
                  mainActivity.setToastToActivity(mainActivity.getString(R.string.error_internet_connecting));
                 return false;
              }
    }
}
