package com.sensepost.yeti.models;

import java.util.ArrayList;
import java.util.Iterator;
import com.sensepost.yeti.results.YileResult;

public class YileModel extends BaseModel {

    public YileModel() {
        super();
        this.setColumnNames(
                new String[]{"Source", "Host", "Domain", "Keep"}
        );
        this.setTypes(
                new Class[]{java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class, java.lang.String.class,}
        );
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        YileResult row = (YileResult) this.data.get(rowIndex);
        if (row == null) {
            return null;
        }
        switch (columnIndex) {
            case 0:
                return row.getSource();
            case 1:
                return row.getHostName();
            case 2:
                return row.getDomainName();
            case 3:
                return row.getKeep();
            case 4:
                return row.getRegistrant();
            case 5:
                return row.getIPAddress();
            default:
                return null;
        }
    }

    @Override
    public void addRow(Object item) {
        Iterator i = this.data.iterator();
        while (i.hasNext()) {
            YileResult r = (YileResult) i.next();
            if (r.getHostName().compareTo(((YileResult) item).getHostName()) == 0) {
                return;
            }
        }
        this.data.add((YileResult) item);
        fireTableDataChanged();
    }

    @Override
    public void addRows(ArrayList<Object> items) {
        //this.data.addAll(items);
        Iterator i = items.iterator();
        while (i.hasNext()) {
            this.data.add((YileResult) i.next());
        }
        fireTableDataChanged();
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        YileResult item = (YileResult) this.data.get(row);
        switch (col) {
            case 0:
                item.setSource(value.toString());
                break;
            case 1:
                item.setHostName(value.toString());
                break;
            case 2:
                item.setDomainName(value.toString());
                break;
            case 3:
                item.setKeep((Boolean) value);
                break;
            case 4:
                item.setRegistrant(value.toString());
                break;
            case 5:
                item.setIPAddress(value.toString());
                break;
        }
        fireTableCellUpdated(row, col);
    }
}
