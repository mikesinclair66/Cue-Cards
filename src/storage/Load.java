package storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import navigation.Directory;
import pages.*;
import storage.Folder;

public class Load {
    static File projNames;
    static File catFile;
    
    static FileInputStream inStream;
    static Properties prop = new Properties();
    
    public static void load(){
        projNames = new File(Save.path + "folderNames" + Save.EX);
        if(projNames.exists()){
            try{
                inStream = new FileInputStream(projNames);
                prop.load(inStream);
            } catch(IOException e){ System.err.println("Could not load folderNames" + Save.EX); }

            int folders = 0;
            String fName = prop.getProperty("fo0");
            while(fName != null){
                Subject.folders[folders] = new Folder(fName);
                Directory.packs[folders] = new Folder(fName);

                //load the number of categories in each folder
                int cat = 0;
                while((new File(Save.path + "fo" + folders + "ca" + cat + Save.EX)).exists())
                    cat++;
                Subject.folders[folders].packsNo = cat;
                Categories.packsNo = cat;

                fName = prop.getProperty("fo" + (++folders));
            }
            Subject.foldersNo = folders;
            Directory.files = folders;
            
            if(prop.getProperty("path") != null){
                Save.path = prop.getProperty("path");
                Save.pathSet = true;
            }

            try{ inStream.close(); }
            catch(IOException e){ System.err.println("Could not load folderNames" + Save.EX); }
            prop.clear();

            //load each category pack
            for(int fNo = 0; fNo < folders; fNo++){
                for(int cNo = 0; cNo < Subject.folders[fNo].packsNo; cNo++){
                    catFile = new File(Save.path + "fo" + fNo + "ca" + cNo + Save.EX);
                    try{
                        inStream = new FileInputStream(catFile);
                        prop.load(inStream);
                    } catch(IOException e){
                        System.err.println("Could not load fo" + fNo + "ca" + cNo + Save.EX);
                    }
                    
                    Subject.folders[fNo].packs[cNo] = new CardPack(prop.getProperty("name"));
                    boolean edited = Boolean.parseBoolean(prop.getProperty("edited"));
                    Subject.folders[fNo].packs[cNo].edited = edited;
                    if(edited)
                        loadPack(fNo, cNo);
                    else {
                        //make all strings in the cardpack empty
                        for(int page = 0; page < Directory.PACK_LIMIT; page++){
                            for(int i = 0; i <= FlashCards.lines; i++){
                                Subject.folders[fNo].packs[cNo].frontSide[page][i] = "";
                                Subject.folders[fNo].packs[cNo].backSide[page][i] = "";
                                
                                //make FlashCard's cardpack strings empty
                                FlashCards.frontSide[page][i] = "";
                                FlashCards.backSide[page][i] = "";
                            }
                        }
                    }

                    try{ inStream.close(); }
                    catch(IOException e){
                        System.err.println("Could not load fo" + fNo + "ca" + cNo + Save.EX);
                    }
                    prop.clear();
                }
            }
        } else {
            System.out.println("Could not load " + Save.path + "folderNames" + Save.EX);
        }
    }
    
    private static void loadPack(int fNo, int cNo){
        for(int page = 0; page < Directory.PACK_LIMIT; page++){
            for(int line = 0; line <= FlashCards.lines; line++){
                Subject.folders[fNo].packs[cNo].frontSide[page][line]
                        = prop.getProperty("frpa" + page + "li" + line);
                Subject.folders[fNo].packs[cNo].backSide[page][line]
                        = prop.getProperty("bapa" + page + "li" + line);
            }
        }
    }
}