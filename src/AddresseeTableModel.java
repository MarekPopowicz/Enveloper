
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class AddresseeTableModel extends AbstractTableModel {

    private List<AddresseeModel> addressee;
    private String[] colNames = {"ID", "Nazwa", "Wydział", "Adres", "Kod Pocztowy", "Poczta"};

    public AddresseeTableModel() {

    }


    public void setData(List<AddresseeModel> addressee){
        this.addressee = addressee;
    }

    protected void removeAddressee(int rowIndex){
        this.addressee.remove(rowIndex);
    }
    protected void addAddressee(AddresseeModel addressee){
        this.addressee.add(addressee);
    }

    protected void updateAddressee(int rowIndex, AddresseeModel addressee){
        this.addressee.remove(rowIndex);
        this.addressee.add(addressee);
    }

    @Override
    public int getRowCount() {
        return addressee.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public String getColumnName(int column) {
        return colNames[column];
    }


    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if(!addressee.isEmpty()) {
            AddresseeModel addresseeModel = addressee.get(rowIndex);

            switch (columnIndex) {
                case 0:
                    return addresseeModel.getId();
                case 1:
                    return addresseeModel.getFirstname();
                case 2:
                    return addresseeModel.getLastname();
                case 3:
                    return addresseeModel.getAddress();
                case 4:
                    return addresseeModel.getZipcode();
                case 5:
                    return addresseeModel.getPost();
            }
        }
        return null;
    }


}
