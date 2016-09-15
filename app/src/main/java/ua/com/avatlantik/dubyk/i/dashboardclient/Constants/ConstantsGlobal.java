package ua.com.avatlantik.dubyk.i.dashboardclient.Constants;

public class ConstantsGlobal {

    public static final String[] mounth = {"Січень","Лютий","Березень","Квітень","Травень","Червень","Липень",
                                 "Серпень","Вересень","Жовтень","Листопад","Грудень"};

    public static final int MAX_TIME =2000;
    public static final int HALF_TIME =1000;
    public static final int SMALL_TIME =500;

    public static final String HTTPSERVICE_1C_NAME = "/hs/DashBoard";

    public static final String PLANE_12Q = "12Q";
    public static final String PLANE_3Q = "3Q";
    public static final String PLANE_1Q = "1Q";
    public static final String PLANE_12S = "12S";
    public static final String PLANE_3S = "3S";
    public static final String PLANE_1S = "1S";
    public static final String PLANE_12P = "12P";
    public static final String PLANE_3P = "3P";
    public static final String PLANE_1P = "1P";

    public static final String FACT = "fact";
    public static final String NORM = "norm";
    public static final String QC = "quantityClients";
    public static final String AZ = "averageClients";



    //===To Data Base
    public static final String DATABASE_NAME = "dataB.db";
    public static final String TABLE_NAME = "dataTable";
    public static final String TABLE_COLUMN_ID = "id";
    public static final String TABLE_COLUMN_MAIN_TYPE_DATA = "mainTypeData";
    public static final String TABLE_COLUMN_BN_NAME = "bnName";
    public static final String TABLE_COLUMN_BN_GUID = "bnGuid";
    public static final String TABLE_COLUMN_BRANCH_NAME = "branchName";
    public static final String TABLE_COLUMN_BRANCH_GUID = "branchGuid";
    public static final String TABLE_COLUMN_MANAGER_NAME = "managerName";
    public static final String TABLE_COLUMN_MANAGER_GUID = "managerGuid";
    public static final String TABLE_COLUMN_TYPE_DATA = "typeData";
    public static final String TABLE_COLUMN_PERIOD = "period";
    public static final String TABLE_COLUMN_DATA = "data";

    //===Type Data
    public static final String TYPE_DATA_BN_DATA = "bnData";
    public static final String TYPE_DATA_BN_DATA_RES = "bnDataRes";
    public static final String TYPE_DATA_BRANCH_DATA = "branchData";
    public static final String TYPE_DATA_MANAGER_DATA = "managerData";

    public static final String TYPE_DATA_12_3_DATA = "12_3";
    public static final String TYPE_DATA_3_1_DATA = "3_1";
    public static final String TYPE_DATA_1_exp_DATA = "1_exp";

}
