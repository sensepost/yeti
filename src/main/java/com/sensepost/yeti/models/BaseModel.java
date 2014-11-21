package com.sensepost.yeti.models;

import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author willemmouton
 */
public abstract class BaseModel extends DefaultTableModel implements DataModelIFace {

    String[] columnNames = null;
    Class[] types = null;

    protected ArrayList<Object> data = new ArrayList<>();

    public BaseModel() {
        if (data == null) {
            this.data = new ArrayList<>();
        }
    }

    public void setColumnNames(String[] colNames) {
        this.columnNames = colNames;
    }

    public void setTypes(Class[] types) {
        this.types = types;
    }

    @Override
    public int getRowCount() {
        if (this.data == null) {
            return 0;
        }
        return this.data.size();
    }

    @Override
    public int getColumnCount() {
        return this.columnNames.length;
    }

    public void clearData() {
        this.data.clear();
        fireTableDataChanged();
    }

    @Override
    public abstract void addRow(Object item);

    @Override
    public abstract void addRows(ArrayList<Object> items);

    public void deleteRow(int row) {
        this.data.remove(row);
        fireTableRowsDeleted(row, row);
    }

    @Override
    public Class getColumnClass(int c) {
        if (getValueAt(0, c) != null) {
            return getValueAt(0, c).getClass();
        }

        return null;
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    public ArrayList<Object> getData() {
        return this.data;
    }

    @Override
    public abstract Object getValueAt(int rowIndex, int columnIndex);

    @Override
    public abstract void setValueAt(Object value, int row, int col);

    public Object valueAt(int rowIndex) {
        return this.data.get(rowIndex);
    }
}
