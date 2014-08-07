/**
 * Created by Talicska on 2014.07.31..
 */

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import java.util.Vector;

public class DyeTableModel extends AbstractTableModel {

    protected Vector<String> columnNames;
    protected Vector<DyeParent> dataVector= new Vector<DyeParent>();

    public DyeTableModel(final Vector<DyeParent> dataVector, Vector<String> columnNames) {
        this.columnNames = columnNames;
        this.dataVector = dataVector;
        this.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                //int row = e.getFirstRow();
                //int column = e.getColumn();
                //System.out.println("Value changed!");
                //System.out.println(PPC.calcObj.getAllDyeTypes().get(row).getName()+" "+
                //PPC.calcObj.getAllDyeTypes().get(row).getPrice());

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
        DyeParent mat = dataVector.get(rowIndex);
        switch (columnIndex) {
            case 0: return mat.getName();
            case 1: return mat.getPrice();
        }
        return null;
    }

    @Override
    public void setValueAt(Object value, int row, int col){
        switch (col) {
            case 0: dataVector.get(row).setName(value.toString());
                break;
            case 1: dataVector.get(row).setPrice(Double.parseDouble(value.toString()));
                break;
            default: break;
        }
        fireTableCellUpdated(row,col);
        PPCDB.refreshDyeParents(PPC.calcObj.getAllDyeTypes());
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return true;
    }

    public Vector<DyeParent> getDataVector(){
        return dataVector;
    }

}