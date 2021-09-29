import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class SearchForm extends JDialog {


    private JTextField searchText = new JTextField(30);
    private ButtonGroup radioButtonsGroup;
    private SearchCaseTableListener searchCaseTableListener;

    public SearchForm(SearchCaseTableListener searchCaseTableListener) throws IOException {
        this.searchCaseTableListener = searchCaseTableListener;
        setLayout(new BorderLayout());
        setTitle("Znajdź wysyłkę");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        add(createView());
        setModal(true);
        setResizable(false);
        pack();
        setAlwaysOnTop(true);
        setLocationRelativeTo(rootPane);
        searchText.requestFocus();
        setVisible(true);


    }
    private JPanel createLine(String text, JTextField component) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(text);
        label.setPreferredSize(new Dimension(50, 25));
        panel.add(label, BorderLayout.WEST);
        panel.add(component);
        return panel;
    }

    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        setPanelBorder(panel);
        panel.add(createLine("Szukaj:", searchText));

        return panel;
    }

    private JPanel createRadioButtonPannel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        setPanelBorder(panel);

        JRadioButton caseButton = new JRadioButton("Nr Sprawy");
        caseButton.setActionCommand(AddresseeDB.COLUMN_CASE_NUMBER);
        caseButton.addActionListener(new CaseRadioButtonListener());

        JRadioButton SODButton = new JRadioButton("Nr Kancelaryjny");
        SODButton.setActionCommand(AddresseeDB.COLUMN_CASE_SOD_NUMBER);
        SODButton.addActionListener(new SODradioButtonListener());

        JRadioButton IdButton = new JRadioButton("ID");
        IdButton.setActionCommand(AddresseeDB.COLUMN_CASE_ID);
        IdButton.addActionListener(new IDradioButtonListener());


        radioButtonsGroup = new ButtonGroup();
        radioButtonsGroup.add(caseButton);
        radioButtonsGroup.add(SODButton);
        radioButtonsGroup.add(IdButton);
        panel.add(IdButton);
        panel.add(caseButton);
        panel.add(SODButton);
        caseButton.setSelected(true);

       return panel;
    }
    private void setPanelBorder(JPanel panel){
        Border empty  = BorderFactory.createEmptyBorder(10,10,10,10);
        Border etched = BorderFactory.createEtchedBorder();
        panel.setBorder(BorderFactory.createCompoundBorder(etched,empty));
    }


    private JPanel createView() throws IOException {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.add(createRadioButtonPannel(), BorderLayout.NORTH);
        panel.add(createSearchPanel(), BorderLayout.CENTER);
        panel.add(createButtonPanel(), BorderLayout.SOUTH);
        return panel;
    }

    private JButton createButton(String text, ActionListener listener, Icon icon) {
        JButton cmd = new JButton(text, icon);
        cmd.setPreferredSize(new Dimension(140, 50));
        cmd.addActionListener(listener);
        return cmd;
    }


    private JPanel createButtonPanel() throws IOException {

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        setPanelBorder(panel);
        Icon searchIcon = new ImageIcon(ImageIO.read(getClass().getResource("search.png")));
        panel.add(createButton("Znajdź", new buttonActionListener(), searchIcon));

        return panel;
    }

    private class buttonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String textToFind = searchText.getText();

            if(!searchCaseTableListener.searchCaseTable(radioButtonsGroup.getSelection().getActionCommand(),textToFind)){
                String message = "Brak danych spełniających kryterium wyboru. ";
                JOptionPane.showMessageDialog(SearchForm.this, message,"Informacja", JOptionPane.INFORMATION_MESSAGE);
            }

            clearSearchText();
        }
    }

    private void clearSearchText(){
        searchText.setText("");
        searchText.requestFocus();
    }


    private class CaseRadioButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            clearSearchText();
        }
    }

    private class SODradioButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            clearSearchText();
        }
    }

    private class IDradioButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            clearSearchText();
        }
    }
}
