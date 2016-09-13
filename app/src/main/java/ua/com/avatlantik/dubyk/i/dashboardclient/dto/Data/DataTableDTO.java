package ua.com.avatlantik.dubyk.i.dashboardclient.dto.Data;

/**
 * Created by i.dubyk on 07.07.2016.
 */
public class DataTableDTO {

    private String typeData;
    private double sumMonth;
    private double sumDay;
    private double delta12;
    private double delta3;
    private double delta1;

    public DataTableDTO(String typeData, double sumMonth, double sumDay, double delta12, double delta3, double delta1) {
        this.typeData = typeData;
        this.sumMonth = sumMonth;
        this.sumDay = sumDay;
        this.delta12 = delta12;
        this.delta3 = delta3;
        this.delta1 = delta1;
    }

    public String getTypeData() {
        return typeData;
    }

    public void setTypeData(String typeData) {
        this.typeData = typeData;
    }

    public double getSumMonth() {
        return sumMonth;
    }

    public void setSumMonth(double sumMonth) {
        this.sumMonth = sumMonth;
    }

    public double getDelta12() {
        return delta12;
    }

    public void setDelta12(double delta12) {
        this.delta12 = delta12;
    }

    public double getSumDay() {
        return sumDay;
    }

    public void setSumDay(double sumDay) {
        this.sumDay = sumDay;
    }

    public double getDelta3() {
        return delta3;
    }

    public void setDelta3(double delta3) {
        this.delta3 = delta3;
    }

    public double getDelta1() {
        return delta1;
    }

    public void setDelta1(double delta1) {
        this.delta1 = delta1;
    }
}
