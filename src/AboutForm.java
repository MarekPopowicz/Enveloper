
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class AboutForm extends JDialog {

    public AboutForm() throws IOException {

        setTitle("(c) 2020 Marek Popowicz");
       setPreferredSize(new Dimension(450, 240));
        setResizable(false);
        setModal(true);
        setAlwaysOnTop(true);
       try {
           createView();
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(rootPane);

        setVisible(true);
    }


    private void createView() throws IOException, BadLocationException {

        JPanel panel = new JPanel();

        JTextPane pane = new JTextPane();
        pane.setEditable(false);

        StyledDocument doc = pane.getStyledDocument();

        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        StyleConstants.setForeground(center, Color.BLUE);
        StyleConstants.setFontSize(center, 16);

        doc.insertString(doc.getLength(), "Enveloper 1.0", center);

        SimpleAttributeSet left = new SimpleAttributeSet();
        StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);
        StyleConstants.setForeground(left, Color.DARK_GRAY);
        StyleConstants.setFontSize(left, 12);

        doc.insertString(13,"\n\n",left);


        String info = "Aplikacja do wydruku zaadresowanych szablonów kopert.\n" +
                "- Umożliwia zapis adresatów do lokalnej bazy danych.\n" +
                "- Umożliwia zapis wysłanej korespondencji do rejestru.\n" +
                "- Umożliwia przełączanie aplikacji pomiędzy bazami danych.\n";


        doc.insertString(doc.getLength(), info, left);

        JScrollPane scrollPane = new JScrollPane(pane);
        panel.add(scrollPane, BorderLayout.CENTER);

        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setLayout(new BoxLayout(panel,BoxLayout.PAGE_AXIS));

        panel.add(Box.createRigidArea(new Dimension(0,5)));
        panel.add(createButtonPanel());
        add(panel);
    }

    private JPanel createButtonPanel() throws IOException {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        setPanelBorder(panel);
        Icon okIcon = new ImageIcon(ImageIO.read(getClass().getResource("ok.png")));
        panel.add(createButton("", new OkHandler(), okIcon));

        return panel;
    }

    private JButton createButton(String text, ActionListener listener, Icon icon) {
        JButton cmd = new JButton(text, icon);
        cmd.setPreferredSize(new Dimension(60, 40));
        cmd.addActionListener(listener);
        return cmd;
    }

    private void setPanelBorder(JPanel panel){
        Border empty  = BorderFactory.createEmptyBorder(5,5,5,5);
        Border etched = BorderFactory.createEtchedBorder();
        panel.setBorder(BorderFactory.createCompoundBorder(etched,empty));
    }

    private class OkHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }
}
