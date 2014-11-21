package com.sensepost.yeti.models;

import com.sensepost.yeti.persistence.DataStore;
import java.util.ArrayList;
import java.util.List;
import com.sensepost.yeti.results.FootprintData;

public class FootprintModel extends BaseModel {

    public FootprintModel() {
        setColumnNames(
                new String[]{"Name", "Created"}
        );
        setTypes(new Class[]{java.lang.String.class, java.lang.String.class,});
        loadData();
    }

    public void reload() {
        data.clear();
        loadData();
    }

    private void loadData() {
        List<FootprintData> list = DataStore.getFootprintData();
        if (list != null) {
            for (FootprintData fpd : list) {
                addRow(fpd);
            }
        }
    }

    public void reloadData() {
        loadData();
    }

    @Override
    public void addRow(Object item) {
        data.add((FootprintData) item);
        fireTableDataChanged();
    }

    @Override
    public void addRows(ArrayList<Object> items) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        FootprintData row = (FootprintData) data.get(rowIndex);
        if (row == null) {
            return null;
        }
        switch (columnIndex) {
            case 0:
                return row.getName();
            case 1:
                return row.getCreated();
            default:
                return null;
        }
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }
}
