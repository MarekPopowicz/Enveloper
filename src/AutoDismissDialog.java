
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AutoDismissDialog implements ActionListener{

    private JDialog dialog;

    private AutoDismissDialog(JDialog dialog)
    {
        this.dialog = dialog;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent)
    {
        dialog.dispose();
    }

    public static void showMessageDialog(Component parentComponent,
                                         String message, String title,
                                         int delayInMilliseconds, ImageIcon icon)
    {
        final JOptionPane optionPane = new JOptionPane(message,JOptionPane.PLAIN_MESSAGE,JOptionPane.CLOSED_OPTION,icon);
        final JDialog dialog = optionPane.createDialog(parentComponent, title);
        dialog.setTitle(title);
        Timer timer = new Timer(delayInMilliseconds, new AutoDismissDialog(dialog));
        timer.setRepeats(false);
        timer.start();
        if (dialog.isDisplayable())
        {
            dialog.setVisible(true);
        }
    }
}
