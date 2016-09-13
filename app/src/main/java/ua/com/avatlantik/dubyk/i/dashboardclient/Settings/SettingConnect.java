package ua.com.avatlantik.dubyk.i.dashboardclient.Settings;

/**
 * Created by i.dubyk on 07.07.2016.
 */
public class SettingConnect {

    private static volatile SettingConnect _instance = null;
    private String adressServer;

    private SettingConnect() {}

    public static SettingConnect getInstance() {

        if (_instance == null)
            synchronized (SettingConnect.class) {
                if (_instance == null)
                    _instance = new SettingConnect();
            }
        return _instance;

    }

    public String getAdressServer() {
        return adressServer;
    }

    public void setAdressServer(String adressServer) {
        this.adressServer = adressServer;
    }

    public String inquiryGetData(){
        if(adressServer == ""){
            return "";
        }
        return adressServer + "/getData";
    }
}
