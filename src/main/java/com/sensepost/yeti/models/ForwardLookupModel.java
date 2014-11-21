package com.sensepost.yeti.models;

import java.util.ArrayList;
import java.util.Iterator;
import com.sensepost.yeti.results.ForwardLookupResult;

/**
 *
 * @author willemm
 */
public class ForwardLookupModel extends BaseModel {

    public ForwardLookupModel() {
        setColumnNames(new String[]{"Domain name", "Host name", "Lookup type",
            "IP Address", "Keep"});
        setTypes(new Class[]{
            java.lang.String.class,
            java.lang.String.class,
            java.lang.String.class,
            java.lang.String.class,
            java.lang.Boolean.class,});
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ForwardLookupResult row = (ForwardLookupResult) data.get(rowIndex);
        if (row == null) {
            return null;
        }
        switch (columnIndex) {
            case 0:
                return row.getDomainName();
            case 1:
                return row.getHostName();
            case 2:
                return row.getLookupType();
            case 3:
                return row.getIpAddress();
            case 4:
                return row.getKeep();
            default:
                return null;
        }
    }

    @Override
    public void addRow(Object item) {
        data.add((ForwardLookupResult) item);
        fireTableDataChanged();
    }

    @Override
    public void addRows(ArrayList<Object> items) {
        Iterator i = items.iterator();
        while (i.hasNext()) {
            data.add((ForwardLookupResult) i.next());
        }
        fireTableDataChanged();
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        ForwardLookupResult rowData = (ForwardLookupResult) data.get(row);
        if (rowData == null) {
            return;
        }
        switch (col) {
            case 0:
                break;
            case 1:
                rowData.setHostName(value.toString());
                break;
            case 2:
                rowData.setLookupType(value.toString());
                break;
            case 3:
                rowData.setIpAddress(value.toString());
                break;
            case 4:
                rowData.setKeep((Boolean) value);
                break;

        }
        fireTableCellUpdated(row, col);
    }
}
