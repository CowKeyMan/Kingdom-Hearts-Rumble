//To use buttons
import java.awt.*;
//To use array lists
import java.util.*;
public class Menu{
    //The buttons to be used
    ArrayList<Button> btn = new ArrayList<Button>();
    Button btnBack;
    //Boolean to check if a back button is implemented
    boolean back = false;
    
    //Method to show the buttons on screen
    //widthLimit = horizontal size of screen
    //heighLimit = vertical size of screen
    //top separation = distance of first button from roof of screen
    //bottom separation = distance of last button from bottom of screen
    //side separation = distance of buttons from sides of screen(buttons are centered)
    //int vertical separation = vertical separation from button to button
    void showButtons(int widthLimit, int heightLimit, int topSeparation, int bottomSeparation,
        int sideSeparation, int verticalSeparation){
            //The amount of vertical space allocated fro the buttons
            int verticalSpace = heightLimit - topSeparation - bottomSeparation;
            //The button height
            int height = (verticalSpace - verticalSeparation*(btn.size()-1))/btn.size();
            //The button width
            int width = widthLimit - sideSeparation*2;
            
            //set the positions of the buttons
            for(int i = 0; i < btn.size(); i++){
                btn.get(i).setBounds(sideSeparation, topSeparation + (height + verticalSeparation)*i, width, height);
                btn.get(i).setVisible(true);
            }
            
            if(back == true){
                btnBack.setVisible(true);
            }
    }
    
    //Method to remove the buttons from screen
    void hideButtons(){
        for(int i = 0; i < btn.size(); i++){
            btn.get(i).setVisible(false);
        }
        if(back == true){
            btnBack.setVisible(false);
        }
    }
    
    //Method to add buttons
    void addButton(String name){
        btn.add(new Button(name));
        btn.get(btn.size()-1).setVisible(false);
    }
    
    void addBackButton(int heightLimit, int width, int height){
        btnBack = new Button("BACK");
        btnBack.setBounds(3, heightLimit-height, width, height);
        back = true;
        btnBack.setVisible(false);
    }
}