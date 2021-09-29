import javax.swing.*;
import java.awt.*;


public class LookAndFeel {

    public static final String NIMBUS_LF = "Nimbus";


    public  void setLookAndFeel() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if (NIMBUS_LF.equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
    }
            String message = "Wybrany motyw nie jest dost\u0119pny w Twoim systemie.";
            public void setMetalLook(Component c) {
                try
                {
                    UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
                    SwingUtilities.updateComponentTreeUI(c);
                }
                catch (Exception ex)
                {
                    JOptionPane.showMessageDialog(null, message,"Informacja",JOptionPane.INFORMATION_MESSAGE);
                }
            }

            public void setMotifLook(Component c) {
                try
                {
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
                    SwingUtilities.updateComponentTreeUI(c);
                }
                catch (Exception ex)
                {
                    JOptionPane.showMessageDialog(null, message,"Informacja",JOptionPane.INFORMATION_MESSAGE);
                }
            }

    public void setNimbusLook(Component c) {
        try
        {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
            SwingUtilities.updateComponentTreeUI(c);
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, message,"Informacja",JOptionPane.INFORMATION_MESSAGE);
        }
    }

            public void setWindowsLook(Component c) {
                try
                {
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                    SwingUtilities.updateComponentTreeUI(c);
                }
                catch (Exception ex)
                {
                    JOptionPane.showMessageDialog(null, message,"Informacja",JOptionPane.INFORMATION_MESSAGE);
                }
            }


}
