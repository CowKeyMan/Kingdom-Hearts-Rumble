//To use GUI items
import java.awt.*;
import java.util.*;
import java.awt.event.*;
public class PauseScreen implements ActionListener{
    
    //The buttons to level up a specific attribute
    ArrayList<Label[]> lbl = new ArrayList<Label[]>();
    ArrayList<Image> img = new ArrayList<Image>();
    int width = 0, height = 0;
    
    Button exit = new Button("Exit to Main Menu"), cont = new Button("Continue");
    
    public PauseScreen(int widthLimit, int heightLimit){
        exit.setBounds(3,25, 250, 50);
        cont.setBounds(widthLimit/2 - 100, heightLimit - 80, 200, 50);
        
        hideAll();
    }
    
    void showAll(int widthLimit, int heightLimit, ArrayList<Character> c){
        lbl.clear();
        img.clear();
        
        //Add buttons and images
        for(int i = 0; i < c.size(); i++){
            lbl.add(new Label[5]);
            lbl.get(i)[0] = new Label("maxHP: " + c.get(i).maxHP, 1);
            lbl.get(i)[1] = new Label("Damage: " + c.get(i).damage, 1);
            lbl.get(i)[2] = new Label("Speed: " + c.get(i).getBaseSpeed(), 1);
            lbl.get(i)[3] = new Label("Ability 1 Cooldown: " + c.get(i).abilityCooldown1, 1);
            lbl.get(i)[4] = new Label("Ability 2 Cooldown: " + c.get(i).abilityCooldown2, 1);
        }
        for(int i = 0; i < c.size(); i ++){
            img.add(c.get(i).healthyImage);
        }
        
        width = (widthLimit - 100 - 10*(c.size()-1))/c.size();
        height = (heightLimit - 400 - 10*(lbl.get(0).length-1))/lbl.get(0).length;
        
        //Set labels' positions
        for(int i = 0; i < lbl.size(); i++){
            for(int x = 0; x < lbl.get(i).length; x++){
                lbl.get(i)[x].setBounds(50 + (width + 10)*i, 180 + (height + 10)*x, width, height);
            }
        }
        
        //Set them visible
        for(int i = 0; i < lbl.size(); i++){
            for(int x = 0; x < lbl.get(i).length; x++){
                lbl.get(i)[x].setVisible(true);
            }
        }
        
        exit.setVisible(true);
        cont.setVisible(true);
    }
    void hideAll(){
        for(int i = 0; i < lbl.size(); i++){
            for(int x = 0; x < lbl.get(i).length; x++){
                lbl.get(i)[x].setVisible(false);
            }
        }
        exit.setVisible(false);
        cont.setVisible(false);
    }
    
    public void paint(Graphics g){
        g.drawString("Pause Screen - Characters' status", 300, 90);
        for(int i = 0; i < lbl.size(); i++){
            g.drawImage(img.get(i), width/2 + 50 + (width*i), 110, null);
        }
    }
    
    public void actionPerformed(ActionEvent e){
        hideAll();
    }
}