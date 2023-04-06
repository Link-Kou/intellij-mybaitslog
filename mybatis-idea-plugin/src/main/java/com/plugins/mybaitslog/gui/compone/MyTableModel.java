package com.plugins.mybaitslog.gui.compone;

import javax.swing.table.DefaultTableModel;

/**
 * A <code>MyTableModel</code> Class
 *
 * @author lk
 * @version 1.0
 * <p><b>date: 2023/4/6 10:05</b></p>
 */
public class MyTableModel extends DefaultTableModel {


    public MyTableModel() {
        super(new String[]{"Performer", "Enable"}, 0);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        Class clazz = String.class;
        switch (columnIndex) {
            case 0:
                clazz = String.class;
                break;
            case 1:
                clazz = Boolean.class;
                break;
        }
        return clazz;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return column == 1;
    }
}
