package fr.univ.client;

import javax.swing.*;

public class MenuBar extends JMenuBar {

    public MenuBar() {
        JMenuItem itemNew = new JMenuItem( "New" );
        this.add(itemNew);

        /*JMenuItem itemOpen = new JMenuItem( "Open" );
        this.add(itemOpen);

        JMenuItem itemSave = new JMenuItem( "Save" );
        this.add(itemSave);

        JMenuItem itemSaveAs = new JMenuItem( "Save as" );
        this.add(itemSaveAs);*/

    }

}
