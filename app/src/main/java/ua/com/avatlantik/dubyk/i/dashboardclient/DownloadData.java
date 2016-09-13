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
import java.util.ArrayList;

import ua.com.avatlantik.dubyk.i.dashboardclient.Constants.ConstantsGlobal;
import ua.com.avatlantik.dubyk.i.dashboardclient.Modules.Module_GetURL;
import ua.com.avatlantik.dubyk.i.dashboardclient.dto.Data.DataAddDTO;
import ua.com.avatlantik.dubyk.i.dashboardclient.dto.Data.DataDTO;
import ua.com.avatlantik.dubyk.i.dashboardclient.dto.Data.DataTableDTO;
import ua.com.avatlantik.dubyk.i.dashboardclient.dto.DataStoreDTO;

/**
 * Created by i.dubyk on 11.07.2016.
 */
public class DownloadData extends AsyncTask<String, Integer, String> {

    private HttpURLConnection urlConnection = null;
    private BufferedReader reader = null;
    private String resultJson = "";
    private MainActivity mainActivity;
    private ProgressDialog progressDialog;
    private String nameData;
    private DataStoreDTO dataStoreDTO;
    private boolean openStart;
    private int idItemSelected;

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void setNameData(String nameData) {
        this.nameData = nameData;
    }

    public void setOpenStart(boolean openStart) {
        this.openStart = openStart;
    }

    public void setIdItemSelected(int idItemSelected) {
        this.idItemSelected = idItemSelected;
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

        dataStoreDTO = DataStoreDTO.getInstance();

        if (nameData.equals(ConstantsGlobal.SALES_GET_NAME)) {

            return parseSalesUGK(strJson);

        }else if(nameData.equals(ConstantsGlobal.SALESMONEY_GET_NAME)){

            return parseSalesMoney(strJson);

        }else if(nameData.equals(ConstantsGlobal.STOCKS_GET_NAME)){
            return parseStocks(strJson);
        }

        return "";
    }

    private String parseSalesUGK(String strJson){

        String result ="";

        JSONArray dataJsonArray = null;
        try {

            dataJsonArray = new JSONArray(strJson);

            ArrayList<DataDTO> arrayS = new ArrayList<>();

            JSONObject SalesUGKObject = dataJsonArray.getJSONObject(0);

            JSONArray SalesUGKDTOarray = SalesUGKObject.getJSONArray("salesUGK");

            for (int i = 0; i < SalesUGKDTOarray.length(); i++) {

                JSONObject SalesUGK = SalesUGKDTOarray.getJSONObject(i);

                arrayS.add(new DataDTO(SalesUGK.optString("typeData",""), SalesUGK.optInt("numberDay",0), SalesUGK.optDouble("value",0.0)));
            }

            dataStoreDTO.setSalesUGKDTO(arrayS);

            ArrayList<DataTableDTO> arrayT = new ArrayList<>();

            JSONObject SalesUGKTableObject = dataJsonArray.getJSONObject(1);

            JSONArray SalesUGKTableDTOarray = SalesUGKTableObject.getJSONArray("salesUGKTable");

            for (int i = 0; i < SalesUGKTableDTOarray.length(); i++) {

                JSONObject SalesUGKTable = SalesUGKTableDTOarray.getJSONObject(i);

                arrayT.add(new DataTableDTO(SalesUGKTable.optString("typeData",""), SalesUGKTable.optDouble("sumMonth",0), SalesUGKTable.optDouble("sumDay",0.0), SalesUGKTable.optDouble("delta12",0.0),SalesUGKTable.optDouble("delta3",0.0),SalesUGKTable.optDouble("delta1",0.0)));
            }

            dataStoreDTO.setSalesUGKTableDTO(arrayT);

            JSONObject SalesUGKaddObject = dataJsonArray.getJSONObject(2);

            JSONObject SalesUGKAddObject = SalesUGKaddObject.getJSONObject("salesUGKAdd");


            dataStoreDTO.setSalesUGKAddDTO(new DataAddDTO(SalesUGKAddObject.optDouble("planeNorm",0.0), SalesUGKAddObject.optDouble("plane",0.0),SalesUGKAddObject.optDouble("fact",0.0),SalesUGKAddObject.optDouble("factNorm",0.0)));

            //result = mainActivity.getString(R.string.finish_dowload_data);

        } catch (JSONException e) {
            e.printStackTrace();
            result = mainActivity.getString(R.string.error_processing_data);
        }

        return result;
    }

