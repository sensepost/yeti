package com.sensepost.yeti.models;

import java.util.ArrayList;
import java.util.Iterator;
import com.sensepost.yeti.results.DomainResult;

/**
 *
 * @author willemm
 */
public class TldModel extends BaseModel {

    public TldModel() {
        setColumnNames(
                new String[]{"Domain name", "Name server", "Admin", "Keep"}
        );
        setTypes(
                new Class[]{java.lang.String.class, java.lang.String.class,
                    java.lang.String.class, java.lang.Boolean.class,
                    java.lang.String.class,}
        );
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        DomainResult row = (DomainResult) data.get(rowIndex);
        if (row == null) {
            return null;
        }
        switch (columnIndex) {
            case 0:
                return row.getDomainName();
            case 1:
                return row.getNameServer();
            case 2:
                return row.getAdminName();
            case 3:
                return row.getKeep();
            case 4:
                return row.getRegistrant();
            default:
                return null;
        }
    }

    @Override
    public void addRow(Object item) {
        data.add((DomainResult) item);
        fireTableDataChanged();
    }

    @Override
    public void addRows(ArrayList<Object> items) {
        //data.addAll(items);
        Iterator i = items.iterator();
        while (i.hasNext()) {
            data.add((DomainResult) i.next());
        }
        fireTableDataChanged();
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        DomainResult item = (DomainResult) data.get(row);
        switch (col) {
            case 0:
                item.setDomainName(value.toString());
                break;
            case 1:
                item.setNameServer(value.toString());
                break;
            case 2:
                item.setAdminName(value.toString());
                break;
            case 3:
                item.setKeep((Boolean) value);
                break;
            case 4:
                item.setRegistrant(value.toString());
                break;
        }
        fireTableCellUpdated(row, col);
    }
}
