import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.util.prefs.BackingStoreException;

public class DBFileChooser {

    private FileTypeFilter filter = new FileTypeFilter("db", "Database file");
    private File selectedFile = null;

    public DBFileChooser() {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.addChoosableFileFilter(filter);
        jFileChooser.setAcceptAllFileFilterUsed(false);
        jFileChooser.setFileFilter(filter);
        jFileChooser.setCurrentDirectory(new File(Application.preferences.get("database","")));

        int result = jFileChooser.showOpenDialog(new JFrame());

        if (result == JFileChooser.APPROVE_OPTION) {
            this.selectedFile = jFileChooser.getSelectedFile();
            try {
                Application.preferences.clear();
                Application.preferences.put("database",selectedFile.getAbsolutePath());
            } catch (BackingStoreException backingStoreException) {
                backingStoreException.printStackTrace();
            }
        }
    }

    public File getSelectedFile() {
        return selectedFile;
    }

    private class FileTypeFilter extends FileFilter {
        private String extension;
        private String description;

        public FileTypeFilter(String extension, String description) {
            this.extension = extension;
            this.description = description;
        }

        public boolean accept(File file) {
            if (file.isDirectory()) {
                return true;
            }
            return file.getName().endsWith(extension);
        }
        public String getDescription() {
            return description + String.format(" (*%s)", extension);
        }
    }
}
