/**
 * Created by Talicska on 2014.07.09..
 */

import java.util.Vector;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public class EtalonTableModel extends DefaultTableModel {

    protected Vector<String> columnNames;
    protected Vector<Vector<Double>> dataVector;

    public EtalonTableModel(final Vector<Vector<Double>> dataVector, Vector<String> columnNames) {
        super(dataVector, columnNames);
        this.columnNames = columnNames;
        this.dataVector = dataVector;
        this.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                /*int row = e.getFirstRow();
                int column = e.getColumn();
                System.out.println("Value changed!");
                System.out.println(PPC.calcObj.getEtalonObj().getEtalonMatrix().get(row).get(column));*/
            }
        });
    }

    @Override
    public Class getColumnClass(int column) {
        if (column == 0)
            return Integer.class;
        return Double.class;
    }

    @Override
    public void setValueAt(Object value, int row, int column) {
        super.setValueAt(value, row, column);

    }

}
