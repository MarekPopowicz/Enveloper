import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;


public class CaseTableEditForm extends JDialog {

    private JPanel dialogPanel;
    private JTable caseTable;
    private CaseTableModel caseTableModel;
    protected static CaseTableListener caseTableListener;

    public CaseTableEditForm() {
        setTitle("Rejestr Wysyłek");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        createView();
        setModal(true);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createView()
    {
        Dimension mySize = new Dimension(800, 625);
        setMinimumSize(mySize);
        setPreferredSize(mySize);
        setMaximumSize(mySize);
        dialogPanel = new JPanel();
        dialogPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        dialogPanel.setLayout(new BorderLayout());
        dialogPanel.add(Box.createRigidArea(new Dimension(0,10)));
        dialogPanel.add(createTablePanel(),BorderLayout.CENTER);
        try {
            dialogPanel.add(createButtonPanel(), BorderLayout.SOUTH);
        } catch (IOException e) {
            e.printStackTrace();
        }

        add(dialogPanel);
    }

    private JScrollPane createTablePanel(){

        caseTableModel = new CaseTableModel();
        caseTableModel.setData(new CaseDAO().getCaseList());
        caseTable = new JTable(caseTableModel);
        caseTable.setPreferredSize(new Dimension(790, 470));
        caseTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        caseTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        caseTable.getColumnModel().getColumn(2).setPreferredWidth(150);
        caseTable.getColumnModel().getColumn(3).setPreferredWidth(450);
        caseTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        this.caseTableListener = new CaseTableListener() {

            @Override
            public void rowDeleted(int rowIndex) {

                long caseID = (long) caseTableModel.getValueAt(rowIndex,0);

                if(new CaseDAO().deleteCase(caseID)) {
                    String message = "Wysyłka została trwale usunięta.";
                    JOptionPane.showMessageDialog(rootPane, message,"Informacja", JOptionPane.INFORMATION_MESSAGE);
                    caseTableModel.removeCase(rowIndex);
                    caseTableModel.fireTableRowsDeleted(rowIndex, rowIndex);

                }
                else {
                    String message = "Operacja usunięcia wysyłki nie powiodła się.";
                    JOptionPane.showMessageDialog(rootPane, message,"Informacja", JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void rowAdded(CaseModel caseModel) {
                caseTableModel.addCase(caseModel);
                caseTableModel.fireTableDataChanged();

            }


            @Override
            public void rowUpdated(int rowIndex, CaseModel caseModel) {
                caseTableModel.updateCase(rowIndex, caseModel);
                caseTableModel.fireTableDataChanged();
            }
        };

        if(caseTable.getRowCount()>0) caseTable.setRowSelectionInterval(0,0);
        JScrollPane scrollPane = new JScrollPane(caseTable);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEtchedBorder());

        return scrollPane;
    }


    private JPanel createButtonPanel() throws IOException {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        setPanelBorder(panel);
        Icon searcgIcon = new ImageIcon(ImageIO.read(getClass().getResource("search.png")));
        panel.add(createButton("Znajdź", new SearchHandler(), searcgIcon));
        Icon editIcon = new ImageIcon(ImageIO.read(getClass().getResource("edit.png")));
        panel.add(createButton("Edytuj", new EditHandler(), editIcon));
        Icon deleteIcon = new ImageIcon(ImageIO.read(getClass().getResource("delete.png")));
        panel.add(createButton("Usuń", new DeleteHandler(), deleteIcon));
        return panel;
    }

    private JButton createButton(String text, ActionListener listener, Icon icon)
    {
        JButton cmd = new JButton(text, icon);
        cmd.setPreferredSize(new Dimension(140, 50));
        cmd.addActionListener(listener);
        return cmd;
    }

    private void setPanelBorder(JPanel panel){
        Border empty  = BorderFactory.createEmptyBorder(10,10,10,10);
        Border etched = BorderFactory.createEtchedBorder();
        panel.setBorder(BorderFactory.createCompoundBorder(etched,empty));
    }

    private class SearchHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                new SearchForm(new SearchCaseTableListener() {
                    @Override
                    public boolean searchCaseTable(String columnName, String textToFind) {
                        CaseDAO caseDAO = new CaseDAO();
                        String query = caseDAO.sqlQuerySelectAll +
                                " WHERE " + columnName + " LIKE  '%" + textToFind + "%'";

                        List<CaseModel> caseModels  = caseDAO.getFilteredCaseList(query);

                        if(caseModels==null || caseModels.isEmpty()){
                            return false;
                        }

                        caseTableModel.setData(caseModels);
                        caseTableModel.fireTableDataChanged();
                        return true;
                    }
                });

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    private class EditHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(caseTableModel.getRowCount()>0 && caseTable.getSelectedRow()!=-1){
                long caseID = (long) caseTableModel.getValueAt(caseTable.getSelectedRow(),0);
                new CaseEditForm(caseTable.getSelectedRow(), new CaseDAO().getCaseById(caseID));
            }
            else {
                String message = "Brak danych wysyłki do wyedytowania\nlub nie wskazano konkretnego wiersza tabeli.";
                JOptionPane.showMessageDialog(rootPane, message,"Informacja", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private class DeleteHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            if(caseTable.getSelectedRow()==-1) {
                String message = "Brak danych wysyłki do usunięcia. ";
                JOptionPane.showMessageDialog(rootPane, message,"Informacja", JOptionPane.INFORMATION_MESSAGE);
                return;
            }


            caseTableListener.rowDeleted(caseTable.getSelectedRow());
            if(caseTableModel.getRowCount()>0)
                caseTable.setRowSelectionInterval(0,0);
        }
    }

}
