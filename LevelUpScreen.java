//To use GUI items
import java.awt.*;
import java.util.*;
public class LevelUpScreen{
    //The buttons to level up a specific attribute
    ArrayList<Button[]> btn = new ArrayList<Button[]>();
    ArrayList<Image> img = new ArrayList<Image>();
    int width = 0, height = 0;
    
    void showAll(int widthLimit, int heightLimit, ArrayList<Character> c){
        btn.clear();
        img.clear();
        Button but[] = new Button[]{
            new Button("Increase Health"), new Button("Increase Damage"), 
            new Button("Decrease Ability 1's cooldown"), new Button("Decrease Ability 2's cooldown"),
            new Button("Increase Movement Speed"), new Button("Level Up Ability")
        };
        Button but2[] = new Button[]{
            new Button("Increase Health"), new Button("Increase Damage"), 
            new Button("Decrease Ability 1's cooldown"), new Button("Decrease Ability 2's cooldown"),
            new Button("Increase Movement Speed"), new Button("Level Up Ability")
        };
        Button but3[] = new Button[]{
            new Button("Increase Health"), new Button("Increase Damage"), 
            new Button("Decrease Ability 1's cooldown"), new Button("Decrease Ability 2's cooldown"),
            new Button("Increase Movement Speed"), new Button("Level Up Ability")
        };
        //Add buttons and images
        for(int i = 0; i < c.size(); i ++){
            img.add(c.get(i).healthyImage);
        }
        btn.add(but); btn.add(but2); btn.add(but3);
        
        width = (widthLimit - 100 - 10*(c.size()-1))/c.size();
        height = (heightLimit - 150 - 10*(btn.get(0).length-1))/btn.get(0).length;
        
        //Set button's positions
        for(int i = 0; i < btn.size(); i++){
            for(int x = 0; x < btn.get(i).length; x++){
                btn.get(i)[x].setBounds(50 + (width + 10)*i, 100 + (height + 10)*x, width, height);
            }
        }
        
        //Set them visible
        for(int i = 0; i < btn.size(); i++){
            for(int x = 0; x < btn.get(i).length; x++){
                btn.get(i)[x].setVisible(true);
            }
        }
    }
    void hideAll(){
        for(int i = 0; i < btn.size(); i++){
            for(int x = 0; x < btn.get(i).length; x++){
                btn.get(i)[x].setVisible(false);
            }
        }
    }
    
    public void paint(Graphics g){
        for(int i = 0; i < btn.size(); i++){
            g.drawImage(img.get(i), width/2 + 50 + (width*i), 40, null);
        }
    }
}