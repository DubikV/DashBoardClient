package ua.com.avatlantik.dubyk.i.dashboardclient.Data;

/**
 * Created by i.dubyk on 14.09.2016.
 */
public class Data_table {
    private String name;
    private double value1;
    private double value2;
    private double value3;

    public Data_table(String name, double value1, double value2, double value3) {
        this.name = name;
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue1() {
        return value1;
    }

    public void setValue1(double value1) {
        this.value1 = value1;
    }

    public double getValue2() {
        return value2;
    }

    public void setValue2(double value2) {
        this.value2 = value2;
    }

    public double getValue3() {
        return value3;
    }

    public void setValue3(double value3) {
        this.value3 = value3;
    }
}
