package ua.com.avatlantik.dubyk.i.dashboardclient.Modules;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import ua.com.avatlantik.dubyk.i.dashboardclient.Constants.ConstantsGlobal;
import ua.com.avatlantik.dubyk.i.dashboardclient.Database.DBHelper;
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

    public String getGetURL(String dataName, String bn, String brunch, String manager, String period){

        String textURL;

        if(settingsUser.getUserLogin()==null || settingsUser.getUserLogin().isEmpty()){
            mainActivity.setToastToActivity((mainActivity.getString(R.string.error_login_null)));
            return "";
        }

        if(settingConnect.getAdressServer()==null || settingConnect.getAdressServer().isEmpty()){
            mainActivity.setToastToActivity((mainActivity.getString(R.string.error_server_null)));
            return "";
        }

        if(dataName==null || dataName.isEmpty()){
            return "";
        }else{
            textURL = "getDataDTO?type="+ dataName;
        }

        if(bn!=null){
           if (!bn.isEmpty()) {
               textURL = textURL + "&" + ConstantsGlobal.TABLE_COLUMN_BN_GUID + "=" +
                       getGuidElementByName(bn, ConstantsGlobal.TABLE_COLUMN_BN_NAME, ConstantsGlobal.TABLE_COLUMN_BN_GUID);
           }
        }
        if(brunch!=null){
            if (!brunch.isEmpty()){
               textURL = textURL + "&" + ConstantsGlobal.TABLE_COLUMN_BRANCH_GUID+"="+
                       getGuidElementByName(brunch,ConstantsGlobal.TABLE_COLUMN_BRANCH_NAME, ConstantsGlobal.TABLE_COLUMN_BRANCH_GUID);
            }
        }

        if(manager!=null){
            if (!manager.isEmpty()){
            textURL = textURL + "&" + ConstantsGlobal.TABLE_COLUMN_MANAGER_GUID+"="+
                    getGuidElementByName(manager,ConstantsGlobal.TABLE_COLUMN_MANAGER_NAME, ConstantsGlobal.TABLE_COLUMN_MANAGER_GUID);
            }
        }

        if(period!=null){
            if (!period.isEmpty()){
               if(period.equals("Січень")){
                   textURL = textURL +"&period=1";
               }else if(period.equals("Лютий")){
                   textURL = textURL +"&period=2";
               }else if(period.equals("Березень")){
                   textURL = textURL +"&period=3";
               }else if(period.equals("Квітень")){
                   textURL = textURL +"&period=4";
               }else if(period.equals("Травень")){
                   textURL = textURL +"&period=5";
               }else if(period.equals("Червень")){
                   textURL = textURL +"&period=6";
               }else if(period.equals("Липень")){
                   textURL = textURL +"&period=7";
               }else if(period.equals("Серпень")){
                   textURL = textURL +"&period=8";
               }else if(period.equals("Вересень")){
                   textURL = textURL +"&period=9";
               }else if(period.equals("Жовтень")){
                   textURL = textURL +"&period=10";
               }else if(period.equals("Листопад")){
                   textURL = textURL +"&period=11";
               }else if(period.equals("Грудень")){
                   textURL = textURL +"&period=12";
               }
            }
        }

        return "http://" + settingConnect.getAdressServer() + ConstantsGlobal.HTTPSERVICE_1C_NAME + "/" + textURL;

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

    private String getGuidElementByName(String name,String columName, String columGuid){
        DBHelper dbHelper = new DBHelper(mainActivity);

        Cursor res = dbHelper.getDataWithSelection(columGuid,
                ""+columName+" = '"+name+"'");

        while(res.isAfterLast() == false){
            return res.getString(res.getColumnIndex(columGuid));
        }
        return "";
    }

}
