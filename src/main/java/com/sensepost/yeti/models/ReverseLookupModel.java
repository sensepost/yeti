package com.sensepost.yeti.models;

import java.util.ArrayList;
import com.sensepost.yeti.results.ReverseLookupResult;

/**
 *
 * @author willemm
 */
public class ReverseLookupModel extends BaseModel {

    public ReverseLookupModel() {
        super();
        this.setColumnNames(new String[]{"IP Address", "Hostname", "Domain", "Keep"});
        this.setTypes(new Class[]{
            java.lang.String.class,
            java.lang.String.class,
            java.lang.String.class,
            java.lang.Boolean.class
        }
        );
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ReverseLookupResult row = (ReverseLookupResult) this.data.get(rowIndex);
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
        ReverseLookupResult rowData = (ReverseLookupResult) this.data.get(row);
        if (rowData == null) {
            return;
        }
        switch (col) {
            case 0:
                rowData.setIpAddress(value.toString());
            case 1:
                rowData.setHostName(value.toString());
            case 2:
                rowData.setDomainName(value.toString());
            case 3:
                rowData.setKeep((Boolean) value);

        }
        fireTableCellUpdated(row, col);
    }

    @Override
    public void addRow(Object item) {
        this.data.add((ReverseLookupResult) item);
        fireTableDataChanged();
    }

    @Override
    public void addRows(ArrayList<Object> items) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
