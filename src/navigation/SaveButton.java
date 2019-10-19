package navigation;

import java.awt.Graphics2D;
import java.awt.Color;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import main.MainPanel;
import pages.*;
import storage.Save;

public class SaveButton extends Button {
    JFrame chooserFrame = new JFrame();
    JFileChooser fileChooser = new JFileChooser();
    
    public SaveButton(int x, int y, int width, int height){
        super(x, y, width, height);
        an.setMsg("Save all");
    }
    
    @Override
    void drawIcon(Graphics2D comp) {
        //draw the save button icon
        comp.setColor(new Color(137, 86, 255));
        comp.fillRoundRect(x + 5, y + 5, width - 10, height - 10, 5, 5);
        comp.setColor(new Color(25, 25, 25));
        comp.fillRect(x + 10, y + 5, width - 20, height / 4);
        comp.setColor(Color.white);
        comp.fillRect(x + 10, y + height / 2, width - 20, height / 2 - 5);
    }

    @Override
    void execute() {
        storeFlashCards();
        if(!Save.pathSet)
            requestSavePath();
        else
            Save.save();
    }
    
    void requestSavePath(){
        fileChooser.setDialogTitle("Choose a directory to save files");
        
        fileChooser.setCurrentDirectory(new File("C:\\Users\\Acer\\Documents\\Bcit\\Cue cards\\"));
        fileChooser.setSelectedFile(new File("save.properties"));
        int saveDialog = fileChooser.showSaveDialog(chooserFrame);
 
        if (saveDialog == JFileChooser.APPROVE_OPTION) {
            Save.pathSet = true;
            Save.path = fileChooser.getCurrentDirectory() + "\\";
            Save.save();
        }
    }
    
    /** Puts FlashCards.frontSide/backSide in their assigned slots if on flashcards page. */
    private void storeFlashCards(){
        if(MainPanel.page == 2){
            for(int page = 0; page < Directory.PACK_LIMIT; page++){
                for(int line = 0; line <= FlashCards.lines; line++){
                    Subject.folders[Categories.curFolder].packs[FlashCards.curCardPack].frontSide[page][line]
                            = FlashCards.frontSide[page][line];
                    Subject.folders[Categories.curFolder].packs[FlashCards.curCardPack].backSide[page][line]
                            = FlashCards.backSide[page][line];
                    Subject.folders[Categories.curFolder].packs[FlashCards.curCardPack].edited = true;
                }
            }
        }
    }
}