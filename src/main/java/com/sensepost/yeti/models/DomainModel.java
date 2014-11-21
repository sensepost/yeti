package com.sensepost.yeti.models;

import java.util.ArrayList;
import java.util.List;
import com.sensepost.yeti.common.Globals;
import com.sensepost.yeti.persistence.DataStore;
import com.sensepost.yeti.results.DomainEntry;

/**
 *
 * @author willemmouton
 */
public class DomainModel extends BaseModel {

    public DomainModel() {
        super();
        setColumnNames(new String[]{"Domain name", "NS server", "Admin", "No. Attributes"});
        setTypes(new Class[]{
            java.lang.String.class,
            java.lang.String.class,
            java.lang.String.class,
            java.lang.Integer.class,
            java.lang.String.class,});
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (data.size() > rowIndex) {
            DomainEntry row = (DomainEntry) data.get(rowIndex);
            if (row == null) {
                return null;
            }
            switch (columnIndex) {
                case 0:
                    return row.getDomainName();
                case 1:
                    if (row.getNS() == null) {
                        return "";
                    }
                    return row.getNS();
                case 2:
                    if (row.getAdmin() == null) {
                        return "";
                    }
                    return row.getAdmin();
                case 3:
                    return row.getAttributeCount();

            }
        }
        return null;
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        DomainEntry item = (DomainEntry) data.get(row);
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
        data.add((DomainEntry) item);
        fireTableDataChanged();
    }

    @Override
    public void addRows(ArrayList<Object> items) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void loadData() {
        data.clear();
        List<DomainEntry> domainList = DataStore.getDomainData(Globals.getCurrentFootprintId());
        if (domainList != null && !domainList.isEmpty()) {
            data.addAll(domainList);
            fireTableDataChanged();
        }
    }

    public DomainEntry getItemAt(int index) {
        if (index >= 0 && index < data.size()) {
            return (DomainEntry) data.get(index);
        }
        return null;
    }
}
