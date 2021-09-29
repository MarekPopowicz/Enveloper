import javax.swing.table.AbstractTableModel;
import java.util.Collections;
import java.util.List;

public class CaseTableModel extends AbstractTableModel {
    private List<CaseModel> caseModels;
    private String[] colNames = {"ID", "Nr Sprawy", "Nr Kancelaryjny", "Adresat"};

    public CaseTableModel() {

    }

    public void setData(List<CaseModel> caseList){
        this.caseModels = caseList;
    }

    protected void removeCase(int rowIndex){
        this.caseModels.remove(rowIndex);
    }
    protected void addCase(CaseModel caseModel){
        this.caseModels.add(caseModel);
    }

    protected void updateCase(int rowIndex, CaseModel caseModel){
        this.caseModels.remove(rowIndex);
        this.caseModels.add(caseModel);
        Collections.sort(caseModels);
    }

    @Override
    public int getRowCount() {
        return caseModels.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public String getColumnName(int column) {
        return colNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if(!caseModels.isEmpty()) {
            CaseModel caseModel = caseModels.get(rowIndex);

            switch (columnIndex) {
                case 0:
                    return caseModel.getId();
                case 1:
                    return caseModel.getCaseNo();
                case 2:
                    return caseModel.getSODNo();
                case 3:
                    AddresseeModel addresseeModel = new AddresseeDAO().getAddresseeById(Long.parseLong(caseModel.getAddresse()));
                    return addresseeModel.toString();
            }
        }
        return null;
    }

}
