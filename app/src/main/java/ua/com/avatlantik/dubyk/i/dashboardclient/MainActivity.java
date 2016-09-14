package ua.com.avatlantik.dubyk.i.dashboardclient;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import ua.com.avatlantik.dubyk.i.dashboardclient.Constants.ConstantsGlobal;
import ua.com.avatlantik.dubyk.i.dashboardclient.Database.DBHelper;
import ua.com.avatlantik.dubyk.i.dashboardclient.Modules.Module_GetURL;
import ua.com.avatlantik.dubyk.i.dashboardclient.Modules.Module_ReadWrite_Data;
import ua.com.avatlantik.dubyk.i.dashboardclient.Settings.SettingConnect;
import ua.com.avatlantik.dubyk.i.dashboardclient.Settings.SettingsUser;
import ua.com.avatlantik.dubyk.i.dashboardclient.fragment.FragmentComparion_12_3;
import ua.com.avatlantik.dubyk.i.dashboardclient.fragment.InfoFragment;
import ua.com.avatlantik.dubyk.i.dashboardclient.fragment.SettingsFragment;

public class MainActivity extends AppCompatActivity {

    private static final int LAYOUT = R.layout.activity_main;

    private Resources res;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private FragmentManager mFragmentManager;
    private Module_ReadWrite_Data module_readWrite_data;
    private Module_GetURL module_getURL;
    private NavigationView navigationView;
    private SettingConnect settingConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        module_readWrite_data = new Module_ReadWrite_Data(this);
        module_getURL = new Module_GetURL(this);
        settingConnect = SettingConnect.getInstance();

        res = getResources();

        module_readWrite_data.readDataFromMemory();


        initToolbar();

        initNavigationView();

        initTabs();

        downloadFirstData();

