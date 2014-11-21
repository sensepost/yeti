package com.sensepost.yeti.models;

import java.util.ArrayList;

/**
 *
 * @author willemmouton
 */
public interface DataModelIFace {

    void addRow(Object item);

    void addRows(ArrayList<Object> items);

    int getColumnCount();

    String getColumnName(int col);

    int getRowCount();

    Object getValueAt(int rowIndex, int columnIndex);

}
