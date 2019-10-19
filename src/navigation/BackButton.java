package navigation;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import main.MainPanel;
import pages.*;
import storage.CardPack;

public class BackButton extends Button {
    Font font = new Font("Elephant", Font.BOLD, 20);
    
    public BackButton(int x, int y, int width, int height){
        super(x, y, width, height);
        an.setMsg("Go back");
    }
    
    @Override
    public void drawIcon(Graphics2D comp){
        comp.setFont(font);
        comp.setColor(Color.green);
        comp.drawString("<", x + 9, y + 32);
        comp.drawString("_", x + 24, y + 23);
    }
    
    @Override
    public void execute(){
        if(MainPanel.page == 1){
            int length = Directory.packs.length;
            for(int i = 0; i < length; i++){
                Subject.folders[Categories.curFolder].packs[i] = (CardPack) Directory.packs[i];
                Directory.packs[i] = null;
            }
            //clear the empty folders
            for(int i = length; i < Directory.PACK_LIMIT; i++)
                Subject.folders[Categories.curFolder].packs[i] = null;
            
            //fill Directory.packs with Subject.folders
            length = Subject.folders.length;
            for(int i = 0; i < length; i++)
                Directory.packs[i] = Subject.folders[i];
            
            Directory.navigateFolders = true;
            MainPanel.page = 0;
        } else if(MainPanel.page == 2){
            //assign the Subject.folders all their data
            for(int cardNo = 0; cardNo < Directory.PACK_LIMIT; cardNo++){
                for(int line = 0; line <= FlashCards.lines; line++){
                    Subject.folders[Categories.curFolder].packs[FlashCards.curCardPack].frontSide[cardNo][line]
                            = FlashCards.frontSide[cardNo][line];
                    Subject.folders[Categories.curFolder].packs[FlashCards.curCardPack].backSide[cardNo][line]
                            = FlashCards.backSide[cardNo][line];
                }
            }
            Subject.folders[Categories.curFolder].packs[FlashCards.curCardPack].edited = true;
            
            MainPanel.page = 1;
            FlashCards.page = 0;
            Directory.selected = -1;
        }
    }
}