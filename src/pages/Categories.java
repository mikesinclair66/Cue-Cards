package pages;

import java.awt.Graphics2D;
import main.MainPanel;
import navigation.BackButton;
import navigation.DeleteButton;
import navigation.Directory;
import navigation.NewSet;
import navigation.SaveButton;
import storage.CardPack;

public class Categories {
    public static int curFolder;//the folder that the user is currently in
    static BackButton backButton =
            new BackButton(6, 6, MainPanel.navbarHeight - 15, MainPanel.navbarHeight - 15);
    static NewSet newFolderIcon
            = new NewSet(6 + MainPanel.navbarHeight, 6, 
                    MainPanel.navbarHeight - 15, MainPanel.navbarHeight - 15, false);
    static SaveButton saveButton
            = new SaveButton(6 + MainPanel.navbarHeight * 2, 6, MainPanel.navbarHeight - 15,
            MainPanel.navbarHeight - 15);
    public static DeleteButton deleteButton
            = new DeleteButton(6 + MainPanel.navbarHeight * 3, 6, MainPanel.navbarHeight - 15,
            MainPanel.navbarHeight - 15);
    
    public static int packsNo;//number of cue-card packs
    
    public static void draw(Graphics2D comp){
        backButton.draw(comp);
        newFolderIcon.draw(comp);
        saveButton.draw(comp);
        deleteButton.draw(comp);
        Directory.draw(comp);
        
        if(!Directory.naming){
            if(backButton.highlighted)
                backButton.an.draw(comp);
            if(newFolderIcon.highlighted)
                newFolderIcon.an.draw(comp);
            if(saveButton.highlighted)
                saveButton.an.draw(comp);
            if(deleteButton.highlighted)
                deleteButton.an.draw(comp);
        } else{
            backButton.an.count = 0;
            backButton.an.visible = false;
            newFolderIcon.an.count = 0;
            newFolderIcon.an.visible = false;
            saveButton.an.count = 0;
            saveButton.an.visible = false;
            deleteButton.an.count = 0;
            deleteButton.an.visible = false;
        }
    }
    
    public static void update(){
        backButton.update();
        newFolderIcon.update();
        saveButton.update();
        deleteButton.update();
        Directory.render(packsNo);
    }
    
    /** Checks if any buttons are highlighted. */
    public static void requestHighlight(int x, int y){
        if(!Directory.naming){
            backButton.requestHighlight(x, y);
            newFolderIcon.requestHighlight(x, y);
            saveButton.requestHighlight(x, y);
            deleteButton.requestHighlight(x, y);
        }
    }
    
    /** Checks if any buttons are clicked. */
    public static void requestLeftClick(){
        if(!Directory.naming){
            backButton.requestClick();
            newFolderIcon.requestClick();
            saveButton.requestClick();
            deleteButton.requestClick();
        }
    }
}