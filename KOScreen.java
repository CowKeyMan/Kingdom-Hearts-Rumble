import java.awt.*;
import java.awt.event.*;
import java.util.*;
public class KOScreen implements ActionListener{
    Button cont = new Button("Restart Round"), exit = new Button("Exit to Main Menu");
    ArrayList<Image> img = new ArrayList<Image>();
    
    public KOScreen(int widthLimit, int heightLimit){
        cont.setBounds(widthLimit/2 - 125, heightLimit/2 + 100, 250, 50);
        cont.addActionListener(this);
        exit.setBounds(10,35, 250, 50);
        exit.addActionListener(this);
        hideButtons();
    }
    
    void showButtons(ArrayList<Character> c){
        img.clear();
        for(int i = 0; i < c.size(); i++){
            img.add(c.get(i).koImage);
        }
        cont.setVisible(true);
        exit.setVisible(true);
    }
    void hideButtons(){
        cont.setVisible(false);
        exit.setVisible(false);
    }
    public void paint(Graphics g, int widthLimit){
        g.drawString("You have died", widthLimit/2 - 200, 200);
        for(int i = 0; i < img.size(); i++){
            g.drawImage(img.get(i), widthLimit/6 + (widthLimit/3) * i, 250, null);
        }
    }
    
    public void actionPerformed(ActionEvent e){
        hideButtons();
    }
}