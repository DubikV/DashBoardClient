package ua.com.avatlantik.dubyk.i.dashboardclient.dto.Data;

/**
 * Created by i.dubyk on 07.07.2016.
 */
public class DataDTO {

    private String typeData;
    private int numberDay;
    private double valye;

    public DataDTO(String typeData, int numberDay, double valye) {
        this.typeData = typeData;
        this.numberDay = numberDay;
        this.valye = valye;
    }

    public String getTypeData() {
        return typeData;
    }

    public void setTypeData(String typeData) {
        this.typeData = typeData;
    }

    public int getNumberDay() {
        return numberDay;
    }

    public void setNumberDay(int numberDay) {
        this.numberDay = numberDay;
    }

    public double getValye() {
        return valye;
    }

    public void setValye(double valye) {
        this.valye = valye;
    }
}
