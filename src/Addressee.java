
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.prefs.BackingStoreException;


public class Addressee extends JFrame{

    private final JTextField txtFirstname = new JTextField();
    private final JTextField txtLastname = new JTextField();
    private JTextField txtAddress= new JTextField();
    private JTextField txtZipcode= new JTextField();
    private JTextField txtPost = new JTextField();
    private JTextField txtCaseNo = new JTextField();
    private JList list;
    private DefaultListModel model = new DefaultListModel();


    String pattern = "yyyy-MM-dd";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    String date = simpleDateFormat.format(new Date());

    private JTextField txtSODNo = new JTextField("TD/OWR/OMI/"+date+"/0000001");

    public Addressee() throws IOException {

        super("Enveloper: " + Application.preferences.get("database", ""));

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height;
        int width = screenSize.width;
        setSize(width/2, height/2);

        setPreferredSize(new Dimension(500, 625));
        setResizable(false);
        createView();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        txtCaseNo.requestFocus();
        setVisible(true);
    }

    private void createView() throws IOException {
        setJMenuBar(createMenuBar());
        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setLayout(new BoxLayout(panel,BoxLayout.PAGE_AXIS));
        panel.add(createCasePanel());
        panel.add(Box.createRigidArea(new Dimension(0,5)));
        panel.add(createAddresseePanel());
        panel.add(Box.createRigidArea(new Dimension(0,5)));
        panel.add(createListPanel());
        panel.add(Box.createRigidArea(new Dimension(0,5)));
        panel.add(createButtonPanel());
        add(panel);
    }

    private void setData(AddresseeModel addressee) {
            txtFirstname.setText(addressee.getFirstname());
            txtLastname.setText(addressee.getLastname());
            txtAddress.setText(addressee.getAddress());
            txtZipcode.setText(addressee.getZipcode());
            txtPost.setText(addressee.getPost());
    }

