

import javax.swing.*;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

public abstract class DBConnect {

    private static  String DB_PATH = "jdbc:sqlite:";
    private static  String DB_NAME = "enveloper.db";

    public static Connection connect() {
        Connection c = null;

        try {
            Class.forName("org.sqlite.JDBC");

            String dbFile = Application.preferences.get("database","");

            File f = new File(dbFile);
            if(f.exists() && f.isFile()) {
                DB_NAME = dbFile;
                c = DriverManager.getConnection(DB_PATH + DB_NAME);
            }
           else {
                String path = new File(".").getCanonicalPath() + File.separatorChar;
                String message = "Baza adresatów nie istnieje, została usunięta lub przeniesiona.\n" +
                "Wybierz istniejącą bazę adresatów lub zostanie utworzona nowa.";
                int response = JOptionPane.showConfirmDialog(null, message, "Informacja", JOptionPane.WARNING_MESSAGE);

                if(response==JOptionPane.CANCEL_OPTION) {
                        c = DriverManager.getConnection(DB_PATH + DB_NAME);
                        Application.preferences.clear();
                        Application.preferences.put("database", path + DB_NAME);
                        String m = "Baza adresatów została utworzona w lokalizacji:\n" + path + DB_NAME;
                        JOptionPane.showMessageDialog(null, m, "Informacja", JOptionPane.INFORMATION_MESSAGE);
                    }

                    if(response==JOptionPane.OK_OPTION) {
                        File file = new DBFileChooser().getSelectedFile();

                        if (file != null) {
                            Application.preferences.clear();
                            Application.preferences.put("database", file.getCanonicalPath());
                            c = DriverManager.getConnection(DB_PATH + Application.preferences.get("database",""));
                        }
                        else {
                            String m = "Nie wskazano bazy danych adresatów.\nAplikacja zostanie zamknięta.";
                            JOptionPane.showMessageDialog(null, m, "Informacja", JOptionPane.INFORMATION_MESSAGE);
                            System.exit(0);
                        }
                    }
            }


        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return c;
    }


}



