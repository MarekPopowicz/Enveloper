import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;


public class AddresseeTableEditForm extends JDialog {

    private JTable addresseeTable;
    private AddresseeTableModel addresseeTableModel;
    protected static AddresseeTableListener addresseeTableListener;
    private JPanel dialogPanel;
    private final Addressee invoker;

    public AddresseeTableEditForm(Addressee invoker) {
        this.invoker = invoker;

        setTitle("Baza Adresatów");
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
            dialogPanel.add(createButtonPanel(),BorderLayout.SOUTH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        add(dialogPanel);
    }

    private void updateListModel(){
        DefaultListModel model = ((DefaultListModel) invoker.getList().getModel());
        model.clear();
        List<AddresseeModel> addresseeList = new  AddresseeDAO().getAddresseeList();
        invoker.setAddresseeList(addresseeList);
    }

    private JScrollPane createTablePanel(){

        addresseeTableModel = new AddresseeTableModel();
        addresseeTableModel.setData(new AddresseeDAO().getAddresseeList());
        addresseeTable = new JTable(addresseeTableModel);
        addresseeTable.setPreferredSize(new Dimension(470, 470));
        addresseeTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        addresseeTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        addresseeTable.getColumnModel().getColumn(2).setPreferredWidth(150);
        addresseeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        this.addresseeTableListener = new AddresseeTableListener() {

            @Override
            public void rowDeleted(int rowIndex) {

                long addresseeID = (long) addresseeTableModel.getValueAt(rowIndex,0);

                if(new AddresseeDAO().deleteAddressee(addresseeID)) {
                    String message = "Adresat został trwale usunięty.";
                    JOptionPane.showMessageDialog(rootPane, message,"Informacja", JOptionPane.INFORMATION_MESSAGE);
                    addresseeTableModel.removeAddressee(rowIndex);
                    addresseeTableModel.fireTableRowsDeleted(rowIndex, rowIndex);

                    updateListModel();

                }
                else {
                    String message = "Operacja usunięcia adresata nie powiodła się.";
                    JOptionPane.showMessageDialog(rootPane, message,"Informacja", JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void rowAdded(AddresseeModel addressee) {
                    addresseeTableModel.addAddressee(addressee);
                    addresseeTableModel.fireTableDataChanged();
                    updateListModel();
                }


            @Override
            public void rowUpdated(int rowIndex, AddresseeModel addressee) {
                    addresseeTableModel.updateAddressee(rowIndex, addressee);
                    addresseeTableModel.fireTableDataChanged();
                    updateListModel();
                }
        };

        if(addresseeTable.getRowCount()>0) addresseeTable.setRowSelectionInterval(0,0);
        JScrollPane scrollPane = new JScrollPane(addresseeTable);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEtchedBorder());

        return scrollPane;
    }

    private JPanel createButtonPanel() throws IOException {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        setPanelBorder(panel);
        Icon addIcon = new ImageIcon(ImageIO.read(getClass().getResource("add.png")));
        panel.add(createButton("Nowy", new NewHandler(), addIcon));
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

    private class NewHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new AddresseeEditForm(-1,null);
        }
    }

    private class EditHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(addresseeTableModel.getRowCount()>0 && addresseeTable.getSelectedRow()!=-1){
                 long addresseeID = (long) addresseeTableModel.getValueAt(addresseeTable.getSelectedRow(),0);
                 new AddresseeEditForm(addresseeTable.getSelectedRow(), new AddresseeDAO().getAddresseeById(addresseeID));
            }
            else {
                String message = "Brak danych adresata do wyedytowania\nlub nie wskazano konkretnego wiersza tabeli. ";
                JOptionPane.showMessageDialog(rootPane, message,"Informacja", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private class DeleteHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            if(addresseeTable.getSelectedRow()==-1) {
                String message = "Brak danych adresata do usunięcia. ";
                JOptionPane.showMessageDialog(rootPane, message,"Informacja", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            addresseeTableListener.rowDeleted(addresseeTable.getSelectedRow());
            if(addresseeTableModel.getRowCount()>0)
                addresseeTable.setRowSelectionInterval(0,0);
            }
        }
}