    private JMenuBar createMenuBar()  {

        JMenuBar menuBar = new JMenuBar();
        JMenu settings = new JMenu("Ustawienia");
        JMenu look = new JMenu("Wygląd");

        JMenuItem nimbus = new JMenuItem("Nimbus");
        JMenuItem motif = new JMenuItem("Motif");
        JMenuItem metal = new JMenuItem("Metal");
        JMenuItem windows = new JMenuItem("Windows");

        motif.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LookAndFeel().setMotifLook(Addressee.this);
            }
        });
        metal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LookAndFeel().setMetalLook(Addressee.this);
            }
        });
        windows.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LookAndFeel().setWindowsLook(Addressee.this);
            }
        });
        nimbus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LookAndFeel().setNimbusLook(Addressee.this);
            }
        });

        look.add(nimbus);
        look.add(motif);
        look.add(metal);
        look.add(windows);

        JMenuItem storage = new JMenuItem("Lokalizacja danych");
        JMenuItem about = new JMenuItem("O programie");
        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new AboutForm();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        settings.add(storage);
        settings.add(look);
        settings.add(new JSeparator());
        settings.add(about);


        storage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = new DBFileChooser().getSelectedFile();

                if (file != null) {
                    try {
                        Application.preferences.clear();
                        String path = file.getCanonicalPath();
                        Application.preferences.put("database", path);
                        setTitle("Enveloper: " + path);

                        model.removeAllElements();
                        model = setAddresseeList(new AddresseeDAO().getAddresseeList());
                        if(!model.isEmpty()) list.setSelectedIndex(0);

                    } catch (BackingStoreException | IOException backingStoreException) {
                        backingStoreException.printStackTrace();
                    }
                }
            }

        });

        JMenu addressee = new JMenu("Dane");
        JMenuItem editDB = new JMenuItem("Baza Adresatów");
        JMenuItem editCaseDB = new JMenuItem("Rejestr Wysyłek");
        editCaseDB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CaseTableEditForm();
            }
        });


        editDB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddresseeTableEditForm(Addressee.this);
            }
        });
        addressee.add(editDB);
        addressee.add(editCaseDB);
        menuBar.add(settings);
        menuBar.add(addressee);

        return menuBar;
    }

    protected DefaultListModel setAddresseeList(List<AddresseeModel> addresseeList){

        for (AddresseeModel addressee : addresseeList) {
            this.model.addElement(addressee);
        }
        return model;
    }

    private JScrollPane createListPanel() {
        list = new JList();
        model = setAddresseeList(new AddresseeDAO().getAddresseeList());
        list.setModel(model);
        list.setBorder(BorderFactory.createEtchedBorder());

        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                AddresseeModel addressee = (AddresseeModel) list.getSelectedValue();
                if (model.isEmpty()) clearData();
                else if(addressee!=null)
                    setData(addressee);
                else setData((AddresseeModel) model.firstElement());
            }

        });
        list.setSelectedIndex(0);
        return new JScrollPane(list);
    }

    protected JList getList(){
        return this.list;
    }

    private JPanel createButtonPanel() throws IOException {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        setPanelBorder(panel);
        Icon mailIcon = new ImageIcon(ImageIO.read(getClass().getResource("mail.png")));
        panel.add(createButton("Zaadresuj", new OkHandler(), mailIcon));
        Icon clearIcon = new ImageIcon(ImageIO.read(getClass().getResource("clear.png")));
        panel.add(createButton("Wyczyść", new ClearHandler(), clearIcon));
        Icon logoutIcon = new ImageIcon(ImageIO.read(getClass().getResource("logout.png")));
        panel.add(createButton("Zakończ", new QuiteHandler(), logoutIcon));
        return panel;
    }

    private JButton createButton(String text, ActionListener listener, Icon icon) {
        JButton cmd = new JButton(text, icon);
        cmd.setPreferredSize(new Dimension(140, 50));
        cmd.addActionListener(listener);
        return cmd;
    }

    private JPanel createLine(String text, JTextField component) throws IOException {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        Icon clipboardIcon = new ImageIcon(ImageIO.read(getClass().getResource("clipboard.png")));
        JLabel label = new JLabel(text);
        JButton copyButton = new JButton(clipboardIcon);
        copyButton.addActionListener(new copyButtonListener(component));
        copyButton.setPreferredSize(new Dimension(25, 25));
        component.setPreferredSize(new Dimension(350, 25));
        label.setPreferredSize(new Dimension(65, 25));
        panel.add(label);
        panel.add(copyButton);
        panel.add(component);
        return panel;
    }

    private void setPanelBorder(JPanel panel){
        Border empty  = BorderFactory.createEmptyBorder(5,5,5,5);
        Border etched = BorderFactory.createEtchedBorder();
        panel.setBorder(BorderFactory.createCompoundBorder(etched,empty));
    }

    private JPanel createCasePanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1, 5, 5));
        setPanelBorder(panel);
        try {
            panel.add(createLine("Nr Sprawy", txtCaseNo));
            panel.add(createLine("Nr SOD", txtSODNo));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return panel;
    }

    private JPanel createAddresseePanel() {
        JPanel panel = new JPanel(new GridLayout(5, 1, 5, 10));
        setPanelBorder(panel);
        try {
            panel.add(createLine("Nazwa", txtFirstname));
            panel.add(createLine("Wydział", txtLastname));
            panel.add(createLine("Adres", txtAddress));
            panel.add(createLine("Kod", txtZipcode));
            panel.add(createLine("Poczta", txtPost));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return panel;
    }

    private void openPDF(String fileName){
            if (Desktop.isDesktopSupported()) {
                try {
                    File myFile = new File(fileName);
                    Desktop.getDesktop().open(myFile);
                } catch (IOException ex) {
                    String message = "Brak zainstalowanej aplikacji do obsługi plików PDF";
                    JOptionPane.showMessageDialog(rootPane, message, "Informacja", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

    private void createEnvelope(String caseNo, String SODNo, String addressee){

        String pattern = "ddMMyyyyhhmmss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());

        try {
            InputStream pdf = this.getClass().getResourceAsStream("envelope_template.pdf");
            PDDocument pDDocument = PDDocument.load(pdf);
            PDAcroForm pDAcroForm = pDDocument.getDocumentCatalog().getAcroForm();
            PDField fieldCaseNo = pDAcroForm.getField("Nr_Sprawy");
            fieldCaseNo.setValue(caseNo);

            PDField fieldSODNo = pDAcroForm.getField("Nr_SOD");
            fieldSODNo.setValue(SODNo);

            PDField fieldAdresat = pDAcroForm.getField("Adresat");
            fieldAdresat.setValue(addressee);

            String timestamp = date;
            pDDocument.save("envelope_" + timestamp + ".pdf");
            pDDocument.close();

            String message = "Plik PDF z zaadresowaną kopertą został wygenerowany\npod nazwą: envelope_" + timestamp + ".pdf";
            JOptionPane.showMessageDialog(rootPane, message, "Informacja", JOptionPane.INFORMATION_MESSAGE);

            openPDF("envelope_" + timestamp + ".pdf");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean validateData(){ //Sprawdzenie wymagalności danych

        if(txtCaseNo.getText().equals("")) return false;
        if(txtSODNo.getText().equals("")) return false;
        if(txtFirstname.getText().equals("")) return false;
        if(txtAddress.getText().equals("")) return false;
        if(txtZipcode.getText().equals("")) return false;
        if(txtPost.getText().equals("")) return false;

        return true;
    }

    private class OkHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            if(validateData()) {
                StringBuilder sb = new StringBuilder();
                sb.append(txtFirstname.getText() + "\n");
                sb.append(txtLastname.getText() + "\n");
                sb.append(txtAddress.getText() + "\n");
                sb.append(txtZipcode.getText() + " ");
                sb.append(txtPost.getText());

                String addressee = sb.toString();
                String caseNo = txtCaseNo.getText();
                String SODNo = txtSODNo.getText();
                

                String message = "Czy dodać wysyłkę do rejestru ?";

                int choice = JOptionPane.showOptionDialog(Addressee.this,
                        message,
                        "Pytanie",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null, null, JOptionPane.OK_OPTION);
                if (choice == JOptionPane.YES_OPTION)
                {
                   long id = ((AddresseeModel)list.getSelectedValue()).getId();
                   new CaseDAO().insertCase(new CaseModel(txtCaseNo.getText(),txtSODNo.getText(), String.valueOf(id)));
                }

                createEnvelope(caseNo, SODNo, addressee);

            }
            else {
                String message = "Brak wymaganych danych aby kontynuować.\n"
                        + "Uzupełnij brakujące dane.";
                JOptionPane.showMessageDialog(rootPane, message, "Informacja", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void clearData(){
        txtCaseNo.setText("");
        txtSODNo.setText("TD/OWR/OMI/"+ date +"/0000001");
        txtFirstname.setText("");
        txtLastname.setText("");
        txtAddress.setText("");
        txtZipcode.setText("");
        txtPost.setText("");
    }

    private class ClearHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            clearData();
        }
    }

    private class QuiteHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }

    private class copyButtonListener implements ActionListener {

        private JTextField textField;
        public copyButtonListener(JTextField textField) {
            this.textField = textField;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String textToCopy = textField.getText();
            ImageIcon clipboardIcon = null;
            try {
                clipboardIcon = new ImageIcon(ImageIO.read(getClass().getResource("clipboard_i48px.png")));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            if(!textToCopy.equals("")) {
                StringSelection stringSelection = new StringSelection(textToCopy);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
               AutoDismissDialog.showMessageDialog(rootPane,
                       "Tekst: \n" + textToCopy + "\nzostał skopiowany do schowka.",
                       "Informacja",
                       2000,clipboardIcon);
            }
        }
    }


}
