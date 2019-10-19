package storage;

import navigation.Directory;

public class Folder extends Pack {
    public CardPack[] packs = new CardPack[Directory.PACK_LIMIT];
    public int packsNo;//number of packs
    
    public Folder(String name){
        super(name);
    }
}