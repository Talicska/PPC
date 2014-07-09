/**
 * Created by Talicska on 2014.07.09..
 */

import java.util.Vector;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class InteractiveTableModel extends DefaultTableModel {

    protected Vector<String> columnNames;
    protected Vector<Vector<Double>> dataVector;

    public InteractiveTableModel(final Vector<Vector<Double>> dataVector,Vector<String> columnNames) {
        super(dataVector,columnNames);
        this.columnNames = columnNames;
        this.dataVector = dataVector;
        this.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int row = e.getFirstRow();
                int column = e.getColumn();
                //PPC.getEtalonObj().setElement(dataVector.get(row).get(column),row,column);

                System.out.println("Value changed!");
                System.out.println(PPC.getEtalonObj().getEtalonMatrix().get(row).get(column));

            }
        });
    }

    /*public String getColumnName(int column) {
        return columnNames.get(column);
    }*/

    /*public boolean isCellEditable(int row, int column) {
        return true;
    }*/

   /* public Class getColumnClass(int column) {
        return Double.class;
    }*/

    /*public Object getValueAt(int row, int column) {
        return dataVector.get(row).get(column);
    }*/

    /*public void setValueAt(double value, int row, int column) {
        dataVector.get(row).set(column, value);
        fireTableCellUpdated(row, column);
    }*/

    /*public int getRowCount() {
        return dataVector.size();
    }

    public int getColumnCount() {
        return columnNames.size();
    }*/

    public boolean hasEmptyRow() {
        /*if (dataVector.size() == 0) return false;
        AudioRecord audioRecord = (AudioRecord)dataVector.get(dataVector.size() - 1);
        if (audioRecord.getTitle().trim().equals("") &&
                audioRecord.getArtist().trim().equals("") &&
                audioRecord.getAlbum().trim().equals(""))
        {
            return true;
        }
        else */return false;
    }

    /*public void addEmptyRow() {
        dataVector.add(new AudioRecord());
        fireTableRowsInserted(
                dataVector.size() - 1,
                dataVector.size() - 1);
    }*/
}
