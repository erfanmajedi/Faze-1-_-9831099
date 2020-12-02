package ceit.gui;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * this CFrame class represents the main frame for the INote app.
 */
public class CFrame extends JFrame implements ActionListener {

    //main panel.
    private CMainPanel mainPanel;

    //add new item.
    private JMenuItem newItem;

    //save item.
    private JMenuItem saveItem;

    //exit app.
    private JMenuItem exitItem;

    //save note with current date.
    private JMenuItem saveItemDate;

    /**
     * this constructor makes the main frame and adds the menu bar.
     *
     * @param title title of the frame.
     */
    public CFrame(String title) {
        super(title);

        initMenuBar();//create menuBar

        initMainPanel(); //create main panel
    }

    /**
     * this initMenuBar method makes the menu bar for the app that contains new , save
     * , exit and save with date items.
     */
    private void initMenuBar() {

        JMenuBar menuBar = new JMenuBar();
        JMenu jmenu = new JMenu("File");

        newItem = new JMenuItem("New");
        //saveItem = new JMenuItem("Save");
        saveItemDate = new JMenuItem("Save With Date");
        exitItem = new JMenuItem("Exit");

        newItem.addActionListener(this);
        //saveItem.addActionListener(this);
        exitItem.addActionListener(this);
        saveItemDate.addActionListener(this);

        jmenu.add(newItem);
        //jmenu.add(saveItem);
        jmenu.add(saveItemDate);
        jmenu.add(exitItem);

        menuBar.add(jmenu);
        setJMenuBar(menuBar);
    }

    /**
     * this initMainPanel method make the main panel with 2 components.
     */
    private void initMainPanel() {
        mainPanel = new CMainPanel();
        add(mainPanel);
    }

    /**
     * override the actionPerformed method to take action in menubar.
     * @param e event.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newItem) {
            mainPanel.addNewTab();
        } else if (e.getSource() == saveItem) {
            mainPanel.saveObject();
        } else if (e.getSource() == exitItem) {
            //TODO: Phase1: check all tabs saved ...
            JTabbedPane tabs = ((JTabbedPane) mainPanel.getComponent(1));
            for (int i = 0; i < tabs.getTabCount(); i++) {
                mainPanel.saveObject();
            }
            System.exit(0);
        } else if (e.getSource() == saveItemDate) {
            mainPanel.saveObject();
        } else {
            System.out.println("Nothing detected...");
        }
    }
}

