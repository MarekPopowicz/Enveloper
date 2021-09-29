
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class AddresseeEditForm extends JDialog {

    private final JTextField txtFirstname = new JTextField();
    private final JTextField txtLastname = new JTextField();
    private JTextField txtAddress = new JTextField();
    private JTextField txtZipcode = new JTextField();
    private JTextField txtPost = new JTextField();
    private AddresseeModel addresseeModel;
    private int rowIndex;

    public AddresseeEditForm(int rowIndex, AddresseeModel addresseeModel) {
        this.addresseeModel = addresseeModel;
        this.rowIndex = rowIndex;
        setTitle("Adresat");
        setPreferredSize(new Dimension(500, 330));
        setResizable(false);
        setModal(true);
        setAlwaysOnTop(true);
        createView();
        setData();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createView() {
        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.add(createAddresseePanel());
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        try {
            panel.add(createButtonPanel());
        } catch (IOException e) {
            e.printStackTrace();
        }
        add(panel);
    }

    protected void setData() {
        if (addresseeModel != null) {
            txtFirstname.setText(addresseeModel.getFirstname());
            txtLastname.setText(addresseeModel.getLastname());
            txtAddress.setText(addresseeModel.getAddress());
            txtZipcode.setText(addresseeModel.getZipcode());
            txtPost.setText(addresseeModel.getPost());
        }
    }

    private JPanel createButtonPanel() throws IOException {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        setPanelBorder(panel);
        Icon icon = new ImageIcon(ImageIO.read(getClass().getResource("save.png")));
        panel.add(createButton("Zapisz", new OkHandler(), icon));
        return panel;
    }

    private JButton createButton(String text, ActionListener listener, Icon icon) {
        JButton cmd = new JButton(text, icon);
        cmd.setPreferredSize(new Dimension(140, 50));
        cmd.addActionListener(listener);
        return cmd;
    }

    private JPanel createLine(String text, JComponent component) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(text);
        label.setPreferredSize(new Dimension(100, 25));
        panel.add(label, BorderLayout.WEST);
        panel.add(component);
        return panel;
    }

    private void setPanelBorder(JPanel panel) {
        Border empty = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        Border etched = BorderFactory.createEtchedBorder();
        panel.setBorder(BorderFactory.createCompoundBorder(etched, empty));
    }

    private JPanel createAddresseePanel() {
        JPanel panel = new JPanel(new GridLayout(5, 1, 5, 10));
        setPanelBorder(panel);
        panel.add(createLine("Nazwa", txtFirstname));
        panel.add(createLine("Wydział", txtLastname));
        panel.add(createLine("Adres", txtAddress));
        panel.add(createLine("Kod Pocztowy", txtZipcode));
        panel.add(createLine("Poczta", txtPost));

        return panel;
    }

    private boolean validateData() { //Sprawdzenie wymagalności danych

        if (txtFirstname.getText().equals("")) return false;
        if (txtAddress.getText().equals("")) return false;
        if (txtZipcode.getText().equals("")) return false;
        if (txtPost.getText().equals("")) return false;

        return true;
    }

    private void setAddresseeModel() {
        addresseeModel.setFirstname(txtFirstname.getText());
        addresseeModel.setLastname(txtLastname.getText());
        addresseeModel.setAddress(txtAddress.getText());
        addresseeModel.setZipcode(txtZipcode.getText());
        addresseeModel.setPost(txtPost.getText());
    }

    private class OkHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (!validateData()) {
                String message = "Nie wszystkie wymagane pola zostały poprawnie wypełnione.";
                JOptionPane.showMessageDialog(rootPane, message, "Informacja", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            AddresseeEditForm.this.dispose();

            if (validateData()) {
                if (addresseeModel == null) { //Nowy adresat
                    addresseeModel = new AddresseeModel();
                    setAddresseeModel();
                    long id = new AddresseeDAO().getLastId();
                    addresseeModel.setId(id + 1);

                    if (new AddresseeDAO().insertAddressee(addresseeModel)) {

                        String message = "Nowy adresat został zapisany.";
                        JOptionPane.showMessageDialog(rootPane, message, "Informacja", JOptionPane.INFORMATION_MESSAGE);
                        AddresseeTableEditForm.addresseeTableListener.rowAdded(addresseeModel);
                    } else {
                        AddresseeEditForm.this.dispose();
                        String message = "Operacja zapisu nowego adresata nie powiodła się.";
                        JOptionPane.showMessageDialog(rootPane, message, "Informacja", JOptionPane.ERROR_MESSAGE);
                    }
                }

                else { //Aktualizacja danych istniejącego adresata
                    setAddresseeModel();
                    if (new AddresseeDAO().updateAddressee(addresseeModel)) {
                        String message = "Dane adresata zostały zapisane.";
                        JOptionPane.showMessageDialog(rootPane, message, "Informacja", JOptionPane.INFORMATION_MESSAGE);
                        AddresseeTableEditForm.addresseeTableListener.rowUpdated(rowIndex, addresseeModel);
                    } else {
                        String message = "Operacja aktualizacji danych adresata nie powiodła się.";
                        JOptionPane.showMessageDialog(rootPane, message, "Informacja", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }
}
