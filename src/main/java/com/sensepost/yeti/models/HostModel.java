package com.sensepost.yeti.models;

import java.util.ArrayList;
import java.util.List;
import com.sensepost.yeti.common.Globals;
import com.sensepost.yeti.persistence.DataStore;
import com.sensepost.yeti.results.HostEntry;

public class HostModel extends BaseModel {

    public HostModel() {
        this.setColumnNames(new String[]{"Hostname", "Domain"});
        this.setTypes(new Class[]{
            java.lang.String.class,
            java.lang.String.class,});
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (data.size() > rowIndex) {
            HostEntry row = (HostEntry) data.get(rowIndex);
            if (row == null) {
                return null;
            }
            switch (columnIndex) {
                case 0:
                    return row.getHostName();
                case 1:
                    return row.getDomainName();
            }
        }
        return null;
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        HostEntry item = (HostEntry) data.get(row);
        /*switch (col){
         case 0: item.setIpAddress(value.toString());
         case 1: item.setCommonName(value.toString());
         case 2: item.setDomain(value.toString());
         case 3: item.setKeep((Boolean)value);
         }*/
        fireTableCellUpdated(row, col);
    }

    @Override
    public void addRow(Object item) {
        data.add((HostEntry) item);
        fireTableDataChanged();
    }

    @Override
    public void addRows(ArrayList<Object> items) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void loadData() {
        data.clear();
        List<HostEntry> hostList = DataStore.getHostDataForFootprint(Globals.getCurrentFootprintId());
        if (hostList != null && !hostList.isEmpty()) {
            data.addAll(hostList);
            fireTableDataChanged();
        }
    }

    public HostEntry getItemAt(int index) {
        if (index >= 0 && index < data.size()) {
            return (HostEntry) data.get(index);
        }
        return null;
    }
}
