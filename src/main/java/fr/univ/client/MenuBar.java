package fr.univ.client;

import javax.swing.*;

public class MenuBar extends JMenuBar {

    public MenuBar() {
        super();

        JButton itemNew = new JButton( "New" );
        this.add(itemNew);

        JButton itemOpen = new JButton( "Open" );
        this.add(itemOpen);

        JButton itemSave = new JButton( "Save" );
        this.add(itemSave);

        JButton itemSaveAs = new JButton( "Save as" );
        this.add(itemSaveAs);

    }

}
