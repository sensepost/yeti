package com.sensepost.yeti.models;

import com.maxmind.geoip.LookupService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.sensepost.yeti.common.ConfigSettings;
import com.sensepost.yeti.persistence.DataStore;
import java.util.List;

public class IpModel extends BaseModel {

    private LookupService countryLookup = null;

    public IpModel() {
        super();
        setColumnNames(new String[]{"Ip Address", "Netblock", "Country"});
        setTypes(new Class[]{
            java.lang.String.class,
            java.lang.String.class,
            java.lang.String.class,});
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (countryLookup == null) {
            try {
                countryLookup = new LookupService(ConfigSettings.getGeoIPCountryFile(),
                        LookupService.GEOIP_MEMORY_CACHE);
            } catch (IOException ex) {
                Logger.getLogger(IpModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (rowIndex < getRowCount()) {
            String rowStr = (String) data.get(rowIndex);
            if (rowStr == null) {
                return null;
            }
            switch (columnIndex) {
                case 0:
                    return rowStr;
                case 1:
                    return "<n/a>";
                case 2:
                    if (countryLookup != null) {
                        return countryLookup.getCountry(rowStr).getName();
                    } else {
                        return "<n/a>";
                    }
            }
        }
        return null;
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        fireTableCellUpdated(row, col);
    }

    @Override
    public void addRow(Object item) {
        data.add((String) item);
        fireTableDataChanged();
    }

    @Override
    public void addRows(ArrayList<Object> items) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void loadData() {
        data.clear();
        List<String> list = DataStore.getAllIPSForCurrentFootprint();
        if (list != null && !list.isEmpty()) {
            data.addAll(list);
            fireTableDataChanged();
        }
    }

    public String getItemAt(int index) {
        if (index >= 0 && index < data.size()) {
            return (String) data.get(index);
        }
        return null;
    }
}
