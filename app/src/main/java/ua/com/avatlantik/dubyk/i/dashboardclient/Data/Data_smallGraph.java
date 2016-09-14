package ua.com.avatlantik.dubyk.i.dashboardclient.Data;

/**
 * Created by i.dubyk on 14.09.2016.
 */
public class Data_smallGraph {
    private String type;
    private int xAxic;
    private double value;

    public Data_smallGraph(String type, int xAxic, double value) {
        this.type = type;
        this.xAxic = xAxic;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getxAxic() {
        return xAxic;
    }

    public void setxAxic(int xAxic) {
        this.xAxic = xAxic;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
