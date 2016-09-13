package ua.com.avatlantik.dubyk.i.dashboardclient.dto.Data;

/**
 * Created by i.dubyk on 07.07.2016.
 */
public class DataAddDTO {

    private double planeNorm;
    private double plane;
    private double fact;
    private double factNorm;

    public DataAddDTO(double planeNorm, double plane, double fact, double factNorm) {
        this.planeNorm = planeNorm;
        this.plane = plane;
        this.fact = fact;
        this.factNorm = factNorm;
    }

    public double getPlaneNorm() {
        return planeNorm;
    }

    public void setPlaneNorm(double planeNorm) {
        this.planeNorm = planeNorm;
    }

    public double getPlane() {
        return plane;
    }

    public void setPlane(double plane) {
        this.plane = plane;
    }

    public double getFact() {
        return fact;
    }

    public void setFact(double fact) {
        this.fact = fact;
    }

    public double getFactNorm() {
        return factNorm;
    }

    public void setFactNorm(double factNorm) {
        this.factNorm = factNorm;
    }
}
