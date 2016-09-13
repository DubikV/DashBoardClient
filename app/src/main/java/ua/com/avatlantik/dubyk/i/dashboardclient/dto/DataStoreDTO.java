package ua.com.avatlantik.dubyk.i.dashboardclient.dto;

import java.util.ArrayList;
import java.util.Date;

import ua.com.avatlantik.dubyk.i.dashboardclient.dto.Data.DataAddDTO;
import ua.com.avatlantik.dubyk.i.dashboardclient.dto.Data.DataDTO;
import ua.com.avatlantik.dubyk.i.dashboardclient.dto.Data.DataTableDTO;

/**
 * Created by i.dubyk on 11.07.2016.
 */
public class DataStoreDTO {
    private Date dateDownload;

    private ArrayList<DataDTO> salesUGKDTO;
    private ArrayList<DataTableDTO> salesUGKTableDTO;
    private DataAddDTO salesUGKAddDTO;

    private ArrayList<DataDTO> moneyDTO;
    private ArrayList<DataTableDTO> moneyTableDTO;
    private DataAddDTO moneyAddDTO;

    private ArrayList<DataDTO> stoksDTO;
    private ArrayList<DataTableDTO> stoksTableDTO;
    private DataAddDTO stoksAddDTO;

    private ArrayList<DataDTO> marginDTO;
    private ArrayList<DataTableDTO> marginTableDTO;
    private DataAddDTO marginAddDTO;

    private static volatile DataStoreDTO _instance = null;

    private DataStoreDTO() {}

    public static DataStoreDTO getInstance() {

        if (_instance == null)
            synchronized (DataStoreDTO.class) {
                if (_instance == null)
                    _instance = new DataStoreDTO();
            }

        return _instance;

    }

    public Date getDateDownload() {
        return dateDownload;
    }

    public void setDateDownload(Date dateDownload) {
        this.dateDownload = dateDownload;
    }

    public ArrayList<DataDTO> getSalesUGKDTO() {
        return salesUGKDTO;
    }

    public void setSalesUGKDTO(ArrayList<DataDTO> salesUGKDTO) {
        this.salesUGKDTO = salesUGKDTO;
    }

    public ArrayList<DataTableDTO> getSalesUGKTableDTO() {
        return salesUGKTableDTO;
    }

    public void setSalesUGKTableDTO(ArrayList<DataTableDTO> salesUGKTableDTO) {
        this.salesUGKTableDTO = salesUGKTableDTO;
    }

    public ArrayList<DataTableDTO> getMoneyTableDTO() {
        return moneyTableDTO;
    }

    public void setMoneyTableDTO(ArrayList<DataTableDTO> moneyTableDTO) {
        this.moneyTableDTO = moneyTableDTO;
    }

    public ArrayList<DataDTO> getMoneyDTO() {
        return moneyDTO;
    }

    public void setMoneyDTO(ArrayList<DataDTO> moneyDTO) {
        this.moneyDTO = moneyDTO;
    }

    public DataAddDTO getSalesUGKAddDTO() {
        return salesUGKAddDTO;
    }

    public void setSalesUGKAddDTO(DataAddDTO salesUGKAddDTO) {
        this.salesUGKAddDTO = salesUGKAddDTO;
    }

    public DataAddDTO getMoneyAddDTO() {
        return moneyAddDTO;
    }

    public void setMoneyAddDTO(DataAddDTO moneyAddDTO) {
        this.moneyAddDTO = moneyAddDTO;
    }

    public DataAddDTO getStoksAddDTO() {
        return stoksAddDTO;
    }

    public void setStoksAddDTO(DataAddDTO stoksAddDTO) {
        this.stoksAddDTO = stoksAddDTO;
    }

    public ArrayList<DataTableDTO> getStoksTableDTO() {
        return stoksTableDTO;
    }

    public void setStoksTableDTO(ArrayList<DataTableDTO> stoksTableDTO) {
        this.stoksTableDTO = stoksTableDTO;
    }

    public ArrayList<DataDTO> getStoksDTO() {
        return stoksDTO;
    }

    public void setStoksDTO(ArrayList<DataDTO> stoksDTO) {
        this.stoksDTO = stoksDTO;
    }

    public ArrayList<DataDTO> getMarginDTO() {
        return marginDTO;
    }

    public void setMarginDTO(ArrayList<DataDTO> marginDTO) {
        this.marginDTO = marginDTO;
    }

    public ArrayList<DataTableDTO> getMarginTableDTO() {
        return marginTableDTO;
    }

    public void setMarginTableDTO(ArrayList<DataTableDTO> marginTableDTO) {
        this.marginTableDTO = marginTableDTO;
    }

    public DataAddDTO getMarginAddDTO() {
        return marginAddDTO;
    }

    public void setMarginAddDTO(DataAddDTO marginAddDTO) {
        this.marginAddDTO = marginAddDTO;
    }
}
