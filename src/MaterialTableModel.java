/**
 * Created by Talicska on 2014.07.13..
 */

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import java.util.Vector;


public class MaterialTableModel extends AbstractTableModel {

    protected Vector<String> columnNames;
    protected Vector<Material> dataVector= new Vector();

    public MaterialTableModel(final Vector<Material> dataVector, Vector<String> columnNames) {
        this.columnNames = columnNames;
        this.dataVector = dataVector;
        this.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int row = e.getFirstRow();
                int column = e.getColumn();
                System.out.println("Value changed!");
                System.out.println(PPC.calcObj.getEtalonObj().getEtalonMatrix().get(row).get(column));

            }
        });
    }

    @Override
    public String getColumnName(int columnIndex){
        return columnNames.get(columnIndex);
    }

    @Override
    public int getRowCount() {
        return dataVector.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Class getColumnClass(int column) {
        switch (column){
            case 0: return String.class;
            case 1: return Double.class;
        }
        return null;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Material mat = dataVector.get(rowIndex);
        switch (columnIndex) {
            case 0: return mat.getName();
            case 1: return mat.getPrice();
        }
        return null;
    }


}
