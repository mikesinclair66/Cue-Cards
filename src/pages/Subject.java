package pages;

import java.awt.Graphics2D;
import main.MainPanel;
import navigation.DeleteButton;
import navigation.Directory;
import navigation.NewSet;
import navigation.OpenButton;
import navigation.SaveButton;
import storage.Folder;

public class Subject {
    static NewSet newFolderIcon
            = new NewSet(6, 6, MainPanel.navbarHeight - 15, MainPanel.navbarHeight - 15, true);
    static SaveButton saveButton
            = new SaveButton(6 + MainPanel.navbarHeight, 6, MainPanel.navbarHeight - 15,
            MainPanel.navbarHeight - 15);
    static OpenButton openButton
            = new OpenButton(6 + MainPanel.navbarHeight * 2, 6, MainPanel.navbarHeight - 15,
            MainPanel.navbarHeight - 15);
    public static DeleteButton deleteButton
            = new DeleteButton(6 + MainPanel.navbarHeight * 3, 6, MainPanel.navbarHeight - 15,
            MainPanel.navbarHeight - 15);
    
    public static int foldersNo;//number of folders
    public static Folder[] folders = new Folder[Directory.PACK_LIMIT];
    
    public static void draw(Graphics2D comp){
        newFolderIcon.draw(comp);
        saveButton.draw(comp);
        openButton.draw(comp);
        deleteButton.draw(comp);
        Directory.draw(comp);
        
        if(!Directory.naming){
            if(newFolderIcon.highlighted)
                newFolderIcon.an.draw(comp);
            if(saveButton.highlighted)
                saveButton.an.draw(comp);
            if(openButton.highlighted)
                openButton.an.draw(comp);
            if(deleteButton.highlighted)
                deleteButton.an.draw(comp);
        } else {
            newFolderIcon.an.count = 0;
            newFolderIcon.an.visible = false;
            saveButton.an.count = 0;
            saveButton.an.visible = false;
            openButton.an.count = 0;
            openButton.an.visible = false;
            deleteButton.an.count = 0;
            deleteButton.an.visible = false;
        }
    }
    
    public static void update(){
        newFolderIcon.update();
        saveButton.update();
        openButton.update();
        deleteButton.update();
        Directory.render(foldersNo);
    }
    
    /** Checks if any buttons are highlighted. */
    public static void requestHighlight(int x, int y){
        if(!Directory.naming){
            newFolderIcon.requestHighlight(x, y);
            saveButton.requestHighlight(x, y);
            openButton.requestHighlight(x, y);
            deleteButton.requestHighlight(x, y);
        }
    }
    
    /** Checks if any buttons are clicked. */
    public static void requestLeftClick(){
        if(!Directory.naming){
            newFolderIcon.requestClick();
            saveButton.requestClick();
            openButton.requestClick();
            deleteButton.requestClick();
        }
    }
}