        initSpinners();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        module_readWrite_data.saveDataToMemory();

    }

    @Override
    protected void onStop() {
        super.onStop();

        module_readWrite_data.saveDataToMemory();

    }


    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                int id = menuItem.getItemId();

                if (id == R.id.action_loadData) {
                    downloadDatafullData();
                    return true;
                }
                return false;

            }
        });

        toolbar.inflateMenu(R.menu.main);


    }

    private void initSpinners() {


        ArrayList<String> list =  new ArrayList<String>();
        DBHelper dbHelper = new DBHelper(this);

        Cursor res = dbHelper.getDataWithSelection(ConstantsGlobal.TABLE_COLUMN_BN_NAME,
                ""+ConstantsGlobal.TABLE_COLUMN_TYPE_DATA+" = '"+ConstantsGlobal.TYPE_DATA_BN_DATA+"'");

        while(res.isAfterLast() == false){
            list.add(res.getString(res.getColumnIndex(ConstantsGlobal.TABLE_COLUMN_BN_NAME)));
            res.moveToNext();
        }

        Spinner spinner_bn = (Spinner) navigationView.getHeaderView(0).findViewById(R.id.spinner_bn);

        spinner_bn.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,list));


        Spinner spinner_period = (Spinner) navigationView.getHeaderView(0).findViewById(R.id.spinner_period);

        spinner_period.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, ConstantsGlobal.mounth ));

        Calendar c = Calendar.getInstance();
        int number = c.get(Calendar.MONTH);
        if (number>12){
            number = 12;
        }else{
            number = number++;
        }
        spinner_period.setSelection(number, true);

    }

    private void initTabs() {

        mFragmentManager = getSupportFragmentManager();

    }

    private void initNavigationView() {

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close){


            @Override
            public boolean onOptionsItemSelected(MenuItem item) {
                return super.onOptionsItemSelected(item);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                 /* hide keyboard */
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);


                TextView text_nav_heared_login = (TextView) findViewById(R.id.text_nav_heared_login);

                SettingsUser settingsUser = SettingsUser.getInstance();

                if (settingsUser.getUserLogin() != null){

                    text_nav_heared_login.setText(settingsUser.getUserLogin());

                }

                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();

        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                drawerLayout.closeDrawers();

                int itemId = item.getItemId();
                item.setChecked(true);

                if (itemId == R.id.nav_comparion_12_3) {
                    onNavigationItemSelectedComparion_12_3();
                }else if (itemId == R.id.nav_comparion_3_1) {

                }else if (itemId == R.id.nav_comparion_1_exp) {

                }else if (itemId == R.id.nav_comparion_12_exp) {

                }else if (itemId == R.id.nav_comparion_1_exp_dec) {

                }else {
                    setNavigationItemSelected(itemId);
                }

                return false;
            }


        });
    }

    public void setNavigationItemSelected(int itemId) {

        if (itemId == R.id.nav_comparion_12_3) {
            setToolbarText(getString(R.string.nav_comparion_12_3_ua));
            FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
            xfragmentTransaction.replace(R.id.containerView, new FragmentComparion_12_3()).commit();
        }else if (itemId == R.id.nav_info) {
            setToolbarText(getString(R.string.nav_info_ua));
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.containerView, new InfoFragment()).commit();
            return;

        }else if (itemId == R.id.nav_settings) {
            setToolbarText(getString(R.string.action_settings_ua));
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.containerView, new SettingsFragment()).commit();
        }else if (itemId == R.id.nav_exit) {

            SetOnExitApp();

        }
    }

    private void onNavigationItemSelectedComparion_12_3(){

        if(module_getURL.getCheckConnektion()) {

            String url = module_getURL.getGetURL(ConstantsGlobal.SALES_GET_NAME);
            if(url.isEmpty() || url==null){
                return;
            }
            DownloadData downloadData = new DownloadData();
            downloadData.setMainActivity(this);
            downloadData.setNameData(ConstantsGlobal.SALES_GET_NAME);
            downloadData.setOpenStart(true);
            downloadData.setIdItemSelected(R.id.nav_comparion_12_3);
            downloadData.execute(url);

        }

        setNavigationItemSelected(R.id.nav_comparion_12_3);
    }


    private void SetOnExitApp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.title_Exit));
        builder.setMessage(getString(R.string.questions_Exit));

        builder.setPositiveButton(getString(R.string.questions_Exit_answer_yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                System.exit(0);

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

    @Override
    public void onBackPressed() {
        try{
            drawerLayout.closeDrawers();
        }catch (Exception e){
            super.onBackPressed();
        }
    }

    public void downloadDatafullData(){


//        if(module_getURL.getCheckConnektion()) {
//
//            String url ="";
//
//            url = module_getURL.getGetURL(ConstantsGlobal.SALES_GET_NAME);
//            if(!url.isEmpty() && url!=null) {
//                DownloadData downloadDataSales = new DownloadData();
//                downloadDataSales.setMainActivity(this);
//                downloadDataSales.setNameData(ConstantsGlobal.SALES_GET_NAME);
//                downloadDataSales.setOpenStart(false);
//                downloadDataSales.execute(url);
//            }
//
//            url = module_getURL.getGetURL(ConstantsGlobal.SALESMONEY_GET_NAME);
//            if(!url.isEmpty() && url!=null) {
//                DownloadData downloadDataMoney = new DownloadData();
//                downloadDataMoney.setMainActivity(this);
//                downloadDataMoney.setNameData(ConstantsGlobal.SALESMONEY_GET_NAME);
//                downloadDataMoney.setOpenStart(false);
//                downloadDataMoney.execute(url);
//            }
//            url = module_getURL.getGetURL(ConstantsGlobal.STOCKS_GET_NAME);
//            if(!url.isEmpty() && url!=null) {
//                DownloadData downloadDataMoney = new DownloadData();
//                downloadDataMoney.setMainActivity(this);
//                downloadDataMoney.setNameData(ConstantsGlobal.STOCKS_GET_NAME);
//                downloadDataMoney.setOpenStart(false);
//                downloadDataMoney.execute(url);
//            }
//            setNavigationItemSelected(R.id.nav_salesUgk);
//        }

    }

    public void downloadFirstData(){

        String textURL = "getDataDTO?type="+ ConstantsGlobal.TYPE_DATA_BN_DATA;

        if(module_getURL.getCheckConnektion()) {

            String url = module_getURL.getGetURL(textURL);
            if(url.isEmpty() || url==null){
                setToolbarText(getString(R.string.action_settings_ua));
                FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.containerView, new SettingsFragment()).commit();
                return;
            }
            DownloadData downloadData = new DownloadData();
            downloadData.setMainActivity(this);
            downloadData.setNameData(null);
            downloadData.setOpenStart(false);
            downloadData.execute(url);

            drawerLayout.openDrawer(Gravity.LEFT);

        }

    }

    public void setToolbarText(String text){

        toolbar.setTitle(text);

    }

    public void setToastToActivity(String textToast){

        Toast.makeText(getApplicationContext(), textToast, Toast.LENGTH_LONG).show();

    }




}
