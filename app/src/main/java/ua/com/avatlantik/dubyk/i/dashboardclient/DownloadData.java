package ua.com.avatlantik.dubyk.i.dashboardclient;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Base64;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import ua.com.avatlantik.dubyk.i.dashboardclient.Constants.ConstantsGlobal;
import ua.com.avatlantik.dubyk.i.dashboardclient.Database.DBHelper;
import ua.com.avatlantik.dubyk.i.dashboardclient.Modules.Module_GetURL;

/**
 * Created by i.dubyk on 11.07.2016.
 */
public class DownloadData extends AsyncTask<String, Integer, String> {

    private HttpURLConnection urlConnection = null;
    private BufferedReader reader = null;
    private String resultJson = "";
    private MainActivity mainActivity;
    private ProgressDialog progressDialog;
    private boolean openStart;
    private int idItemSelected;
    private DBHelper dbHelper;
    private String mainTypeData;

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void setOpenStart(boolean openStart) {
        this.openStart = openStart;
    }

    public void setIdItemSelected(int idItemSelected) {
        this.idItemSelected = idItemSelected;
    }

    public void setMainTypeData(String mainTypeData) {
        this.mainTypeData = mainTypeData;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog = new ProgressDialog(mainActivity);
        progressDialog.setCancelable(true);
        progressDialog.setMessage(mainActivity.getString(R.string.dowload_data_from_server));
        progressDialog.show();

    }

    @Override
    protected String doInBackground(String... url) {
        String content = null;
        try{
            content = downloadData(url[0]);
        }catch (IOException e){}

        return content;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        progressDialog.cancel();

        if (result!="") {
            mainActivity.setToastToActivity(result);
        }

        if(openStart) {
            mainActivity.setNavigationItemSelected(idItemSelected);
        }


    }

    public String downloadData(String urlData) throws IOException {
        // получаем данные с внешнего ресурса
        try

        {
            URL url = new URL(urlData);

            urlConnection = (HttpURLConnection) url.openConnection();

            Module_GetURL module_getURL = new Module_GetURL();

            urlConnection.setRequestMethod("GET");
            urlConnection.setUseCaches(false);
            urlConnection.setConnectTimeout(10000);
            urlConnection.addRequestProperty ("Authorization", "Basic " + Base64.encodeToString((module_getURL.getusrlogin()+":"+module_getURL.getusrPassword()).getBytes(), Base64.NO_WRAP));
            urlConnection.connect();

            int status = 408;

            try {
                // Will throw IOException if server responds with 401.
                status = urlConnection.getResponseCode();
            } catch (IOException e) {
                // Will return 401, because now connection has the correct internal state.
                status = urlConnection.getResponseCode();
            }

            //int status = urlConnection.getResponseCode();

            switch (status) {
                case 400:
                    return mainActivity.getString(R.string.error_invalid_query);
                case 401:
                    return mainActivity.getString(R.string.error_authorisation_error);
                case 404:
                    return mainActivity.getString(R.string.error_data_not_found);
                case 200:
                case 201:
                    reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder buffer = new StringBuilder();
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line);
                    }
                    reader.close();
                    resultJson = buffer.toString();
                    urlConnection.disconnect();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return mainActivity.getString(R.string.error_retrieving_data);
        } catch (IOException e) {
            e.printStackTrace();
            return mainActivity.getString(R.string.error_retrieving_data);
        }

        return parseDataJson(resultJson);
    }
    private String parseDataJson(String strJson){

        dbHelper = new DBHelper(mainActivity);
        //dbHelper.deleteDataByMainType(mainTypeData);
        String result ="";
        JSONArray dataJsonArray = null;

        try {

            dataJsonArray = new JSONArray(strJson);

            for (int i = 0; i < dataJsonArray.length(); i++) {

                JSONObject dataO = dataJsonArray.getJSONObject(i);

                dbHelper.insertData(dataO.optString(ConstantsGlobal.TABLE_COLUMN_MAIN_TYPE_DATA,""),
                        dataO.optString(ConstantsGlobal.TABLE_COLUMN_BN_NAME,""),
                        dataO.optString(ConstantsGlobal.TABLE_COLUMN_BN_GUID,""),
                        dataO.optString(ConstantsGlobal.TABLE_COLUMN_BRANCH_NAME,""),
                        dataO.optString(ConstantsGlobal.TABLE_COLUMN_BRANCH_GUID,""),
                        dataO.optString(ConstantsGlobal.TABLE_COLUMN_MANAGER_NAME,""),
                        dataO.optString(ConstantsGlobal.TABLE_COLUMN_MANAGER_GUID,""),
                        dataO.optString(ConstantsGlobal.TABLE_COLUMN_TYPE_DATA,""),
                        dataO.optInt(ConstantsGlobal.TABLE_COLUMN_PERIOD,0),
                        dataO.optDouble(ConstantsGlobal.TABLE_COLUMN_DATA,0.0));
            }

            //result = mainActivity.getString(R.string.finish_dowload_data);

        } catch (JSONException e) {
            e.printStackTrace();
            result = mainActivity.getString(R.string.error_processing_data);
        }

        return result;
    }

}
