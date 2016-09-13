package ua.com.avatlantik.dubyk.i.dashboardclient.Settings;

/**
 * Created by i.dubyk on 07.07.2016.
 */
public class SettingsUser {

    private static volatile SettingsUser _instance = null;
    private String userLogin;
    private String userPassword;
    private String userRole;

    private SettingsUser() {}

    public static synchronized SettingsUser getInstance() {

        if (_instance == null)
            synchronized (SettingsUser.class) {
                if (_instance == null)
                    _instance = new SettingsUser();
            }
        return _instance;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}
