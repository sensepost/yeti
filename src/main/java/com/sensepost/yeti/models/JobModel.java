package com.sensepost.yeti.models;

import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import com.sensepost.yeti.controllers.RunningJob;

/**
 *
 * @author willemmouton
 */
public class JobModel extends DefaultTableModel {

    protected String[] columnNames = null;
    protected Class[] types = null;
    protected ArrayList<Object> data = new ArrayList<>();

    public JobModel() {
        this.columnNames = (new String[]{"Id", "Type", "Progress"});

        this.types = new Class[]{
            java.lang.Integer.class,
            java.lang.String.class,
            java.lang.String.class,};
    }

    public void setTypes(Class[] types) {
        this.types = types;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        RunningJob row = (RunningJob) data.get(rowIndex);
        if (row == null) {
            return null;
        }
        switch (columnIndex) {
            case 0:
                return row.getId();
            case 1:
                return row.getType();
            case 2:
                return row.getProgress();
            default:
                return null;
        }
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        if (data != null && row >= 0 && col >= 0) {
            RunningJob item = (RunningJob) data.get(row);
            switch (col) {
                case 2:
                    item.setProgress(value.toString());
            }
            fireTableCellUpdated(row, col);
        }
    }

    public RunningJob getObjectAt(int index) {
        return (RunningJob) data.get(index);
    }

    public int getIndexOf(Object item) {
        return data.indexOf(item);
    }

    public void addRow(Object item) {
        data.add(item);
        fireTableDataChanged();
    }

    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public int getColumnCount() {
        return this.columnNames.length;
    }

    @Override
    public int getRowCount() {
        if (data == null) {
            return 0;
        }
        return data.size();
    }

    public void clear() {
        data.clear();
        fireTableDataChanged();
    }

    public void removeItem(Object item) {
        data.remove(item);
        fireTableDataChanged();
    }

    public ArrayList<Object> getData() {
        return data;
    }
}
