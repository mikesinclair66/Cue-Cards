package storage;

import navigation.Directory;
import pages.FlashCards;

public class CardPack extends Pack {
    public String frontSide[][] = new String[Directory.PACK_LIMIT][FlashCards.lines + 1];
    public String backSide[][] = new String[Directory.PACK_LIMIT][FlashCards.lines + 1];
    public boolean edited;//if the card pack has been worked on
    
    public CardPack(String name){
        super(name);
    }
}