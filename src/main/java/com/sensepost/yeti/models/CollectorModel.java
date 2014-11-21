package com.sensepost.yeti.models;

import java.util.ArrayList;
import com.sensepost.yeti.results.CollectorResult;

/**
 *
 * @author willem
 */
public class CollectorModel extends BaseModel {

    public CollectorModel() {
        super();
        this.setColumnNames(new String[]{"Domain name", "Host name", "IP address", "Type", "Extra data", "Keep"});
        this.setTypes(new Class[]{
            java.lang.String.class,
            java.lang.String.class,
            java.lang.String.class,
            java.lang.String.class,
            java.lang.String.class,
            java.lang.Boolean.class,});
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        CollectorResult row = (CollectorResult) this.data.get(rowIndex);
        if (row == null) {
            return null;
        }
        switch (columnIndex) {
            case 0:
                return row.getDomainName();
            case 1:
                return row.getHostName();
            case 2:
                return row.getIpAddress();
            case 3:
                return row.getType();
            case 4:
                return row.getExtraData();
            case 5:
                return row.getKeep();
            default:
                return null;
        }
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        CollectorResult item = (CollectorResult) this.data.get(row);
        switch (col) {
            case 0:
                item.setDomainName(value.toString());
                break;
            case 1:
                item.setHostName(value.toString());
                break;
            case 2:
                item.setIpAddress(value.toString());
                break;
            case 3:
                item.setType(value.toString());
                break;
            case 4:
                item.setExtraData(value.toString());
                break;
            case 5:
                item.setKeep(Boolean.valueOf(value.toString()));
        }
        fireTableCellUpdated(row, col);
    }

    @Override
    public void addRow(Object item) {
        this.data.add((CollectorResult) item);
        fireTableDataChanged();
    }

    @Override
    public void addRows(ArrayList<Object> items) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
