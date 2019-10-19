package navigation;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class OpenButton extends Button {
    Color folderColor = new Color(205, 255, 124);
    Color folderOutline = new Color(99, 124, 0);
    
    JFrame chooserFrame = new JFrame();
    JFileChooser fileChooser = new JFileChooser();
    
    public OpenButton(int x, int y, int width, int height){
        super(x, y, width, height);
        an.setMsg("Open directory");
    }
    
    @Override
    public void drawIcon(Graphics2D comp){
        //draw the folder icon with polygons
        comp.setColor(folderColor);

        int[] xPoints = {x + 5, x + 30, x + 35, x + 10};
        int[] yPoints = {y + 15, y + 15, y + 35, y + 35};
        comp.fillPolygon(xPoints, yPoints, 4);
        comp.setColor(folderOutline);
        comp.drawLine(x + 5, y + 15, x + 10, y + 35);
        comp.drawLine(x + 16, y + 15, x + 30, y + 15);
        comp.drawLine(x + 30, y + 15, x + 35, y + 35);

        xPoints[0] = x + 8;
        xPoints[1] = x + 13;
        xPoints[2] = x + 16;
        xPoints[3] = x + 5;
        yPoints[0] = y + 10;
        yPoints[1] = y + 10;
        yPoints[2] = y + 15;
        yPoints[3] = y + 15;
        comp.setColor(folderColor);
        comp.fillPolygon(xPoints, yPoints, 4);
        comp.setColor(folderOutline);
        comp.drawLine(xPoints[3], yPoints[3], xPoints[0], yPoints[0]);
        comp.drawLine(xPoints[0], yPoints[0], xPoints[1], yPoints[1]);
        comp.drawLine(xPoints[1], yPoints[1], xPoints[2], yPoints[2]);

        xPoints[0] = x + 15;
        xPoints[1] = x + 40;
        xPoints[2] = x + 35;
        xPoints[3] = x + 10;
        yPoints[0] = y + 20;
        yPoints[1] = y + 20;
        yPoints[2] = y + 35;
        yPoints[3] = y + 35;
        comp.setColor(folderColor);
        comp.fillPolygon(xPoints, yPoints, 4);
        comp.setColor(folderOutline);
        comp.drawPolygon(xPoints, yPoints, 4);
    }
    
    @Override
    public void execute(){
        fileChooser.setDialogTitle("Choose the directory that contains your cue cards");
        fileChooser.setCurrentDirectory(new File("C:\\Users\\Acer\\Documents\\Bcit\\Cue cards\\"));
        fileChooser.setSelectedFile(new File("save.properties"));
        
        int loadDialog = fileChooser.showOpenDialog(chooserFrame);
        if (loadDialog == JFileChooser.APPROVE_OPTION){
            
        }
    }
}