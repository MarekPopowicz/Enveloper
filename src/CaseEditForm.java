
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class CaseEditForm extends JDialog {

    private final JTextField txtCaseNo = new JTextField();
    private final JTextField txtSODNo = new JTextField();
    private JTextField txtAddresseeFirstName = new JTextField();
    private JTextField txtAddresseeLastName = new JTextField();
    private CaseModel caseModel;
    private int rowIndex;

    public CaseEditForm(int rowIndex, CaseModel caseModel) {
        this.caseModel = caseModel;
        this.rowIndex = rowIndex;
        setTitle("Wysyłka");
        setPreferredSize(new Dimension(550, 310));
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
        if (caseModel != null) {
            txtCaseNo.setText(caseModel.getCaseNo());
            txtSODNo.setText(caseModel.getSODNo());
            AddresseeModel addressee = new AddresseeDAO().getAddresseeById(Long.parseLong(caseModel.getAddresse()));
            txtAddresseeFirstName.setText(addressee.getFirstname());
            txtAddresseeLastName.setText(addressee.getLastname());
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
        JPanel panel = new JPanel(new GridLayout(4, 1, 5, 10));
        setPanelBorder(panel);
        panel.add(createLine("Nr Sprawy", txtCaseNo));
        panel.add(createLine("Nr Kancelaryjny", txtSODNo));
        txtAddresseeFirstName.setEditable(false);
        txtAddresseeLastName.setEditable(false);
        panel.add(createLine("Adresat", txtAddresseeFirstName));
        panel.add(createLine("", txtAddresseeLastName));
        return panel;
    }

    private boolean validateData() { //Sprawdzenie wymagalności danych

        if (txtCaseNo.getText().equals("")) return false;
        if (txtSODNo.getText().equals("")) return false;

        return true;
    }

    private void setCaseModel() {
        caseModel.setCaseNo(txtCaseNo.getText());
        caseModel.setSODNo(txtSODNo.getText());
    }

    private class OkHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (!validateData()) {
                String message = "Nie wszystkie wymagane pola zostały poprawnie wypełnione.";
                JOptionPane.showMessageDialog(rootPane, message, "Informacja", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            CaseEditForm.this.dispose();

            if (validateData()) {
                    setCaseModel();
                    if (new CaseDAO().updateCase(caseModel)) {
                        String message = "Dane wysyłki zostały zapisane.";
                        JOptionPane.showMessageDialog(rootPane, message, "Informacja", JOptionPane.INFORMATION_MESSAGE);
                        CaseTableEditForm.caseTableListener.rowUpdated(rowIndex, caseModel);
                    } else {
                        String message = "Operacja aktualizacji danych wysyłki nie powiodła się.";
                        JOptionPane.showMessageDialog(rootPane, message, "Informacja", JOptionPane.ERROR_MESSAGE);
                    }
            }
        }
    }
}
