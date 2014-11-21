package com.sensepost.yeti.models;

import java.util.ArrayList;
import com.sensepost.yeti.results.CertResult;

/**
 *
 * @author willemm
 */
public class CertModel extends BaseModel {

    public CertModel() {
        super();
        this.setColumnNames(new String[]{"IP Address", "Host name", "Domain name", "Keep"});
        this.setTypes(new Class[]{
            java.lang.String.class,
            java.lang.String.class,
            java.lang.String.class,
            java.lang.Boolean.class,});
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        CertResult row = (CertResult) data.get(rowIndex);
        if (row == null) {
            return null;
        }
        switch (columnIndex) {
            case 0:
                return row.getIpAddress();
            case 1:
                return row.getHostName();
            case 2:
                return row.getDomainName();
            case 3:
                return row.getKeep();
            default:
                return null;
        }
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        CertResult item = (CertResult) data.get(row);
        switch (col) {
            case 0:
                item.setIpAddress(value.toString());
            case 1:
                item.setHostName(value.toString());
            case 2:
                item.setDomainName(value.toString());
            case 3:
                item.setKeep(Boolean.valueOf(value.toString()));
        }
        fireTableCellUpdated(row, col);
    }

    @Override
    public void addRow(Object item) {
        data.add((CertResult) item);
        fireTableDataChanged();
    }

    @Override
    public void addRows(ArrayList<Object> items) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
