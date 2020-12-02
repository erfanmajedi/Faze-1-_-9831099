package ceit.gui;

import ceit.model.Note;
import ceit.utils.FileUtils;
import org.joda.time.*;
import org.joda.time.format.*;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

/**
 * this CMainPanel makes the main panel that contains 2 parts : text field to write on a file and list of files.
 */
public class CMainPanel extends JPanel {

    //Tabs for text fields.
    private JTabbedPane tabbedPane;

    //list of files.
    private JList<File> directoryList;

    /**
     * this constructor makes the main panel and adds tabs and files to it.
     */
    public CMainPanel() {

        setLayout(new BorderLayout());

        initDirectoryList(); // add JList to main Panel

        initTabbedPane(); // add TabbedPane to main panel

        addNewTab(); // open new empty tab when user open the application
    }

    /**
     * the initTabbedPane method makes the tabs.
     */
    private void initTabbedPane() {

        tabbedPane = new JTabbedPane();
        add(tabbedPane, BorderLayout.CENTER);
    }

    /**
     * this initDirectoryList method creates the list of files and show it to the
     * left part of the main frame.
     */
    private void initDirectoryList() {

        File[] files = FileUtils.getFilesInDirectory();
        directoryList = new JList<>(files);

        directoryList.setBackground(new Color(211, 211, 211));
        directoryList.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        directoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        directoryList.setVisibleRowCount(-1);
        directoryList.setMinimumSize(new Dimension(130, 100));
        directoryList.setMaximumSize(new Dimension(130, 100));
        directoryList.setFixedCellWidth(130);
        directoryList.setCellRenderer(new MyCellRenderer());
        directoryList.addMouseListener(new MyMouseAdapter());

        add(new JScrollPane(directoryList), BorderLayout.WEST);
    }

    /**
     * this addNewTab adds a new tab to the JTabbedPane and prepares a text field to write on it.
     */
    public void addNewTab() {
        JTextArea textPanel = createTextPanel();
        textPanel.setText("Write Something here...");
        tabbedPane.addTab("Tab " + (tabbedPane.getTabCount() + 1), textPanel);
    }

    /**
     * this openExistingNote takes a name and opens the tab to show the content.
     *
     * @param content content want to show.
     */
    public void openExistingNote(String content) {
        JTextArea existPanel = createTextPanel();
        existPanel.setText(content);

        int tabIndex = tabbedPane.getTabCount() + 1;
        tabbedPane.addTab("Tab " + tabIndex, existPanel);
        tabbedPane.setSelectedIndex(tabIndex - 1);
    }

    /**
     * this saveNote method takes the text from text area and write in on a file.
     */
    public void saveNote() {
        JTextArea textPanel = (JTextArea) tabbedPane.getSelectedComponent();
        String note = textPanel.getText();
        if (!note.isEmpty()) {
            try {
                FileUtils.fileWriterWithBuffer(note);
            } catch (Exception e) {
                System.out.println("failed to write");
            }
        }
        updateListGUI();
    }

    /**
     * this saveObject method takes the text and makes a note object then write it on
     * file using Serializable.
     */
    public void saveObject() {
        JTextArea textPanel = (JTextArea) tabbedPane.getSelectedComponent();
        String note = textPanel.getText();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(" yyyy-MM-dd HH:mm:ss ");
        LocalDateTime now = LocalDateTime.now();
        Note noteObject = new Note(note,note,dtf.format(now));
        if (!note.isEmpty()) {
            try {
                FileUtils.writeFileObject(noteObject);
            } catch (Exception e) {
                System.out.println("failed to write the object");
            }
        }
        updateListGUI();

    }

    /**
     * this createTextPanel method makes a text field and returns it.
     *
     * @return text panel.
     */
    private JTextArea createTextPanel() {
        JTextArea textPanel = new JTextArea();
        textPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return textPanel;
    }

    /**
     * this updateListGUI updates the list of files in the app.
     */
    private void updateListGUI() {
        File[] newFiles = FileUtils.getFilesInDirectory();
        directoryList.setListData(newFiles);
    }

    /**
     * override the mouseClicked method to listen to the clicks.
     */
    private class MyMouseAdapter extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent eve) {
            // Double-click detected
            if (eve.getClickCount() == 2) {
                int index = directoryList.locationToIndex(eve.getPoint());
                System.out.println("Item " + index + " is clicked...");
                //TODO: Phase1: Click on file is handled... Just load content into JTextArea
                File curr[] = FileUtils.getFilesInDirectory();
                try {
                    String content = FileUtils.readFileObject(curr[index]);
                    openExistingNote(content);
                } catch (Exception e) {
                    System.out.println("File not found.");
                }
            }
        }
    }

    /**
     * override the getListCellRendererComponent method to take a component.
     */
    private class MyCellRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList list, Object object, int index, boolean isSelected, boolean cellHasFocus) {
            if (object instanceof File) {
                File file = (File) object;
                setText(file.getName());
                setIcon(FileSystemView.getFileSystemView().getSystemIcon(file));
                if (isSelected) {
                    setBackground(list.getSelectionBackground());
                    setForeground(list.getSelectionForeground());
                } else {
                    setBackground(list.getBackground());
                    setForeground(list.getForeground());
                }
                setEnabled(list.isEnabled());
            }
            return this;
        }
    }
}

