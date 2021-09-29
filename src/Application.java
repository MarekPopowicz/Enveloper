import javax.swing.*;
import java.util.prefs.Preferences;

public class Application {
    protected static Preferences preferences;
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {
                    preferences = Preferences.userRoot().node("enveloper");

                    new LookAndFeel().setLookAndFeel();
                    new DBCreate();
                    new Addressee();


                } catch (Exception e) {
                    e.getStackTrace();
                    System.err.println(e.getClass().getName() + ": " + e.getMessage());
                }
            }

        });

    }
}