    private String parseSalesMoney(String strJson){

        String result ="";

        try {

            JSONArray dataJsArray = new JSONArray(strJson);

            ArrayList<DataDTO> arrayS = new ArrayList<>();

            JSONObject SalesMoneyObject = dataJsArray.getJSONObject(0);

            JSONArray SalesMoneyDTOarray = SalesMoneyObject.getJSONArray("salesMoney");

            for (int i = 0; i < SalesMoneyDTOarray.length(); i++) {

                JSONObject SalesMoney = SalesMoneyDTOarray.getJSONObject(i);

                arrayS.add(new DataDTO(SalesMoney.optString("typeData",""), SalesMoney.optInt("numberDay",0), SalesMoney.optDouble("value",0.0)));
            }

            dataStoreDTO.setMoneyDTO(arrayS);

            ArrayList<DataTableDTO> arrayT = new ArrayList<>();

            JSONObject SalesMoneyTableObject = dataJsArray.getJSONObject(1);

            JSONArray moneyTableDTOarray = SalesMoneyTableObject.getJSONArray("salesMoneyTable");

            for (int i = 0; i < moneyTableDTOarray.length(); i++) {

                JSONObject moneyTable = moneyTableDTOarray.getJSONObject(i);

                arrayT.add(new DataTableDTO(moneyTable.optString("typeData",""), moneyTable.optDouble("sumMonth",0), moneyTable.optDouble("sumDay",0.0), moneyTable.optDouble("delta12",0.0),moneyTable.optDouble("delta3",0.0),moneyTable.optDouble("delta1",0.0)));
            }

            dataStoreDTO.setMoneyTableDTO(arrayT);

            JSONObject MoneyaddObject = dataJsArray.getJSONObject(2);

            JSONObject MoneyAddObject = MoneyaddObject.getJSONObject("salesMoneyAdd");


            dataStoreDTO.setMoneyAddDTO(new DataAddDTO(MoneyAddObject.optDouble("planeNorm",0.0), MoneyAddObject.optDouble("plane",0.0),MoneyAddObject.optDouble("fact",0.0),MoneyAddObject.optDouble("factNorm",0.0)));

            //result = mainActivity.getString(R.string.finish_dowload_data);

        } catch (JSONException e) {
            e.printStackTrace();
            return mainActivity.getString(R.string.error_processing_data);
        }

        return result;
    }

    private String parseStocks(String strJson){

        String result ="";

        try {

            JSONArray dataJsArray = new JSONArray(strJson);

            ArrayList<DataDTO> arrayS = new ArrayList<>();

            JSONObject StocksObject = dataJsArray.getJSONObject(0);

            JSONArray StocksDTOarray = StocksObject.getJSONArray("stocks");

            for (int i = 0; i < StocksDTOarray.length(); i++) {

                JSONObject stock = StocksDTOarray.getJSONObject(i);

                arrayS.add(new DataDTO(stock.optString("typeData",""), stock.optInt("numberDay",0), stock.optDouble("value",0.0)));
            }

            dataStoreDTO.setStoksDTO(arrayS);

            ArrayList<DataTableDTO> arrayT = new ArrayList<>();

            JSONObject StocksTableObject = dataJsArray.getJSONObject(1);

            JSONArray stoksTableDTOarray = StocksTableObject.getJSONArray("stocksTable");

            for (int i = 0; i < stoksTableDTOarray.length(); i++) {

                JSONObject moneyTable = stoksTableDTOarray.getJSONObject(i);

                arrayT.add(new DataTableDTO(moneyTable.optString("typeData",""), moneyTable.optDouble("sumMonth",0), moneyTable.optDouble("sumDay",0.0), moneyTable.optDouble("delta12",0.0),moneyTable.optDouble("delta3",0.0),moneyTable.optDouble("delta1",0.0)));
            }

            dataStoreDTO.setStoksTableDTO(arrayT);

            JSONObject StocksAddObject = dataJsArray.getJSONObject(2);

            JSONObject stocksAddObject = StocksAddObject.getJSONObject("stocksAdd");


            dataStoreDTO.setStoksAddDTO(new DataAddDTO(stocksAddObject.optDouble("planeNorm",0.0), stocksAddObject.optDouble("plane",0.0),stocksAddObject.optDouble("fact",0.0),stocksAddObject.optDouble("factNorm",0.0)));

            //result = mainActivity.getString(R.string.finish_dowload_data);

        } catch (JSONException e) {
            e.printStackTrace();
            return mainActivity.getString(R.string.error_processing_data);
        }

        return result;
    }

}
