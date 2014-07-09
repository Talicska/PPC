/**
 * Created by Talicska on 2014.07.09..
 */
import java.util.Collection;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;

public class InteractiveTableModel extends AbstractTableModel {
    /*public static final int TITLE_INDEX = 0;
    public static final int ARTIST_INDEX = 1;
    public static final int ALBUM_INDEX = 2;
    public static final int HIDDEN_INDEX = 3;*/

    protected Vector<String> columnNames;
    protected Vector<Vector<Double>> dataVector;

    public InteractiveTableModel(Vector<Vector<Double>> dataVector,Vector<String> columnNames) {
        this.columnNames = columnNames;
        this.dataVector = dataVector;
    }

    public String getColumnName(int column) {
        return columnNames.get(column);
    }

    public boolean isCellEditable(int row, int column) {
        return true;
    }

    public Class getColumnClass(int column) {
        return Double.class;
    }

    public Object getValueAt(int row, int column) {
        Vector<Double> record = dataVector.get(row);
        return record.get(column);
        /*switch (column) {
            case 0:
                return record.getAmount();
            case 1:
                return record.getP0();
            case 2:
                return record.getP1();
            case 3:
                return record.getP2();
            case 4:
                return record.getP3();
            case 5:
                return record.getP4();
            case 6:
                return record.getP5();
            case 7:
                return record.getP6();
            case 8:
                return record.getP7();
            default:
                return new Object();
        }*/
    }

    public void setValueAt(double value, int row, int column) {
        Vector<Double> record = dataVector.get(row);
        record.set(column,value);
        /*switch (column) {
            case 0:
                record.setAmount((int)value);
                break;
            case 1:
                record.setP0(value);
                break;
            case 2:
                record.setP1(value);
                break;
            case 3:
                record.setP2(value);
                break;
            case 4:
                record.setP3(value);
                break;
            case 5:
                record.setP4(value);
                break;
            case 6:
                record.setP5(value);
                break;
            case 7:
                record.setP6(value);
                break;
            case 8:
                record.setP7(value);
                break;
            default:
                System.out.println("invalid index");
        }*/
        fireTableCellUpdated(row, column);
    }

    public int getRowCount() {
        return dataVector.size();
    }

    public int getColumnCount() {
        return columnNames.size();
    }

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
