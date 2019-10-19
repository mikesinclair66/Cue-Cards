package storage;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import navigation.Directory;
import pages.Categories;
import pages.Subject;

public class Delete {
    static File f;
    static FileInputStream inStream;
    static Properties prop = new Properties();
    
    public static void deleteCategory(int selected){
        //delete the selected file
        Subject.folders[Categories.curFolder].packs[selected] = null;
        Directory.packs[selected] = null;
        
        //bring every category pack past the deleted file down a number
        for(int i = selected; i < Subject.folders[Categories.curFolder].packsNo - 1; i++){
            Subject.folders[Categories.curFolder].packs[i] =
                    Subject.folders[Categories.curFolder].packs[i + 1];
            Directory.packs[i] = Directory.packs[i + 1];
        }
        Subject.folders[Categories.curFolder].packs[
                Subject.folders[Categories.curFolder].packsNo - 1] = null;
        Directory.packs[Directory.files - 1] = null;
        Subject.folders[Categories.curFolder].packsNo--;
        Categories.packsNo--;
    }
    
    public static void deleteFolder(int selected){
        //delete the selected folder
        Subject.folders[selected] = null;
        Directory.packs[selected] = null;
        
        //bring every folder past the deleted file down a number
        for(int i = selected; i < Subject.foldersNo - 1; i++){
            Subject.folders[i] = Subject.folders[i + 1];
            Directory.packs[i] = Directory.packs[i + 1];
        }
        Subject.folders[Subject.foldersNo - 1] = null;
        Directory.packs[Directory.files - 1] = null;
        Subject.foldersNo--;
    }
}