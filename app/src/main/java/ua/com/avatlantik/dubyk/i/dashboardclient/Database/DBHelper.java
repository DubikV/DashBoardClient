package ua.com.avatlantik.dubyk.i.dashboardclient.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import ua.com.avatlantik.dubyk.i.dashboardclient.Constants.ConstantsGlobal;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context)
    {
        super(context, ConstantsGlobal.DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table "+ ConstantsGlobal.TABLE_NAME +
                        "("+ ConstantsGlobal.TABLE_COLUMN_ID+" integer primary key, "+
                        ConstantsGlobal.TABLE_COLUMN_MAIN_TYPE_DATA+" text, " +
                        ConstantsGlobal.TABLE_COLUMN_BN_NAME+" text, " +
                        ConstantsGlobal.TABLE_COLUMN_BN_GUID+" text, " +
                        ConstantsGlobal.TABLE_COLUMN_BRANCH_NAME+" text, " +
                        ConstantsGlobal.TABLE_COLUMN_BRANCH_GUID+" text, " +
                        ConstantsGlobal.TABLE_COLUMN_MANAGER_NAME+" text, " +
                        ConstantsGlobal.TABLE_COLUMN_MANAGER_GUID+" text, " +
                        ConstantsGlobal.TABLE_COLUMN_TYPE_DATA+" text, " +
                        ConstantsGlobal.TABLE_COLUMN_PERIOD+" integer, " +
                        ConstantsGlobal.TABLE_COLUMN_DATA+" double)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS "+ ConstantsGlobal.TABLE_NAME );
        onCreate(db);
    }

    public boolean insertData  (String mainTypeData,String bnName, String bnGuid, String branchName, String branchGuid,
                                String managerName, String managerGuid, String typeData, int period, double data)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ConstantsGlobal.TABLE_COLUMN_MAIN_TYPE_DATA, mainTypeData);
        contentValues.put(ConstantsGlobal.TABLE_COLUMN_BN_NAME, bnName);
        contentValues.put(ConstantsGlobal.TABLE_COLUMN_BN_GUID, bnGuid);
        contentValues.put(ConstantsGlobal.TABLE_COLUMN_BRANCH_NAME, branchName);
        contentValues.put(ConstantsGlobal.TABLE_COLUMN_BRANCH_GUID, branchGuid);
        contentValues.put(ConstantsGlobal.TABLE_COLUMN_MANAGER_NAME, managerName);
        contentValues.put(ConstantsGlobal.TABLE_COLUMN_MANAGER_GUID, managerGuid);
        contentValues.put(ConstantsGlobal.TABLE_COLUMN_TYPE_DATA, typeData);
        contentValues.put(ConstantsGlobal.TABLE_COLUMN_PERIOD, period);
        contentValues.put(ConstantsGlobal.TABLE_COLUMN_DATA, data);
        db.insert(ConstantsGlobal.TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+ ConstantsGlobal.TABLE_NAME +" where "+ ConstantsGlobal.TABLE_COLUMN_ID+"="+id+"", null );
        return res;
    }


    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, ConstantsGlobal.TABLE_NAME);
        return numRows;
    }

    public boolean updateData (Integer id, String mainTypeData, String bnName, String bnGuid, String branchName, String branchGuid,
                                  String managerName, String managerGuid, String typeData, int period, double data)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ConstantsGlobal.TABLE_COLUMN_MAIN_TYPE_DATA, mainTypeData);
        contentValues.put(ConstantsGlobal.TABLE_COLUMN_BN_NAME, bnName);
        contentValues.put(ConstantsGlobal.TABLE_COLUMN_BN_GUID, bnGuid);
        contentValues.put(ConstantsGlobal.TABLE_COLUMN_BRANCH_NAME, branchName);
        contentValues.put(ConstantsGlobal.TABLE_COLUMN_BRANCH_GUID, branchGuid);
        contentValues.put(ConstantsGlobal.TABLE_COLUMN_MANAGER_NAME, managerName);
        contentValues.put(ConstantsGlobal.TABLE_COLUMN_MANAGER_GUID, managerGuid);
        contentValues.put(ConstantsGlobal.TABLE_COLUMN_TYPE_DATA, typeData);
        contentValues.put(ConstantsGlobal.TABLE_COLUMN_PERIOD, period);
        contentValues.put(ConstantsGlobal.TABLE_COLUMN_DATA, data);
        db.update(ConstantsGlobal.TABLE_NAME, contentValues, ""+ ConstantsGlobal.TABLE_COLUMN_ID+" = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteDataById(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(ConstantsGlobal.TABLE_NAME,
                ""+ ConstantsGlobal.TABLE_COLUMN_ID + " = ? ",
                new String[] { Integer.toString(id) });
    }

    public Integer deleteDataByMainType(String mainTypeData) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(ConstantsGlobal.TABLE_NAME,
                ""+ ConstantsGlobal.TABLE_COLUMN_MAIN_TYPE_DATA + " = ? ",
                new String[] { mainTypeData });
    }

    public Integer deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(ConstantsGlobal.TABLE_NAME, null, null);
    }

    public ArrayList<String> getDataOneColumString(String colum) {
        ArrayList<String> array_list = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select distinct "+colum+" from "+ ConstantsGlobal.TABLE_NAME + "", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(ConstantsGlobal.TABLE_COLUMN_BN_NAME)));
            res.moveToNext();
        }
        return array_list;
    }


    public Cursor getDataWithSelection(String colums, String selections) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select "+colums+" from "+ ConstantsGlobal.TABLE_NAME +
                                  " where "+selections+"", null );
        res.moveToFirst();

        return res;
    }
}