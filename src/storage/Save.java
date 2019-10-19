package storage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import navigation.Directory;
import navigation.SaveButton;
import pages.FlashCards;
import pages.Subject;

public class Save {
    public static String path = "C:\\Users\\michael66\\Documents\\NetBeansProjects\\Cue Cards\\save\\";
    public static boolean pathSet;//if the save directory is already set by the user
    static File projNames;
    static File catFile;
    
    static FileOutputStream outStream;
    static Properties prop = new Properties();
    
    public static final String EX = ".properties";//extension
    
    public static void save(){
        //save the folder names
        projNames = new File(path + "folderNames" + EX);
        try{ outStream = new FileOutputStream(projNames); }
        catch(IOException e){ System.err.println("Could not save folderNames" + EX); }
        
        for(int i = 0; i < Subject.foldersNo; i++)
            prop.setProperty("fo" + i, Subject.folders[i].name);
        
        //check if there are any deleted folders. If so, delete all cat packs
        int i = Subject.foldersNo;
        int j = 0;
        catFile = new File("fo" + i + "ca" + j + EX);
        do{
            while(catFile.exists()){
                catFile.delete();
                catFile = new File("fo" + i + "ca" + (++j) + EX);
            }
            j = 0;
            catFile = new File("fo" + (++i) + "ca" + j + EX);
        } while(catFile.exists());
        
        //save the path
        prop.setProperty("path", path);
        
        try{
            prop.store(outStream, "");
            outStream.close();
        } catch(IOException e){ 
            System.err.print("Could not save folderNames" + EX);
            SaveButton.displaySaveFailure();
        }
        prop.clear();
        
        //save each category num with their folder num
        for(int fNo = 0; fNo < Subject.foldersNo; fNo++){
            for(int cNo = 0; cNo < Subject.folders[fNo].packsNo; cNo++){
                catFile = new File(path + "fo" + fNo + "ca" + cNo + EX);
                try{ outStream = new FileOutputStream(catFile); }
                catch(IOException e){
                    System.err.println("Could not save fo" + fNo + "ca"
                        + cNo + EX); 
                    SaveButton.displaySaveFailure();
                }
                
                boolean edited = Subject.folders[fNo].packs[cNo].edited;
                prop.setProperty("edited", edited + "");
                prop.setProperty("name", Subject.folders[fNo].packs[cNo].name);
                if(edited)//if the pack has been edited, save the pack
                    savePack(Subject.folders[fNo].packs[cNo].frontSide,
                            Subject.folders[fNo].packs[cNo].backSide);
                
                try{
                    prop.store(outStream, "");
                    outStream.close();
                } catch(IOException e){
                    System.err.println("Could not save fo" + fNo + "ca" + cNo + EX);
                    SaveButton.displaySaveFailure();
                }
                prop.clear();
            }
            
            //check if there are any deleted category pack files
            int cNo = Subject.folders[fNo].packsNo;
            catFile = new File(path + "fo" + fNo + "ca" + cNo + EX);
            while(catFile.exists()){
                catFile.delete();
                catFile = new File(path + "fo" + fNo + "ca" + (++cNo) + EX);
            }
        }
        
        SaveButton.displaySaveSuccess();
    }
    
    private static void savePack(String frontSide[][], String backSide[][]){
        for(int page = 0; page < Directory.PACK_LIMIT; page++){
            for(int line = 0; line <= FlashCards.lines; line++){
                prop.setProperty("frpa" + page + "li" + line, frontSide[page][line]);
                prop.setProperty("bapa" + page + "li" + line, backSide[page][line]);
            }
        }
    }
}