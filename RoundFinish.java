//To use GUI items and images
import java.awt.*;
//To use arraylists
import java.util.*;
public class RoundFinish{
    Button exit = new Button("Exit to Main Menu");
    Button start = new Button("Start round");
    
    int imageSpace;
    
    public RoundFinish(int widthLimit, int heightLimit){
        exit.setBounds(10,35, 250, 50);
        start.setBounds(widthLimit - 250, heightLimit- 50, 250, 50);
        
        imageSpace = (widthLimit - 200)/3;
    }
    
    public void paint(Graphics g, ArrayList<Character> c, ArrayList<Integer> a, int widthLimit, int heightLimit, int round, int maxRound){
        for(int i = 0; i < a.size(); i++){
            g.drawImage(c.get(a.get(i)-1).baseImage, 50 + i*imageSpace, 100, null);
        }
        if(round > 1){
            g.drawString("Congratulations", widthLimit/2 - 200, 70);
            g.drawString("You have completed round " + (round-1) + "/" + maxRound, widthLimit/2 - 400, 120);
        }
        g.drawString("Go to round " + round, widthLimit - 350, heightLimit - 75);
    }
    
    void showButtons(){
        exit.setVisible(true);
        start.setVisible(true);
    }
    void hideButtons(){
        exit.setVisible(false);
        start.setVisible(false);
    }
}